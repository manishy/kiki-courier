package org.KikiCourier.Vehicle;

import java.util.Comparator;
import java.util.PriorityQueue;

public class VehicleManager {
    private final PriorityQueue<Vehicle> availableVehicles;

    private VehicleManager(int numberOfVehicles, double maxSpeed, int maxCarriableWeight) {
        this.availableVehicles = new PriorityQueue<>(Comparator.comparingDouble(Vehicle::getNextAvailabilityInHour));
        initializeVehicles(numberOfVehicles, maxSpeed, maxCarriableWeight);
    }

    public static VehicleManager create(String[] vehicleInstructions) {
        int numberOfVehicles = Integer.parseInt(vehicleInstructions[0]);
        double maxSpeed = Double.parseDouble(vehicleInstructions[1]);
        int maxCarriableWeight = Integer.parseInt(vehicleInstructions[2]);
        return new VehicleManager(numberOfVehicles, maxSpeed, maxCarriableWeight);
    }

    private void initializeVehicles(int numberOfVehicles, double maxSpeed, int maxCarriableWeight) {
        for (int i = 0; i < numberOfVehicles; i++) {
            Vehicle vehicle = new Vehicle(maxSpeed, maxCarriableWeight);
            availableVehicles.add(vehicle);
        }
    }

    public Vehicle getAvailableVehicle() {
        return availableVehicles.poll();
    }

    private void returnVehicle(Vehicle vehicle) {
        availableVehicles.add(vehicle);
    }

    public void completeShipment(Vehicle vehicle) {
        vehicle.completeShipment();
        returnVehicle(vehicle);
    }
}
