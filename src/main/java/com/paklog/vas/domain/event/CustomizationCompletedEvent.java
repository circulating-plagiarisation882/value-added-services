package com.paklog.vas.domain.event;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CustomizationCompletedEvent extends DomainEvent {
    private final String orderId;
    private final String customerId;

    @Builder
    public CustomizationCompletedEvent(String orderId, String customerId) {
        super();
        this.orderId = orderId;
        this.customerId = customerId;
    }

    @Override
    public String getEventType() {
        return "CustomizationCompleted";
    }
}
