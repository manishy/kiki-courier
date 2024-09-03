package org.KikiCourier.Shipment;

import java.util.List;

public class Shipment {
    private final List<ShipmentPackage> packages;
    private double estimatedDeliveryTime;

    public Shipment(List<ShipmentPackage> packages) {
        this.packages = packages;
    }

    public List<ShipmentPackage> getPackages() {
        return packages;
    }

    public void setEstimatedDeliveryTime(double estimatedDeliveryTime){
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (ShipmentPackage pkg : packages) {
            sb.append(pkg.getId())
                    .append(" ")
                    .append(pkg.getShipmentPricing().getDiscount())
                    .append(" ")
                    .append(pkg.getShipmentPricing().getTotalCost())
                    .append(" ")
                    .append(String.format("%.2f", estimatedDeliveryTime))
                    .append("\n");
        }
        return sb.toString();
    }
}