package org.KikiCourier.Vehicle;

import org.KikiCourier.Shipment.Shipment;
import org.KikiCourier.Shipment.ShipmentPackage;

import java.util.Comparator;
import java.util.PriorityQueue;

public class VehicleManager {
    private final PriorityQueue<Vehicle> availableVehicles;

    public VehicleManager(int numberOfVehicles, double maxSpeed, int maxCarriableWeight) {
        this.availableVehicles = new PriorityQueue<>(Comparator.comparingDouble(Vehicle::getNextAvailableTime));
        initializeVehicles(numberOfVehicles, maxSpeed, maxCarriableWeight);
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

    public void updateVehicleAvailability(Vehicle vehicle, double tripTime) {
        vehicle.setNextAvailableTime(vehicle.getNextAvailableTime() + tripTime);
        returnVehicle(vehicle);
    }

    public void completeShipment(Shipment shipment, Vehicle vehicle) {
        int maxDistance = shipment.getPackages().stream()
                .mapToInt(ShipmentPackage::getDistanceInKm)
                .max()
                .orElse(0);
        double tripTime = vehicle.calculateTripTime(maxDistance);
        updateVehicleAvailability(vehicle, tripTime);
    }
}
