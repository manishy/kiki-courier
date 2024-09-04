package org.KikiCourier.Shipment;

import org.KikiCourier.Offer.Offer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShipmentPackageTest {

    ShipmentPackage shipmentPackage;

    @BeforeEach
    void setUp() {
        shipmentPackage = new ShipmentPackage("PKG1", 100.00, 15, 5);
    }

    @Test
    public void shouldInitialiseValues() {
        assertEquals(15, shipmentPackage.getWeightInKg());
        assertEquals(5, shipmentPackage.getDistanceInKm());
        assertEquals("PKG1", shipmentPackage.getId());
    }

    @Test
    public void applyOffer_shouldApplyDiscountAfterAnOfferIsApplied() {
        Offer ofr001 = new Offer("OFR001", 10, null);
        shipmentPackage.applyOffer(ofr001);
        assertEquals("PKG1 28 248 0.00", shipmentPackage.toString());
    }

    @Test
    public void applyOffer_shouldNotApplyDiscountForMultipleOffers() {
        Offer ofr001 = new Offer("OFR001", 10, null);
        Offer ofr002 = new Offer("OFR002", 7, null);
        shipmentPackage.applyOffer(ofr001);
        shipmentPackage.applyOffer(ofr002);

        assertEquals("PKG1 28 248 0.00", shipmentPackage.toString());
    }
}