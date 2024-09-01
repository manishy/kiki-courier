package org.KikiCourier;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShipmentManagerTest {

    @Test
    public void createShipment_createsShipmentWithoutAnyOffer() {
        OfferManager offerManager = new OfferManager();
        ShipmentManager shipmentManager = new ShipmentManager(offerManager);
        ShipmentPackage shipment = shipmentManager.createShipment("ID1", 100.00, 20, 120, null);
        ShipmentPricingSummary shipmentPricing = shipment.getShipmentPricing();
        assertEquals(900.0, shipmentPricing.getActualCost());
        assertEquals(0.0, shipmentPricing.getDiscount());
    }

    @Test
    public void createShipment_createsShipmentWithAnOffer() {
        OfferManager offerManager = new OfferManager();
        ShipmentManager shipmentManager = new ShipmentManager(offerManager);
        ShipmentPackage shipment = shipmentManager.createShipment("ID1", 100.00, 20, 120, "OFR001");
        ShipmentPricingSummary shipmentPricing = shipment.getShipmentPricing();
        assertEquals(810.0, shipmentPricing.getActualCost());
        assertEquals(90.0, shipmentPricing.getDiscount());
    }
}