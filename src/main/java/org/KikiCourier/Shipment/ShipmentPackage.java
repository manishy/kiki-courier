package org.KikiCourier.Shipment;

import org.KikiCourier.Offer.IOffer;

import java.util.HashSet;
import java.util.Set;

public class ShipmentPackage {
    private final String id;
    private final Double basePrice;
    private final int weight;
    private final int distanceInKm;

    private final Set<IOffer> offers = new HashSet<>();
    private Double estimatedDeliveryTime;

    public ShipmentPackage(String id, Double basePrice, int weight, int distanceInKm) {
        this.id = id;
        this.basePrice = basePrice;
        this.weight = weight;
        this.distanceInKm = distanceInKm;
        this.estimatedDeliveryTime = 0.00;
    }

    public void applyOffer(IOffer shipmentOffer) {
        if (offers.isEmpty()) {
            offers.add(shipmentOffer);
        }
    }

    public void setEstimatedDeliveryTime(Double estimatedDeliveryTime){
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }

    public int getWeightInKg() {
        return weight;
    }
    public int getDistanceInKm() {
        return distanceInKm;
    }

    public String getId() {
        return id;
    }

    private Double calculateTotalDiscount(Double deliveryCost) {
        return offers.stream()
                .mapToDouble(offer -> offer.calculateDiscount(deliveryCost))
                .sum();
    }

    private Double calculateBaseDeliveryCost() {
        return basePrice + (weight * 10) + (distanceInKm * 5);
    }

    @Override
    public String toString() {
        Double deliveryCost = calculateBaseDeliveryCost();
        Double totalDiscount = calculateTotalDiscount(deliveryCost);
        Double actualCost = deliveryCost - totalDiscount;
        return String.format("%s %.0f %.0f %.2f", id, totalDiscount, actualCost, estimatedDeliveryTime);
    }
}
