package com.paklog.vas.infrastructure.persistence.repository;

import com.paklog.vas.domain.aggregate.VASOrder;
import com.paklog.vas.domain.repository.VASOrderRepository;
import com.paklog.vas.domain.valueobject.WorkflowStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.Repository;
import java.time.Instant;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class MongoVASOrderRepository implements VASOrderRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public VASOrder save(VASOrder order) {
        return mongoTemplate.save(order);
    }

    @Override
    public Optional<VASOrder> findById(String id) {
        return Optional.ofNullable(mongoTemplate.findById(id, VASOrder.class));
    }

    @Override
    public List<VASOrder> findByStatus(WorkflowStatus status) {
        Query query = new Query(Criteria.where("status").is(status));
        return mongoTemplate.find(query, VASOrder.class);
    }

    @Override
    public List<VASOrder> findByCustomerId(String customerId) {
        Query query = new Query(Criteria.where("customerId").is(customerId));
        return mongoTemplate.find(query, VASOrder.class);
    }

    @Override
    public List<VASOrder> findOverdueOrders() {
        Query query = new Query(Criteria.where("dueDate").lt(Instant.now())
            .and("status").in(WorkflowStatus.PENDING, WorkflowStatus.IN_PROGRESS));
        return mongoTemplate.find(query, VASOrder.class);
    }

    @Override
    public void deleteById(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        mongoTemplate.remove(query, VASOrder.class);
    }
}
