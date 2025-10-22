package com.paklog.vas.domain.event;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class VASOrderCreatedEvent extends DomainEvent {
    private final String orderId;
    private final String orderNumber;
    private final String serviceType;
    private final String customerId;
    private final String priority;

    @Builder
    public VASOrderCreatedEvent(String orderId, String orderNumber, String serviceType,
                               String customerId, String priority) {
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
}
