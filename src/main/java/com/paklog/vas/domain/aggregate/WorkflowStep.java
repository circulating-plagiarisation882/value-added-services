package com.paklog.vas.domain.aggregate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowStep {

    private String stepId;
    private int sequence;
    private String name;
    private String description;

    private boolean completed;
    private boolean requiresQualityCheck;

    private String assignedTo;
    private Instant startedAt;
    private Instant completedAt;

    private int estimatedDurationMinutes;
    private int actualDurationMinutes;

    private String photoUrl;
    private String notes;

    public void start(String workerId) {
        this.assignedTo = workerId;
        this.startedAt = Instant.now();
    }

    public void complete() {
        this.completed = true;
        this.completedAt = Instant.now();

        if (startedAt != null) {
            this.actualDurationMinutes = (int) ((completedAt.getEpochSecond() - startedAt.getEpochSecond()) / 60);
        }
    }

    public boolean isOverdue() {
        if (startedAt == null || estimatedDurationMinutes == 0) {
            return false;
        }

        Instant estimatedCompletion = startedAt.plusSeconds(estimatedDurationMinutes * 60L);
        return Instant.now().isAfter(estimatedCompletion) && !completed;
    }
}
