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
}