package com.paklog.vas.infrastructure.web.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.paklog.vas.application.command.CreateVASOrderCommand;
import com.paklog.vas.application.port.in.VASUseCase;
import com.paklog.vas.domain.aggregate.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/vas")
@Tag(name = "Value-Added Services", description = "VAS operations")
public class VASController {
    private static final Logger log = LoggerFactory.getLogger(VASController.class);


    private final VASUseCase vasUseCase;
    public VASController(VASUseCase vasUseCase) {
        this.vasUseCase = vasUseCase;
    }


    @PostMapping("/orders")
    @Operation(summary = "Create VAS order")
    public ResponseEntity<String> createOrder(@Valid @RequestBody CreateVASOrderCommand command) {
        String orderId = vasUseCase.createOrder(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderId);
    }

    @PostMapping("/orders/{id}/start")
    @Operation(summary = "Start VAS order")
    public ResponseEntity<Void> startOrder(@PathVariable String id) {
        vasUseCase.startOrder(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/orders/{orderId}/steps/{stepId}/complete")
    @Operation(summary = "Complete workflow step")
    public ResponseEntity<Void> completeStep(@PathVariable String orderId, @PathVariable String stepId) {
        vasUseCase.completeStep(orderId, stepId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/orders/{id}/quality-check")
    @Operation(summary = "Perform quality check")
    public ResponseEntity<Void> performQualityCheck(@PathVariable String id, @RequestBody QualityCheckpoint checkpoint) {
        vasUseCase.performQualityCheck(id, checkpoint);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/orders/{id}/complete")
    @Operation(summary = "Complete VAS order")
    public ResponseEntity<Void> completeOrder(@PathVariable String id) {
        vasUseCase.completeOrder(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/orders/{id}")
    @Operation(summary = "Get VAS order")
    public ResponseEntity<VASOrder> getOrder(@PathVariable String id) {
        return ResponseEntity.ok(vasUseCase.getOrder(id));
    }
}
