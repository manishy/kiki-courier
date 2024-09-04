package org.KikiCourier.Utils;

import org.KikiCourier.Shipment.ShipmentPackage;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShipmentsGeneratorTest {

    @Test
    void getAllSubsets_shouldReturnListOfAllThePossibleSubsets() {
        ShipmentPackage shipmentPackage1 = new ShipmentPackage("PKG1", 220.00, 24, 300);
        ShipmentPackage shipmentPackage2 = new ShipmentPackage("PKG2", 220.00, 24, 300);
        ShipmentPackage shipmentPackage3 = new ShipmentPackage("PKG3", 220.00, 24, 300);
        List<ShipmentPackage> shipmentPackages = Arrays.asList(shipmentPackage1, shipmentPackage2, shipmentPackage3);
        ShipmentsGenerator shipmentsGenerator = new ShipmentsGenerator();
        List<List<ShipmentPackage>> allPossibleShipments = shipmentsGenerator.getAllPossibleShipments(shipmentPackages);
        assertEquals(8, allPossibleShipments.size());
        assertTrue(allPossibleShipments.contains(Collections.singletonList(shipmentPackage1)));
        assertTrue(allPossibleShipments.contains(Collections.singletonList(shipmentPackage2)));
        assertTrue(allPossibleShipments.contains(Collections.singletonList(shipmentPackage3)));
        assertTrue(allPossibleShipments.contains(Arrays.asList(shipmentPackage1, shipmentPackage2)));
        assertTrue(allPossibleShipments.contains(Arrays.asList(shipmentPackage1, shipmentPackage2, shipmentPackage3)));
        assertTrue(allPossibleShipments.contains(Arrays.asList(shipmentPackage2, shipmentPackage3)));
        assertTrue(allPossibleShipments.contains(Arrays.asList(shipmentPackage1, shipmentPackage3)));
    }
}