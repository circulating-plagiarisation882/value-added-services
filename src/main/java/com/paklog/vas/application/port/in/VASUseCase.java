package com.paklog.vas.application.port.in;

import com.paklog.vas.application.command.*;
import com.paklog.vas.domain.aggregate.*;

public interface VASUseCase {
    String createOrder(CreateVASOrderCommand command);
    void startOrder(String orderId);
    void completeStep(String orderId, String stepId);
    void performQualityCheck(String orderId, QualityCheckpoint checkpoint);
    void completeOrder(String orderId);
    VASOrder getOrder(String orderId);
}
