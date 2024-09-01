package org.KikiCourier.Offer;

public class OfferRule {
    private final int minDistance;
    private final int maxDistance;
    private final int minWeight;
    private final int maxWeight;

    public OfferRule(int minWeight, int maxWeight, int minDistance, int maxDistance) {
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.minWeight = minWeight;
        this.maxWeight = maxWeight;
    }

    public boolean isWithinRange(int weight, int distance) {
        return distance >= minDistance && distance <= maxDistance &&
                weight >= minWeight && weight <= maxWeight;
    }
}
