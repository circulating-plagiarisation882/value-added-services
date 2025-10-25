package com.paklog.vas.domain.event;


public class VASOrderCreatedEvent extends DomainEvent {
    private final String orderId;
    private final String orderNumber;
    private final String serviceType;
    private final String customerId;
    private final String priority;

    private VASOrderCreatedEvent(final String orderId, final String orderNumber, final String serviceType, final String customerId, final String priority) {
        super();
        this.orderId = orderId;
        this.orderNumber = orderNumber;
        this.serviceType = serviceType;
        this.customerId = customerId;
        this.priority = priority;
    }

    @Override
    public String getEventType() {
        return "VASOrderCreated";
    }

    public final String getOrderId() { return orderId; }
    public final String getOrderNumber() { return orderNumber; }
    public final String getServiceType() { return serviceType; }
    public final String getCustomerId() { return customerId; }
    public final String getPriority() { return priority; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String orderId;
        private String orderNumber;
        private String serviceType;
        private String customerId;
        private String priority;

        public Builder orderId(final String orderId) { this.orderId = orderId; return this; }
        public Builder orderNumber(final String orderNumber) { this.orderNumber = orderNumber; return this; }
        public Builder serviceType(final String serviceType) { this.serviceType = serviceType; return this; }
        public Builder customerId(final String customerId) { this.customerId = customerId; return this; }
        public Builder priority(final String priority) { this.priority = priority; return this; }

        public VASOrderCreatedEvent build() {
            return new VASOrderCreatedEvent(orderId, orderNumber, serviceType, customerId, priority);
        }
    }
}
