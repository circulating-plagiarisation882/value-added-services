package com.paklog.vas.domain.aggregate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KittingComponent {

    private String componentId;
    private String sku;
    private String description;

    private int requiredQuantity;
    private int pickedQuantity;

    private String location;
    private String lotNumber;

    private boolean verified;

    public boolean isComplete() {
        return verified && pickedQuantity >= requiredQuantity;
    }

    public void pick(int quantity) {
        this.pickedQuantity += quantity;
    }

    public void verify() {
        this.verified = true;
    }
}
