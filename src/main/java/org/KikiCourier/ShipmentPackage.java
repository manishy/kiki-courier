package org.KikiCourier;

import org.KikiCourier.Offer.IOffer;
import org.KikiCourier.Offer.Offer;

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

    public void applyOffer(Offer sheepmentOffer) {
        if (offers.size() == 0) {
            offers.add(sheepmentOffer);
        }
    }

    public Double calculateDeliveryCost() {
        Double deliveryCost = basePrice + (weight * 10) + (distanceInKm * 5);
        for (IOffer offer : offers) {
            deliveryCost -= offer.calculateDiscount(deliveryCost);
        }
        return deliveryCost;
    }
}
