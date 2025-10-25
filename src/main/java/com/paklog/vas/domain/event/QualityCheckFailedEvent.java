package com.paklog.vas.domain.event;


public class QualityCheckFailedEvent extends DomainEvent {
    private final String orderId;
    private final String checkpointId;
    private final String reason;

    private QualityCheckFailedEvent(final String orderId, final String checkpointId, final String reason) {
        super();
        this.orderId = orderId;
        this.checkpointId = checkpointId;
        this.reason = reason;
    }

    @Override
    public String getEventType() {
        return "QualityCheckFailed";
    }

    public final String getOrderId() { return orderId; }
    public final String getCheckpointId() { return checkpointId; }
    public final String getReason() { return reason; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String orderId;
        private String checkpointId;
        private String reason;

        public Builder orderId(final String orderId) { this.orderId = orderId; return this; }
        public Builder checkpointId(final String checkpointId) { this.checkpointId = checkpointId; return this; }
        public Builder reason(final String reason) { this.reason = reason; return this; }

        public QualityCheckFailedEvent build() {
            return new QualityCheckFailedEvent(orderId, checkpointId, reason);
        }
    }
}
