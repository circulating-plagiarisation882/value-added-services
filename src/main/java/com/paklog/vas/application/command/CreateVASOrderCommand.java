package com.paklog.vas.application.command;

import com.paklog.vas.domain.valueobject.ServiceType;
import com.paklog.vas.domain.valueobject.ServicePriority;
import com.paklog.vas.domain.valueobject.BillingType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateVASOrderCommand(
    @NotBlank
    String customerId,
    @NotBlank
    String warehouseId,
    @NotNull
    ServiceType serviceType,
    @NotNull
    ServicePriority priority,
    @NotNull
    BillingType billingType,
    double billingRate
) {}
