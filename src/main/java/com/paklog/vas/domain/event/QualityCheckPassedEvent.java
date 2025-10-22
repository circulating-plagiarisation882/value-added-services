package com.paklog.vas.domain.event;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class QualityCheckPassedEvent extends DomainEvent {
    private final String orderId;
    private final String checkpointId;
    private final String grade;

    @Builder
    public QualityCheckPassedEvent(String orderId, String checkpointId, String grade) {
        super();
        this.orderId = orderId;
        this.checkpointId = checkpointId;
        this.grade = grade;
    }

    @Override
    public String getEventType() {
        return "QualityCheckPassed";
    }
}
