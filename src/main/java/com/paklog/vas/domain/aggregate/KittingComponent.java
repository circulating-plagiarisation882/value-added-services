package com.paklog.vas.domain.aggregate;


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


    // Getters
    public String getComponentId() { return componentId; }
    public String getSku() { return sku; }
    public String getDescription() { return description; }
    public int getRequiredQuantity() { return requiredQuantity; }
    public int getPickedQuantity() { return pickedQuantity; }
    public String getLocation() { return location; }
    public String getLotNumber() { return lotNumber; }
    public boolean isVerified() { return verified; }

    // Setters
    public void setComponentId(String componentId) { this.componentId = componentId; }
    public void setSku(String sku) { this.sku = sku; }
    public void setDescription(String description) { this.description = description; }
    public void setRequiredQuantity(int requiredQuantity) { this.requiredQuantity = requiredQuantity; }
    public void setPickedQuantity(int pickedQuantity) { this.pickedQuantity = pickedQuantity; }
    public void setLocation(String location) { this.location = location; }
    public void setLotNumber(String lotNumber) { this.lotNumber = lotNumber; }
    public void setVerified(boolean verified) { this.verified = verified; }

    // Builder
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String componentId;
        private String sku;
        private String description;
        private int requiredQuantity;
        private int pickedQuantity;
        private String location;
        private String lotNumber;
        private boolean verified;

        public Builder componentId(String componentId) { this.componentId = componentId; return this; }
        public Builder sku(String sku) { this.sku = sku; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder requiredQuantity(int requiredQuantity) { this.requiredQuantity = requiredQuantity; return this; }
        public Builder pickedQuantity(int pickedQuantity) { this.pickedQuantity = pickedQuantity; return this; }
        public Builder location(String location) { this.location = location; return this; }
        public Builder lotNumber(String lotNumber) { this.lotNumber = lotNumber; return this; }
        public Builder verified(boolean verified) { this.verified = verified; return this; }

        public KittingComponent build() {
            KittingComponent obj = new KittingComponent();
            obj.componentId = this.componentId;
            obj.sku = this.sku;
            obj.description = this.description;
            obj.requiredQuantity = this.requiredQuantity;
            obj.pickedQuantity = this.pickedQuantity;
            obj.location = this.location;
            obj.lotNumber = this.lotNumber;
            obj.verified = this.verified;
            return obj;
        }
    }
}
