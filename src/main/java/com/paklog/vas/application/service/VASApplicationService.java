package com.paklog.vas.application.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.paklog.vas.application.command.CreateVASOrderCommand;
import com.paklog.vas.application.port.in.VASUseCase;
import com.paklog.vas.application.port.out.PublishEventPort;
import com.paklog.vas.domain.aggregate.*;
import com.paklog.vas.domain.repository.VASOrderRepository;
import com.paklog.vas.domain.service.WorkflowOrchestratorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class VASApplicationService implements VASUseCase {
    private static final Logger log = LoggerFactory.getLogger(VASApplicationService.class);


    private final VASOrderRepository orderRepository;
    private final WorkflowOrchestratorService orchestratorService;
    private final PublishEventPort publishEventPort;
    public VASApplicationService(VASOrderRepository orderRepository, WorkflowOrchestratorService orchestratorService, PublishEventPort publishEventPort) {
        this.orderRepository = orderRepository;
        this.orchestratorService = orchestratorService;
        this.publishEventPort = publishEventPort;
    }


    @Override
    @Transactional
    public String createOrder(CreateVASOrderCommand command) {
        log.info("Creating VAS order for service: {}", command.serviceType());

        VASOrder order = VASOrder.builder()
            .id(UUID.randomUUID().toString())
            .orderNumber("VAS-" + Instant.now().getEpochSecond())
            .customerId(command.customerId())
            .warehouseId(command.warehouseId())
            .serviceType(command.serviceType())
            .priority(command.priority())
            .billingType(command.billingType())
            .billingRate(command.billingRate())
            .steps(orchestratorService.createWorkflowSteps(command.serviceType()))
            .slaHours(orchestratorService.calculateSLAHours(command.serviceType()))
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
