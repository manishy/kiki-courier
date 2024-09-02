package org.KikiCourier.Vehicle;

import org.KikiCourier.Shipment.ShipmentPackage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VehicleTest {

    @Test
    void loadPackage_shouldReturnTrueIfPackageIsLoaded() {
        Vehicle vehicle = new Vehicle(10, 100);
        assertTrue(vehicle.loadPackage(new ShipmentPackage("ID1", 12.0, 24, 100)));
    }

    @Test
    void loadPackage_shouldReturnFalseIfPackageWeightIsMoreThanTheCurrentlyLoadedShipmentPackagesWeight() {
        Vehicle vehicle = new Vehicle(10, 100);
        vehicle.loadPackage(new ShipmentPackage("ID1", 12.0, 80, 100));
        assertFalse(vehicle.loadPackage(new ShipmentPackage("ID2", 12.0, 50, 100)));
    }

    @Test
    void unloadPackages_shouldUnloadAllThePackages() {
        Vehicle vehicle = new Vehicle(10, 100);
        vehicle.loadPackage(new ShipmentPackage("ID1", 12.0, 80, 100));

        assertEquals(1, vehicle.getShipmentPackages().size());

        vehicle.unloadPackages();
        assertEquals(0, vehicle.getShipmentPackages().size());
    }

    @Test
    void setNextAvailableTime_shouldSetNextAvailableTimeForTheVehicle() {
        Vehicle vehicle = new Vehicle(10, 100);
        vehicle.setNextAvailableTime(10.00);
        assertEquals(10.00, vehicle.getNextAvailableTime());
    }

    @Test
    void calculateTripTime_shouldCalculateTripTimeForAGivenDistance() {
        Vehicle vehicle = new Vehicle(10, 100);
        double tripTime = vehicle.calculateTripTime(120);
        assertEquals(24, tripTime);
    }
}