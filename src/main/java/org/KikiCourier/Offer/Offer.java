package org.KikiCourier.Offer;

public class Offer implements IOffer {
    private final String code;
    private final float discountPercentage;
    private final OfferRule offerRule;

    public Offer(String code, float discountPercentage, OfferRule offerRule) {
        this.code = code;
        this.discountPercentage = discountPercentage;
        this.offerRule = offerRule;
    }

    @Override
    public Double calculateDiscount(Double cost) {
        return cost * discountPercentage / 100;
    }
    @Override
    public boolean isApplicable(int weight, int distanceInKm) {
        return offerRule.isWithinRange(weight, distanceInKm);
    }
    @Override
    public boolean isValid(String offerCode) {
        return offerCode != null && offerCode.equals(code);
    }
}
