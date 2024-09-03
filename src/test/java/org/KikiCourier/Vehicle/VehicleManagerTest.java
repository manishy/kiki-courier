package org.KikiCourier.Vehicle;

import org.KikiCourier.Shipment.ShipmentPackage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VehicleManagerTest {

    private VehicleManager vehicleManager;
    private static final int NUMBER_OF_VEHICLES = 3;
    private static final double MAX_SPEED = 70.0;
    private static final int MAX_CARRIABLE_WEIGHT = 200;

    @BeforeEach
    void setUp() {
        vehicleManager = new VehicleManager(NUMBER_OF_VEHICLES, MAX_SPEED, MAX_CARRIABLE_WEIGHT);
    }

    @Test
    public void getAvailableVehicle_shouldReturnNextAvailableVehicle() {
        assertNotNull(vehicleManager.getAvailableVehicle());
    }

    @Test
    public void getAvailableVehicle_shouldNotReturnAnyAvailableVehicleIfNotAvailable() {
        vehicleManager = new VehicleManager(0, MAX_SPEED, MAX_CARRIABLE_WEIGHT);
        assertNull(vehicleManager.getAvailableVehicle());
    }

    @Test
    void completeShipment_shouldCompleteTheShipmentAndUpdateTheVehicleAvailability() {
        Vehicle availableVehicle = vehicleManager.getAvailableVehicle();
        availableVehicle.loadPackage(new ShipmentPackage("ID1", 12.0, 80, 100));
        vehicleManager.completeShipment(availableVehicle);

        assertEquals(2.86, availableVehicle.getNextAvailabilityInHour());
    }
}