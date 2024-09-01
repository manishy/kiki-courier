package org.KikiCourier.Shipment;


public class ShipmentPricingSummary {
    private final String id;
    private final Double discount;
    private final Double actualCost;

    public ShipmentPricingSummary(String id, Double discount, Double actualCost) {
        this.id = id;
        this.discount = discount;
        this.actualCost = actualCost;
    }

    public String getId() {
        return id;
    }

    public Double getDiscount() {
        return discount;
    }

    public Double getActualCost() {
        return actualCost;
    }

    @Override
    public String toString() {
        return String.format("%s %.0f %.0f", id, discount, actualCost);
    }
}