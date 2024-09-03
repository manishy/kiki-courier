package org.KikiCourier.Vehicle;

import org.KikiCourier.Shipment.ShipmentPackage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VehicleTest {
    Vehicle vehicle;
    ShipmentPackage shipmentPackage;

    @BeforeEach
    void setUp() {
        vehicle = new Vehicle(10, 100);
        shipmentPackage = new ShipmentPackage("ID1", 12.0, 80, 100);
    }

    @Test
    void loadPackage_shouldReturnTrueIfPackageIsLoaded() {
        assertTrue(vehicle.loadPackage(shipmentPackage));
    }

    @Test
    void loadPackage_shouldReturnFalseIfPackageWeightIsMoreThanTheCurrentlyLoadedShipmentPackagesWeight() {
        vehicle.loadPackage(shipmentPackage);
        assertFalse(vehicle.loadPackage(new ShipmentPackage("ID2", 12.0, 50, 100)));
    }

    @Test
    void completeShipment_shouldSetTheVehicleAvailabilityBasedOnThePackages() {
        vehicle.loadPackage(shipmentPackage);

        vehicle.completeShipment();

        assertEquals(20.0, vehicle.getNextAvailabilityInHour());
    }

    @Test
    void updateEstimatedTimeInPackages_shouldUpdateDeliveryTimeInPackages() {
        vehicle.loadPackage(shipmentPackage);
        vehicle.updateEstimatedTimeInPackages();

        assertEquals("ID1 0 1312 10.00", shipmentPackage.toString());
    }
}