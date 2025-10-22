package com.paklog.vas.domain.event;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class KittingCompletedEvent extends DomainEvent {
    private final String orderId;
    private final int componentCount;

    @Builder
    public KittingCompletedEvent(String orderId, int componentCount) {
        super();
        this.orderId = orderId;
        this.componentCount = componentCount;
    }

    @Override
    public String getEventType() {
        return "KittingCompleted";
    }
}
