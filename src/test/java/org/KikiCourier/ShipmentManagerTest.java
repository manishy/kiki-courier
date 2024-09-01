package org.KikiCourier;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShipmentManagerTest {

    @Test
    public void createShipment_createsShipmentWithoutAnyOffer() {
        OfferManager offerManager = new OfferManager();
        ShipmentManager shipmentManager = new ShipmentManager(offerManager);
        ShipmentPackage shipment = shipmentManager.createShipment("ID1", 100.00, 20, 120, null);
        Double deliveryCost = shipment.calculateDeliveryCost();
        assertEquals(900.0, deliveryCost);
    }

    @Test
    public void createShipment_createsShipmentWithAnOffer() {
        OfferManager offerManager = new OfferManager();
        ShipmentManager shipmentManager = new ShipmentManager(offerManager);
        ShipmentPackage shipment = shipmentManager.createShipment("ID1", 100.00, 20, 120, "OFR001");
        Double deliveryCost = shipment.calculateDeliveryCost();
        assertEquals(810.0, deliveryCost);
    }
}