package com.paklog.vas.domain.aggregate;

import com.paklog.vas.domain.event.*;
import com.paklog.vas.domain.valueobject.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "vas_orders")
public class VASOrder {

    @Id
    private String id;

    private String orderNumber;
    private String customerId;
    private String warehouseId;

    private ServiceType serviceType;
    private WorkflowStatus status;
    private ServicePriority priority;

    @Builder.Default
    private List<WorkflowStep> steps = new ArrayList<>();

    @Builder.Default
    private List<QualityCheckpoint> qualityCheckpoints = new ArrayList<>();

    @Builder.Default
    private List<KittingComponent> components = new ArrayList<>();

    private int totalSteps;
    private int completedSteps;
    private int progressPercentage;

    private int estimatedLaborMinutes;
    private int actualLaborMinutes;

    private BillingType billingType;
    private double billingRate;
    private double totalCost;

    private Instant dueDate;
    private Instant startedAt;
    private Instant completedAt;

    private int slaHours;
    private boolean slaViolated;

    private int reworkCount;
    private String reworkReason;

    @Version
    private Long version;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    @Transient
    @Builder.Default
    private List<DomainEvent> domainEvents = new ArrayList<>();

    // State machine methods

    public void initiate() {
        this.status = WorkflowStatus.PENDING;
        this.totalSteps = steps.size();
        this.completedSteps = 0;
        this.progressPercentage = 0;
        this.reworkCount = 0;

        calculateDueDate();

        addDomainEvent(VASOrderCreatedEvent.builder()
            .orderId(this.id)
            .orderNumber(this.orderNumber)
            .serviceType(this.serviceType.name())
            .customerId(this.customerId)
            .priority(this.priority.name())
            .build());
    }

    public void start() {
        if (this.status != WorkflowStatus.PENDING) {
            throw new IllegalStateException("Order must be PENDING to start");
        }

        this.status = WorkflowStatus.IN_PROGRESS;
        this.startedAt = Instant.now();

        // Start first step
        if (!steps.isEmpty()) {
            steps.get(0).start(null);
        }

        addDomainEvent(VASWorkflowStartedEvent.builder()
            .orderId(this.id)
            .serviceType(this.serviceType.name())
            .startedAt(this.startedAt)
            .build());
    }

    public void completeStep(String stepId) {
        WorkflowStep step = findStep(stepId);

        if (step == null) {
            throw new IllegalArgumentException("Step not found: " + stepId);
        }

        step.complete();
        this.completedSteps++;
        this.actualLaborMinutes += step.getActualDurationMinutes();

        updateProgress();

        // Check if quality check required
        if (step.isRequiresQualityCheck()) {
            this.status = WorkflowStatus.QUALITY_CHECK;
        }

        // Check if all steps complete
        if (completedSteps >= totalSteps) {
            if (qualityCheckpoints.stream().allMatch(QualityCheckpoint::isPassed)) {
                complete();
            } else {
                this.status = WorkflowStatus.QUALITY_CHECK;
            }
        } else {
            // Start next step
            int nextSequence = step.getSequence() + 1;
            WorkflowStep nextStep = steps.stream()
                .filter(s -> s.getSequence() == nextSequence)
                .findFirst()
                .orElse(null);

            if (nextStep != null && this.status == WorkflowStatus.IN_PROGRESS) {
                nextStep.start(null);
            }
        }
    }

    public void performQualityCheck(QualityCheckpoint checkpoint) {
        checkpoint.perform();
        checkpoint.evaluateResults();

        qualityCheckpoints.add(checkpoint);

        if (checkpoint.isPassed()) {
            this.status = WorkflowStatus.IN_PROGRESS;

            addDomainEvent(QualityCheckPassedEvent.builder()
                .orderId(this.id)
                .checkpointId(checkpoint.getCheckpointId())
                .grade(checkpoint.getGrade().name())
                .build());

        } else {
            requireRework(checkpoint.getNotes());

            addDomainEvent(QualityCheckFailedEvent.builder()
                .orderId(this.id)
                .checkpointId(checkpoint.getCheckpointId())
                .reason(checkpoint.getNotes())
                .build());
        }
    }

