package com.paklog.vas.domain.event;

import java.time.Instant;

public class VASWorkflowStartedEvent extends DomainEvent {
    private final String orderId;
    private final String serviceType;
    private final Instant startedAt;

    private VASWorkflowStartedEvent(final String orderId, final String serviceType, final Instant startedAt) {
        super();
        this.orderId = orderId;
        this.serviceType = serviceType;
        this.startedAt = startedAt;
    }

    @Override
    public String getEventType() {
        return "VASWorkflowStarted";
    }

    public final String getOrderId() { return orderId; }
    public final String getServiceType() { return serviceType; }
    public final Instant getStartedAt() { return startedAt; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String orderId;
        private String serviceType;
        private Instant startedAt;

        public Builder orderId(final String orderId) { this.orderId = orderId; return this; }
        public Builder serviceType(final String serviceType) { this.serviceType = serviceType; return this; }
        public Builder startedAt(final Instant startedAt) { this.startedAt = startedAt; return this; }

        public VASWorkflowStartedEvent build() {
            return new VASWorkflowStartedEvent(orderId, serviceType, startedAt);
        }
    }
}
