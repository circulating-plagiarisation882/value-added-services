package com.paklog.vas.domain.event;


public class KittingCompletedEvent extends DomainEvent {
    private final String orderId;
    private final int componentCount;

    private KittingCompletedEvent(final String orderId, final int componentCount) {
        super();
        this.orderId = orderId;
        this.componentCount = componentCount;
    }

    @Override
    public String getEventType() {
        return "KittingCompleted";
    }

    public final String getOrderId() { return orderId; }
    public final int getComponentCount() { return componentCount; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String orderId;
        private int componentCount;

        public Builder orderId(final String orderId) { this.orderId = orderId; return this; }
        public Builder componentCount(final int componentCount) { this.componentCount = componentCount; return this; }

        public KittingCompletedEvent build() {
            return new KittingCompletedEvent(orderId, componentCount);
        }
    }
}
