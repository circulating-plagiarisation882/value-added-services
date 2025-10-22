package com.paklog.vas.domain.event;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = true)
public class VASOrderCompletedEvent extends DomainEvent {
    private final String orderId;
    private final Instant completedAt;
    private final double totalCost;
    private final int laborMinutes;
    private final boolean slaViolated;

    @Builder
    public VASOrderCompletedEvent(String orderId, Instant completedAt, double totalCost,
                                 int laborMinutes, boolean slaViolated) {
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
}
