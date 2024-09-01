package org.KikiCourier.Offer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OfferRuleTest {
    @Test
    public void isWithinRange_shouldReturnTrueIfWeightAndDistanceIsWithinTheRange() {
        OfferRule offerRule = new OfferRule(70, 200, 0, 200);
        assertTrue(offerRule.isWithinRange(70, 150));
        assertTrue(offerRule.isWithinRange(200, 150));
        assertTrue(offerRule.isWithinRange(100, 150));
        assertTrue(offerRule.isWithinRange(100, 0));
        assertTrue(offerRule.isWithinRange(100, 200));
    }

    @Test
    public void isWithinRange_shouldReturnFalseIfWeightAndDistanceIsNotWithinTheRange() {
        OfferRule offerRule = new OfferRule(70, 200, 1, 200);
        assertFalse(offerRule.isWithinRange(60, 150));
        assertFalse(offerRule.isWithinRange(210, 150));
        assertFalse(offerRule.isWithinRange(150, 210));
        assertFalse(offerRule.isWithinRange(150, 0));
    }
}