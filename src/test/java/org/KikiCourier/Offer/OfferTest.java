package org.KikiCourier.Offer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OfferTest {

    @Test
    public void calculateDiscount_shouldCalculateDiscount() {
        Offer ofr01 = new Offer("OFR01", 4, null);
        Double discount = ofr01.calculateDiscount(200.00);
        assertEquals(8, discount);
    }

    @Test
    public void isValid_shouldReturnTrueIfOfferCodeIsNotNullAndValid() {
        Offer ofr01 = new Offer("OFR01", 4, null);
        assertTrue(ofr01.isValid("OFR01"));
    }

    @Test
    public void isValid_shouldReturnFalseIfOfferCodeIsNotNullAndInValid() {
        Offer ofr01 = new Offer("OFR01", 4, null);
        assertFalse(ofr01.isValid("OFR02"));
    }

    @Test
    public void isValid_shouldReturnFalseIfOfferCodeIsNull() {
        Offer ofr01 = new Offer(null, 4, null);
        assertFalse(ofr01.isValid("OFR02"));
    }

    @Test
    public void isApplicable_shouldReturnTrueIfOfferIsNotApplicable() {
        OfferRule offerRule = new OfferRule(70, 200, 0, 200);
        Offer ofr01 = new Offer("OFR01", 4, offerRule);
        assertTrue(ofr01.isApplicable(80, 10));
    }

    @Test
    public void isApplicable_shouldReturnTrueIfOfferIsApplicable() {
        OfferRule offerRule = new OfferRule(70, 200, 0, 200);
        Offer ofr01 = new Offer("OFR01", 4, offerRule);
        assertFalse(ofr01.isApplicable(60, 10));
    }
}