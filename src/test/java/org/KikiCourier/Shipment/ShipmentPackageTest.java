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
    }

    @Test
    public void getShipmentPricing_shouldCalculateDeliveryCostWhenNoOfferIsApplied() {
        ShipmentPackage shipmentPackage = new ShipmentPackage("PKG1", 100.00, 15, 5);
        ShipmentPricingSummary shipmentPricing = shipmentPackage.getShipmentPricing();
        assertEquals(275, shipmentPricing.getTotalCost());
        assertEquals(0.0, shipmentPricing.getDiscount());
    }

    @Test
    public void applyOffer_shouldApplyDiscountAfterAnOfferIsApplied() {
        ShipmentPackage shipmentPackage = new ShipmentPackage("PKG1", 100.00, 15, 5);
        Offer ofr001 = new Offer("OFR001", 10, null);
        shipmentPackage.applyOffer(ofr001);
        ShipmentPricingSummary shipmentPricing = shipmentPackage.getShipmentPricing();
        assertEquals(247.5, shipmentPricing.getTotalCost());
        assertEquals(27.5, shipmentPricing.getDiscount());
    }

    @Test
    public void applyOffer_shouldNotApplyDiscountForMultipleOffers() {
        ShipmentPackage shipmentPackage = new ShipmentPackage("PKG1", 100.00, 15, 5);
        Offer ofr001 = new Offer("OFR001", 10, null);
        Offer ofr002 = new Offer("OFR002", 7, null);
        shipmentPackage.applyOffer(ofr001);
        shipmentPackage.applyOffer(ofr002);
        ShipmentPricingSummary shipmentPricing = shipmentPackage.getShipmentPricing();
        assertEquals(247.5, shipmentPricing.getTotalCost());
        assertEquals(27.5, shipmentPricing.getDiscount());
    }
}