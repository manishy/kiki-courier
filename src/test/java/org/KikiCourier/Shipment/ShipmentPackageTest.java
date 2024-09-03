package org.KikiCourier.Shipment;

import org.KikiCourier.Offer.Offer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShipmentPackageTest {

    @Test
    public void shouldInitialiseValues() {
        ShipmentPackage shipmentPackage = new ShipmentPackage("PKG1", 100.00, 15, 5);
        assertEquals(15, shipmentPackage.getWeightInKg());
        assertEquals(5, shipmentPackage.getDistanceInKm());
        assertEquals("PKG1", shipmentPackage.getId());
    }

    @Test
    public void applyOffer_shouldApplyDiscountAfterAnOfferIsApplied() {
        ShipmentPackage shipmentPackage = new ShipmentPackage("PKG1", 100.00, 15, 5);
        Offer ofr001 = new Offer("OFR001", 10, null);
        shipmentPackage.applyOffer(ofr001);
        assertEquals("PKG1 28 248 0.00", shipmentPackage.toString());
    }

    @Test
    public void applyOffer_shouldNotApplyDiscountForMultipleOffers() {
        ShipmentPackage shipmentPackage = new ShipmentPackage("PKG1", 100.00, 15, 5);
        Offer ofr001 = new Offer("OFR001", 10, null);
        Offer ofr002 = new Offer("OFR002", 7, null);
        shipmentPackage.applyOffer(ofr001);
        shipmentPackage.applyOffer(ofr002);

        assertEquals("PKG1 28 248 0.00", shipmentPackage.toString());
    }
}