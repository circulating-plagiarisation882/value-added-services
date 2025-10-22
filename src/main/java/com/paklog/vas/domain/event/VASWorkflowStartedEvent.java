package com.paklog.vas.domain.event;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = true)
public class VASWorkflowStartedEvent extends DomainEvent {
    private final String orderId;
    private final String serviceType;
    private final Instant startedAt;

    @Builder
    public VASWorkflowStartedEvent(String orderId, String serviceType, Instant startedAt) {
        super();
        this.orderId = orderId;
        this.serviceType = serviceType;
        this.startedAt = startedAt;
    }

    @Override
    public String getEventType() {
        return "VASWorkflowStarted";
    }
}
