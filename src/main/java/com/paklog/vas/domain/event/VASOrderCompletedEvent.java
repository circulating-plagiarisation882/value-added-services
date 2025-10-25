package com.paklog.vas.domain.event;

import java.time.Instant;

public class VASOrderCompletedEvent extends DomainEvent {
    private final String orderId;
    private final Instant completedAt;
    private final double totalCost;
    private final int laborMinutes;
    private final boolean slaViolated;

    private VASOrderCompletedEvent(final String orderId, final Instant completedAt, final double totalCost, final int laborMinutes, final boolean slaViolated) {
        super();
        this.orderId = orderId;
        this.completedAt = completedAt;
        this.totalCost = totalCost;
        this.laborMinutes = laborMinutes;
        this.slaViolated = slaViolated;
    }

    @Override
    public String getEventType() {
        return "VASOrderCompleted";
    }

    public final String getOrderId() { return orderId; }
    public final Instant getCompletedAt() { return completedAt; }
    public final double getTotalCost() { return totalCost; }
    public final int getLaborMinutes() { return laborMinutes; }
    public final boolean getSlaViolated() { return slaViolated; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String orderId;
        private Instant completedAt;
        private double totalCost;
        private int laborMinutes;
        private boolean slaViolated;

        public Builder orderId(final String orderId) { this.orderId = orderId; return this; }
        public Builder completedAt(final Instant completedAt) { this.completedAt = completedAt; return this; }
        public Builder totalCost(final double totalCost) { this.totalCost = totalCost; return this; }
        public Builder laborMinutes(final int laborMinutes) { this.laborMinutes = laborMinutes; return this; }
        public Builder slaViolated(final boolean slaViolated) { this.slaViolated = slaViolated; return this; }

        public VASOrderCompletedEvent build() {
            return new VASOrderCompletedEvent(orderId, completedAt, totalCost, laborMinutes, slaViolated);
        }
    }
}
