package com.paklog.vas.domain.event;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class VASOrderFailedEvent extends DomainEvent {
    private final String orderId;
    private final String reason;

    @Builder
    public VASOrderFailedEvent(String orderId, String reason) {
        super();
        this.orderId = orderId;
        this.reason = reason;
    }

    @Override
    public String getEventType() {
        return "VASOrderFailed";
    }
}
