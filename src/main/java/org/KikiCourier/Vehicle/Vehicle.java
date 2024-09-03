package org.KikiCourier.Vehicle;

import org.KikiCourier.Shipment.Shipment;
import org.KikiCourier.Shipment.ShipmentPackage;

public class Vehicle {
    private final double maxSpeed;
    private final int maxCarriableWeight;

    private double nextAvailableTime;

    public Vehicle(double maxSpeed, int maxCarriableWeight) {
        this.maxSpeed = maxSpeed;
        this.maxCarriableWeight = maxCarriableWeight;
        this.nextAvailableTime = 0.0;
    }
    public double getNextAvailableTime() {
        return nextAvailableTime;
    }

    public void setNextAvailableTime(double nextAvailableTime) {
        this.nextAvailableTime = nextAvailableTime;
    }

    public double calculateTripTime(int distance) {
        return (2.0 * distance) / maxSpeed;
    }
    public boolean canLoad(int weight) {
        return weight <= maxCarriableWeight;
    }

    public double calculateEstimatedDeliveryTime(Shipment shipment) {
        int maxDistance = shipment.getPackages().stream()
                .mapToInt(ShipmentPackage::getDistanceInKm)
                .max()
                .orElse(0);
        double tripTime = maxDistance / maxSpeed;
        return nextAvailableTime + tripTime;
    }
}
