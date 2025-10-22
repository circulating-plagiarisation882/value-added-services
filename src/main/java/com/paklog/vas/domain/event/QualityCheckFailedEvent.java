package com.paklog.vas.domain.event;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class QualityCheckFailedEvent extends DomainEvent {
    private final String orderId;
    private final String checkpointId;
    private final String reason;

    @Builder
    public QualityCheckFailedEvent(String orderId, String checkpointId, String reason) {
        super();
        this.orderId = orderId;
        this.checkpointId = checkpointId;
        this.reason = reason;
    }

    @Override
    public String getEventType() {
        return "QualityCheckFailed";
    }
}
