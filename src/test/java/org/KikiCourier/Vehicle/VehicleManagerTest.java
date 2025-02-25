package org.KikiCourier.Vehicle;

import org.KikiCourier.Shipment.ShipmentPackage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class VehicleManagerTest {

    private VehicleManager vehicleManager;
    private static final int NUMBER_OF_VEHICLES = 3;
    private static final double MAX_SPEED = 70.0;
    private static final int MAX_CARRIABLE_WEIGHT = 200;

    @BeforeEach
    void setUp() {
        vehicleManager = VehicleManager.create(new String[]{String.valueOf(NUMBER_OF_VEHICLES), String.valueOf(MAX_SPEED), String.valueOf(MAX_CARRIABLE_WEIGHT)});
    }

    @Test
    public void getAvailableVehicle_shouldReturnNextAvailableVehicle() {
        assertNotNull(vehicleManager.getAvailableVehicle());
    }

    @Test
    public void getAvailableVehicle_shouldNotReturnAnyAvailableVehicleIfNotAvailable() {
        VehicleManager vehicleManager = VehicleManager.create(new String[]{"0", String.valueOf(MAX_SPEED), String.valueOf(MAX_CARRIABLE_WEIGHT)});
        assertNull(vehicleManager.getAvailableVehicle());
    }

    @Test
    void completeShipment_shouldCompleteTheShipmentAndUpdateTheVehicleAvailability() {
        Vehicle availableVehicle = vehicleManager.getAvailableVehicle();
        availableVehicle.loadPackages(Collections.singletonList(new ShipmentPackage("ID1", 12.0, 80, 100)));
        vehicleManager.completeShipment(availableVehicle);

        assertEquals(2.85, availableVehicle.getNextAvailabilityInHour());
    }
}