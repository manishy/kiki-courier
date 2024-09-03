package org.KikiCourier.Vehicle;

import org.KikiCourier.Shipment.Shipment;
import org.KikiCourier.Shipment.ShipmentPackage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VehicleTest {
    Vehicle vehicle;

    @BeforeEach
    void setUp() {
        vehicle = new Vehicle(10, 100);
    }

    @Test
    void setNextAvailableTime_shouldSetNextAvailableTimeForTheVehicle() {
        vehicle.setNextAvailableTime(10.00);
        assertEquals(10.00, vehicle.getNextAvailableTime());
    }

    @Test
    void calculateTripTime_shouldCalculateTripTimeForAGivenDistance() {
        double tripTime = vehicle.calculateTripTime(120);
        assertEquals(24, tripTime);
    }

    @Test
    void canLoad_shouldReturnTrueIfVehicleCanLoadTheWeight() {
        assertTrue(vehicle.canLoad(90));
    }

    @Test
    void canLoad_shouldReturnFalseIfVehicleCannotLoadTheWeight() {
        assertFalse(vehicle.canLoad(110));
    }

    @Test
    void calculateEstimatedDeliveryTime_shouldCalculateDeliveryTime() {
        ShipmentPackage shipmentPackage1 = new ShipmentPackage("ID1", 20.0, 100, 120);
        ShipmentPackage shipmentPackage2 = new ShipmentPackage("ID2", 20.0, 100, 135);
        List<ShipmentPackage> packages = Arrays.asList(shipmentPackage1, shipmentPackage2);
        Shipment shipment = new Shipment(packages);
        double estimatedDeliveryTime = vehicle.calculateEstimatedDeliveryTime(shipment);
        assertEquals(13.5, estimatedDeliveryTime);
    }
}