    public void requireRework(String reason) {
        if (reworkCount >= 2) {
            fail("Maximum rework attempts exceeded");
            return;
        }

        this.status = WorkflowStatus.REWORK_REQUIRED;
        this.reworkCount++;
        this.reworkReason = reason;

        // Reset completed steps for rework
        steps.forEach(step -> {
            if (!step.isCompleted() || step.isRequiresQualityCheck()) {
                step.setCompleted(false);
                step.setStartedAt(null);
                step.setCompletedAt(null);
            }
        });
    }

    public void complete() {
        this.status = WorkflowStatus.COMPLETED;
        this.completedAt = Instant.now();
        this.progressPercentage = 100;

        calculateTotalCost();
        checkSLAViolation();

        addDomainEvent(VASOrderCompletedEvent.builder()
            .orderId(this.id)
            .completedAt(this.completedAt)
            .totalCost(this.totalCost)
            .laborMinutes(this.actualLaborMinutes)
            .slaViolated(this.slaViolated)
            .build());

        if (serviceType == ServiceType.KITTING && components.stream().allMatch(KittingComponent::isComplete)) {
            addDomainEvent(KittingCompletedEvent.builder()
                .orderId(this.id)
                .componentCount(components.size())
                .build());
        } else if (serviceType == ServiceType.CUSTOMIZATION) {
            addDomainEvent(CustomizationCompletedEvent.builder()
                .orderId(this.id)
                .customerId(this.customerId)
                .build());
        }
    }

    public void fail(String reason) {
        this.status = WorkflowStatus.FAILED;
        this.reworkReason = reason;

        addDomainEvent(VASOrderFailedEvent.builder()
            .orderId(this.id)
            .reason(reason)
            .build());
    }

    public void cancel() {
        this.status = WorkflowStatus.CANCELLED;
    }

    // Business logic methods

    private void updateProgress() {
        if (totalSteps > 0) {
            this.progressPercentage = (completedSteps * 100) / totalSteps;
        }
    }

    private void calculateDueDate() {
        if (slaHours > 0) {
            this.dueDate = Instant.now().plus(slaHours, ChronoUnit.HOURS);
        }
    }

    private void calculateTotalCost() {
        switch (billingType) {
            case PER_HOUR:
                this.totalCost = (actualLaborMinutes / 60.0) * billingRate;
                break;
            case PER_UNIT:
                this.totalCost = billingRate; // Assuming 1 unit
                break;
            case FLAT_RATE:
                this.totalCost = billingRate;
                break;
            default:
                this.totalCost = 0.0;
        }
    }

    private void checkSLAViolation() {
        if (dueDate != null && completedAt != null) {
            this.slaViolated = completedAt.isAfter(dueDate);
        }
    }

    private WorkflowStep findStep(String stepId) {
        return steps.stream()
            .filter(s -> s.getStepId().equals(stepId))
            .findFirst()
            .orElse(null);
    }

    public void addComponent(KittingComponent component) {
        if (components.size() >= 20) {
            throw new IllegalStateException("Maximum 20 components per kit");
        }
        components.add(component);
    }

    public boolean isOverdue() {
        return dueDate != null && Instant.now().isAfter(dueDate) &&
               status != WorkflowStatus.COMPLETED && status != WorkflowStatus.CANCELLED;
    }

    public WorkflowStep getCurrentStep() {
        return steps.stream()
            .filter(s -> !s.isCompleted())
            .findFirst()
            .orElse(null);
    }

    private void addDomainEvent(DomainEvent event) {
        this.domainEvents.add(event);
    }

    public List<DomainEvent> getDomainEvents() {
        return new ArrayList<>(domainEvents);
    }

    public void clearDomainEvents() {
        domainEvents.clear();
    }
}
