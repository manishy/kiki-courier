package org.KikiCourier.Shipment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShipmentTest {
    List<ShipmentPackage> shipmentPackages;

    @BeforeEach
    void setUp() {
        ShipmentPackage shipmentPackage1 = new ShipmentPackage("ID1", 20.0, 100, 120);
        ShipmentPackage shipmentPackage2 = new ShipmentPackage("ID2", 20.0, 100, 120);
        shipmentPackages = Arrays.asList(shipmentPackage1, shipmentPackage2);
    }

    @Test
    void getPackages_shouldReturnListOfPackages() {
        Shipment shipment = new Shipment(shipmentPackages);
        assertEquals(2, shipment.getPackages().size());
    }

    @Test
    void toString_shouldReturnAllTheValuesForAllThePackagesWithEstimatedDeliveryTime() {
        Shipment shipment = new Shipment(shipmentPackages);
        shipment.setEstimatedDeliveryTime(12);
        assertEquals("ID1 0.0 1620.0 12.00\n" +
                "ID2 0.0 1620.0 12.00\n", shipment.toString());
    }

    @Test
    void toString_shouldReturnAllTheValuesForAllThePackagesWithoutEstimatedDeliveryTime() {
        Shipment shipment = new Shipment(shipmentPackages);
        assertEquals("ID1 0.0 1620.0 0.00\n" +
                "ID2 0.0 1620.0 0.00\n", shipment.toString());
    }
}