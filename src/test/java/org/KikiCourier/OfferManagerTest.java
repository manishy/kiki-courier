package org.KikiCourier;

import org.KikiCourier.Offer.Offer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OfferManagerTest {

    @Test
    public void validateOffer_shouldReturnTrueIfOfferIsValid() {
        OfferManager offerManager = new OfferManager();
        assertTrue(offerManager.validateOffer("OFR001"));
    }

    @Test
    public void validateOffer_shouldReturnFalseIfOfferIsNotValid() {
        OfferManager offerManager = new OfferManager();
        assertFalse(offerManager.validateOffer("OFR004"));
    }

    @Test
    public void getOffer_shouldReturnOfferById() {
        OfferManager offerManager = new OfferManager();
        Offer offer = offerManager.getOfferBy("OFR001");
        assertTrue(offer.isValid("OFR001"));
    }

    @Test
    public void getOffer_shouldNotReturnOfferIfIdIsInvalid() {
        OfferManager offerManager = new OfferManager();
        Offer offer = offerManager.getOfferBy("OFR004");
        assertNull(offer);
    }
}