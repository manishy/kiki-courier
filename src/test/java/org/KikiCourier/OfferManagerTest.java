package org.KikiCourier;

import org.KikiCourier.Offer.IOffer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OfferManagerTest {

    @Test
    public void validateOffer_shouldReturnTrueIfOfferIsValid() {
        OfferManager offerManager = new OfferManager();
        assertTrue(offerManager.isValidOffer("OFR001"));
    }

    @Test
    public void validateOffer_shouldReturnFalseIfOfferIsNotValid() {
        OfferManager offerManager = new OfferManager();
        assertFalse(offerManager.isValidOffer("OFR004"));
    }

    @Test
    public void getOffer_shouldReturnOfferById() {
        OfferManager offerManager = new OfferManager();
        IOffer offer = offerManager.getOfferBy("OFR001");
        assertTrue(offer.isValid("OFR001"));
    }

    @Test
    public void getOffer_shouldNotReturnOfferIfIdIsInvalid() {
        OfferManager offerManager = new OfferManager();
        IOffer offer = offerManager.getOfferBy("OFR004");
        assertNull(offer);
    }

    @Test
    public void isApplicable_shouldNotReturnTrueIfOfferIsApplicable() {
        OfferManager offerManager = new OfferManager();
        boolean isApplicable = offerManager.isApplicable("OFR001", 100, 100);
        assertTrue(isApplicable);
    }

    @Test
    public void isApplicable_shouldNotReturnFalseIfOfferIsNotApplicable() {
        OfferManager offerManager = new OfferManager();
        boolean isApplicable = offerManager.isApplicable("OFR001", 500, 100);
        assertFalse(isApplicable);
    }

    @Test
    public void isApplicable_shouldNotReturnFalseIfOfferCodeIsNotFound() {
        OfferManager offerManager = new OfferManager();
        boolean isApplicable = offerManager.isApplicable("OFR005", 500, 100);
        assertFalse(isApplicable);
    }
}