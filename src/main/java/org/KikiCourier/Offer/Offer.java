package org.KikiCourier.Offer;

public class Offer implements IOffer {
    private final String code;
    private final float discountPercentage;

    public Offer(String code, float discountPercentage) {
        this.code = code;
        this.discountPercentage = discountPercentage;
    }
    @Override
    public Double calculateDiscount(Double cost) {
        return cost * discountPercentage / 100;
    }
}
