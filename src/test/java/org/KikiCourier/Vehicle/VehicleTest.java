package org.KikiCourier.Vehicle;

import org.KikiCourier.Shipment.ShipmentPackage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VehicleTest {
    Vehicle vehicle;
    ShipmentPackage shipmentPackage;

    @BeforeEach
    void setUp() {
        vehicle = new Vehicle(10, 100);
        shipmentPackage = new ShipmentPackage("ID1", 12.0, 80, 100);
    }

    @Test
    void completeShipment_shouldSetTheVehicleAvailabilityBasedOnThePackages() {
        vehicle.loadPackages(Collections.singletonList(shipmentPackage));

        vehicle.completeShipment();

        assertEquals(20.0, vehicle.getNextAvailabilityInHour());
    }

    @Test
    void updateEstimatedTimeInPackages_shouldUpdateDeliveryTimeInPackages() {
        vehicle.loadPackages(Collections.singletonList(shipmentPackage));
        vehicle.updateEstimatedTimeInPackages();

        assertEquals("ID1 0 1312 10.00", shipmentPackage.toString());
    }
}