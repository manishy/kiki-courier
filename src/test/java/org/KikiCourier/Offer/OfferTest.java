package org.KikiCourier.Offer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OfferTest {

    @Test
    public void calculateDiscount_shouldCalculateDiscount() {
        Offer ofr01 = new Offer("OFR01", 4);
        Double discount = ofr01.calculateDiscount(200.00);
        assertEquals(8, discount);
    }

    @Test
    public void isValid_shouldReturnTrueIfOfferCodeIsValid() {
        Offer ofr01 = new Offer("OFR01", 4);
        assertTrue(ofr01.isValid("OFR01"));
    }

    @Test
    public void isValid_shouldReturnFalseIfOfferCodeIsInValid() {
        Offer ofr01 = new Offer("OFR01", 4);
        assertFalse(ofr01.isValid("OFR02"));
    }
}