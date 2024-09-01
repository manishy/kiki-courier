package org.KikiCourier;

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
        if (offers.size() == 0) {
            offers.add(shipmentOffer);
        }
    }

    public Double calculateDeliveryCost() {
        Double deliveryCost = basePrice + (weight * 10) + (distanceInKm * 5);
        Double discount = 0.0;
        for (IOffer offer : offers) {
            discount += offer.calculateDiscount(deliveryCost);
        }
        double actualCost = deliveryCost - discount;
        System.out.printf("%s %.0f %.0f%n", id, discount, actualCost);
        return actualCost;
    }
}
