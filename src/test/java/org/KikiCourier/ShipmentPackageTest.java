package org.KikiCourier;

import org.KikiCourier.Offer.Offer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShipmentPackageTest {

    @Test
    public void calculateDeliveryCost_shouldCalculateDeliveryCostWhenNoOfferIsApplied() {
        ShipmentPackage shipmentPackage = new ShipmentPackage("PKG1", 100.00, 15, 5);
        Double deliveryCost = shipmentPackage.calculateDeliveryCost();
        assertEquals(275, deliveryCost);
    }

    @Test
    public void applyOffer_shouldApplyDiscountAfterAnOfferIsApplied() {
        ShipmentPackage shipmentPackage = new ShipmentPackage("PKG1", 100.00, 15, 5);
        Offer ofr001 = new Offer("OFR001", 10);
        shipmentPackage.applyOffer(ofr001);
        Double deliveryCost = shipmentPackage.calculateDeliveryCost();
        assertEquals(247.5, deliveryCost);
    }

    @Test
    public void applyOffer_shouldNotApplyDiscountForMultipleOffers() {
        ShipmentPackage shipmentPackage = new ShipmentPackage("PKG1", 100.00, 15, 5);
        Offer ofr001 = new Offer("OFR001", 10);
        Offer ofr002 = new Offer("OFR002", 7);
        shipmentPackage.applyOffer(ofr001);
        shipmentPackage.applyOffer(ofr002);
        Double deliveryCost = shipmentPackage.calculateDeliveryCost();
        assertEquals(247.5, deliveryCost);
    }
}