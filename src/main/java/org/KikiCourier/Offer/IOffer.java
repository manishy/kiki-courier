package org.KikiCourier.Offer;

public interface IOffer {
    Double calculateDiscount(Double accumulatedCost);
    boolean isApplicable(int weight, int distanceInKm);
    boolean isValid(String offerCode);
}
