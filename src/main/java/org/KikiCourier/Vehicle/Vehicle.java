package org.KikiCourier.Vehicle;

import org.KikiCourier.Shipment.ShipmentPackage;

import java.util.ArrayList;
import java.util.List;

public class Vehicle {
    private final double maxSpeed;
    private final int maxCarriableWeight;

    private final List<ShipmentPackage> shipmentPackages = new ArrayList<>();
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

    public boolean loadPackage(ShipmentPackage shipmentPackage) {
        float currentWeight = shipmentPackages.stream().map(ShipmentPackage::getWeightInKg).reduce(0, Integer::sum);
        if (shipmentPackage.getWeightInKg() + currentWeight <= maxCarriableWeight) {
            return shipmentPackages.add(shipmentPackage);
        }
        return false;
    }

    public void unloadPackages() {
        shipmentPackages.clear();
    }

    public List<ShipmentPackage> getShipmentPackages() {
        return shipmentPackages;
    }
}
