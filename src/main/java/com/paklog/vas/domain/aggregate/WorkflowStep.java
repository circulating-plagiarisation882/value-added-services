package com.paklog.vas.domain.aggregate;


import java.time.Instant;

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


    // Getters
    public String getStepId() { return stepId; }
    public int getSequence() { return sequence; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public boolean isCompleted() { return completed; }
    public boolean isRequiresQualityCheck() { return requiresQualityCheck; }
    public String getAssignedTo() { return assignedTo; }
    public Instant getStartedAt() { return startedAt; }
    public Instant getCompletedAt() { return completedAt; }
    public int getEstimatedDurationMinutes() { return estimatedDurationMinutes; }
    public int getActualDurationMinutes() { return actualDurationMinutes; }
    public String getPhotoUrl() { return photoUrl; }
    public String getNotes() { return notes; }

    // Setters
    public void setStepId(String stepId) { this.stepId = stepId; }
    public void setSequence(int sequence) { this.sequence = sequence; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setCompleted(boolean completed) { this.completed = completed; }
    public void setRequiresQualityCheck(boolean requiresQualityCheck) { this.requiresQualityCheck = requiresQualityCheck; }
    public void setAssignedTo(String assignedTo) { this.assignedTo = assignedTo; }
    public void setStartedAt(Instant startedAt) { this.startedAt = startedAt; }
    public void setCompletedAt(Instant completedAt) { this.completedAt = completedAt; }
    public void setEstimatedDurationMinutes(int estimatedDurationMinutes) { this.estimatedDurationMinutes = estimatedDurationMinutes; }
    public void setActualDurationMinutes(int actualDurationMinutes) { this.actualDurationMinutes = actualDurationMinutes; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }
    public void setNotes(String notes) { this.notes = notes; }

    // Builder
    public static Builder builder() { return new Builder(); }

    public static class Builder {
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

        public Builder stepId(String stepId) { this.stepId = stepId; return this; }
        public Builder sequence(int sequence) { this.sequence = sequence; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder completed(boolean completed) { this.completed = completed; return this; }
        public Builder requiresQualityCheck(boolean requiresQualityCheck) { this.requiresQualityCheck = requiresQualityCheck; return this; }
        public Builder assignedTo(String assignedTo) { this.assignedTo = assignedTo; return this; }
        public Builder startedAt(Instant startedAt) { this.startedAt = startedAt; return this; }
        public Builder completedAt(Instant completedAt) { this.completedAt = completedAt; return this; }
        public Builder estimatedDurationMinutes(int estimatedDurationMinutes) { this.estimatedDurationMinutes = estimatedDurationMinutes; return this; }
        public Builder actualDurationMinutes(int actualDurationMinutes) { this.actualDurationMinutes = actualDurationMinutes; return this; }
        public Builder photoUrl(String photoUrl) { this.photoUrl = photoUrl; return this; }
        public Builder notes(String notes) { this.notes = notes; return this; }

        public WorkflowStep build() {
            WorkflowStep obj = new WorkflowStep();
            obj.stepId = this.stepId;
            obj.sequence = this.sequence;
            obj.name = this.name;
            obj.description = this.description;
            obj.completed = this.completed;
            obj.requiresQualityCheck = this.requiresQualityCheck;
            obj.assignedTo = this.assignedTo;
            obj.startedAt = this.startedAt;
            obj.completedAt = this.completedAt;
            obj.estimatedDurationMinutes = this.estimatedDurationMinutes;
            obj.actualDurationMinutes = this.actualDurationMinutes;
            obj.photoUrl = this.photoUrl;
            obj.notes = this.notes;
            return obj;
        }
    }
}
