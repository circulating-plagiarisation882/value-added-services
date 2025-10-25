package com.paklog.vas.domain.event;


public class CustomizationCompletedEvent extends DomainEvent {
    private final String orderId;
    private final String customerId;

    private CustomizationCompletedEvent(final String orderId, final String customerId) {
        super();
        this.orderId = orderId;
        this.customerId = customerId;
    }

    @Override
    public String getEventType() {
        return "CustomizationCompleted";
    }

    public final String getOrderId() { return orderId; }
    public final String getCustomerId() { return customerId; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String orderId;
        private String customerId;

        public Builder orderId(final String orderId) { this.orderId = orderId; return this; }
        public Builder customerId(final String customerId) { this.customerId = customerId; return this; }

        public CustomizationCompletedEvent build() {
            return new CustomizationCompletedEvent(orderId, customerId);
        }
    }
}
