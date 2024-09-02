package org.KikiCourier.Offer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OfferTest {

    private Offer offer;

    @BeforeEach
    public void setUp() {
        OfferRule offerRule = new OfferRule(70, 200, 0, 200);
        offer = new Offer("OFR01", 4, offerRule);
    }

    @Test
    public void calculateDiscount_shouldCalculateDiscount() {
        Double discount = offer.calculateDiscount(200.00);
        assertEquals(8, discount);
    }

    @Test
    public void isValid_shouldReturnTrueIfOfferCodeIsValid() {
        assertTrue(offer.isValid("OFR01"));
    }

    @Test
    public void isValid_shouldReturnFalseIfOfferCodeIsInValid() {
        assertFalse(offer.isValid("OFR02"));
    }

    @Test
    public void isValid_shouldReturnFalseIfOfferCodeIsNull() {
        assertFalse(offer.isValid(null));
    }

    @Test
    public void isApplicable_shouldReturnTrueIfOfferIsNotApplicable() {
        assertTrue(offer.isApplicable(80, 10));
    }

    @Test
    public void isApplicable_shouldReturnTrueIfOfferIsApplicable() {
        assertFalse(offer.isApplicable(60, 10));
    }
}