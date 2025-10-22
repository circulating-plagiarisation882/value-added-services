package com.paklog.vas.domain.repository;

import com.paklog.vas.domain.aggregate.VASOrder;
import com.paklog.vas.domain.valueobject.WorkflowStatus;
import java.util.List;
import java.util.Optional;

public interface VASOrderRepository {
    VASOrder save(VASOrder order);
    Optional<VASOrder> findById(String id);
    List<VASOrder> findByStatus(WorkflowStatus status);
    List<VASOrder> findByCustomerId(String customerId);
    List<VASOrder> findOverdueOrders();
    void deleteById(String id);
}
