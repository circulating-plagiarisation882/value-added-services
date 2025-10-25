package com.paklog.vas.domain.event;


public class QualityCheckPassedEvent extends DomainEvent {
    private final String orderId;
    private final String checkpointId;
    private final String grade;

    private QualityCheckPassedEvent(final String orderId, final String checkpointId, final String grade) {
        super();
        this.orderId = orderId;
        this.checkpointId = checkpointId;
        this.grade = grade;
    }

    @Override
    public String getEventType() {
        return "QualityCheckPassed";
    }

    public final String getOrderId() { return orderId; }
    public final String getCheckpointId() { return checkpointId; }
    public final String getGrade() { return grade; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String orderId;
        private String checkpointId;
        private String grade;

        public Builder orderId(final String orderId) { this.orderId = orderId; return this; }
        public Builder checkpointId(final String checkpointId) { this.checkpointId = checkpointId; return this; }
        public Builder grade(final String grade) { this.grade = grade; return this; }

        public QualityCheckPassedEvent build() {
            return new QualityCheckPassedEvent(orderId, checkpointId, grade);
        }
    }
}
