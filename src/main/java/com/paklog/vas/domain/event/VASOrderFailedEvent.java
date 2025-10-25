package com.paklog.vas.domain.event;


public class VASOrderFailedEvent extends DomainEvent {
    private final String orderId;
    private final String reason;

    private VASOrderFailedEvent(final String orderId, final String reason) {
        super();
        this.orderId = orderId;
        this.reason = reason;
    }

    @Override
    public String getEventType() {
        return "VASOrderFailed";
    }

    public final String getOrderId() { return orderId; }
    public final String getReason() { return reason; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String orderId;
        private String reason;

        public Builder orderId(final String orderId) { this.orderId = orderId; return this; }
        public Builder reason(final String reason) { this.reason = reason; return this; }

        public VASOrderFailedEvent build() {
            return new VASOrderFailedEvent(orderId, reason);
        }
    }
}
