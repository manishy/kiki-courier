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

    public ShipmentPackage(String id, Double basePrice, int weight, int distanceInKm) {
        this.id = id;
        this.basePrice = basePrice;
        this.weight = weight;
        this.distanceInKm = distanceInKm;
    }

    public void applyOffer(IOffer shipmentOffer) {
        if (offers.isEmpty()) {
            offers.add(shipmentOffer);
        }
    }

    public ShipmentPricingSummary getShipmentPricing() {
        Double deliveryCost = calculateBaseDeliveryCost();
        Double totalDiscount = calculateTotalDiscount(deliveryCost);
        Double actualCost = deliveryCost - totalDiscount;
        return new ShipmentPricingSummary(id, totalDiscount, actualCost);
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
}
