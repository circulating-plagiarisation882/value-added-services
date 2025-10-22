package com.paklog.vas.application.service;

import com.paklog.vas.application.command.CreateVASOrderCommand;
import com.paklog.vas.application.port.in.VASUseCase;
import com.paklog.vas.application.port.out.PublishEventPort;
import com.paklog.vas.domain.aggregate.*;
import com.paklog.vas.domain.repository.VASOrderRepository;
import com.paklog.vas.domain.service.WorkflowOrchestratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class VASApplicationService implements VASUseCase {

    private final VASOrderRepository orderRepository;
    private final WorkflowOrchestratorService orchestratorService;
    private final PublishEventPort publishEventPort;

    @Override
    @Transactional
    public String createOrder(CreateVASOrderCommand command) {
        log.info("Creating VAS order for service: {}", command.getServiceType());

        VASOrder order = VASOrder.builder()
            .id(UUID.randomUUID().toString())
            .orderNumber("VAS-" + Instant.now().getEpochSecond())
            .customerId(command.getCustomerId())
            .warehouseId(command.getWarehouseId())
            .serviceType(command.getServiceType())
            .priority(command.getPriority())
            .billingType(command.getBillingType())
            .billingRate(command.getBillingRate())
            .steps(orchestratorService.createWorkflowSteps(command.getServiceType()))
            .slaHours(orchestratorService.calculateSLAHours(command.getServiceType()))
            .build();

        order.initiate();
        order = orderRepository.save(order);

        order.getDomainEvents().forEach(publishEventPort::publish);
        order.clearDomainEvents();

        log.info("VAS order created: {}", order.getId());
        return order.getId();
    }

    @Override
    @Transactional
    public void startOrder(String orderId) {
        VASOrder order = orderRepository.findById(orderId)
            .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        order.start();
        orderRepository.save(order);

        order.getDomainEvents().forEach(publishEventPort::publish);
        order.clearDomainEvents();
    }

    @Override
    @Transactional
    public void completeStep(String orderId, String stepId) {
        VASOrder order = orderRepository.findById(orderId)
            .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        order.completeStep(stepId);
        orderRepository.save(order);

        order.getDomainEvents().forEach(publishEventPort::publish);
        order.clearDomainEvents();
    }

    @Override
    @Transactional
    public void performQualityCheck(String orderId, QualityCheckpoint checkpoint) {
        VASOrder order = orderRepository.findById(orderId)
            .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        order.performQualityCheck(checkpoint);
        orderRepository.save(order);

        order.getDomainEvents().forEach(publishEventPort::publish);
        order.clearDomainEvents();
    }

    @Override
    @Transactional
    public void completeOrder(String orderId) {
        VASOrder order = orderRepository.findById(orderId)
            .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        order.complete();
        orderRepository.save(order);

        order.getDomainEvents().forEach(publishEventPort::publish);
        order.clearDomainEvents();
    }

    @Override
    public VASOrder getOrder(String orderId) {
        return orderRepository.findById(orderId)
            .orElseThrow(() -> new IllegalArgumentException("Order not found"));
    }
}
