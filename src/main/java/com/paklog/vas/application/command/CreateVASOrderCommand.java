package com.paklog.vas.application.command;

import com.paklog.vas.domain.valueobject.*;
import lombok.*;
import javax.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateVASOrderCommand {
    @NotBlank
    private String customerId;
    @NotBlank
    private String warehouseId;
    @NotNull
    private ServiceType serviceType;
    @NotNull
    private ServicePriority priority;
    @NotNull
    private BillingType billingType;
    private double billingRate;
}
