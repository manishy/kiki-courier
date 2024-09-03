package org.KikiCourier.Vehicle;

import org.KikiCourier.Shipment.ShipmentPackage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Set;

public class Vehicle {
    private final double maxSpeed;
    private final int maxCarriableWeight;
    private final Set<ShipmentPackage> shipmentPackages;

    private double nextAvailableTime;

    public Vehicle(double maxSpeed, int maxCarriableWeight) {
        this.maxSpeed = maxSpeed;
        this.maxCarriableWeight = maxCarriableWeight;
        this.shipmentPackages = new HashSet<>();
        this.nextAvailableTime = 0.0;
    }

    public boolean loadPackage(ShipmentPackage shipmentPackage) {
        float currentWeight = shipmentPackages.stream().map(ShipmentPackage::getWeightInKg).reduce(0, Integer::sum);
        if (shipmentPackage.getWeightInKg() + currentWeight <= maxCarriableWeight) {
            return shipmentPackages.add(shipmentPackage);
        }
        return false;
    }

    public double getNextAvailabilityInHour() {
        BigDecimal bigDecimal = new BigDecimal(nextAvailableTime).setScale(2, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }

    public void completeShipment() {
        setAvailability();
        unloadPackages();
    }

    public void updateEstimatedTimeInPackages() {
        double estimatedDeliveryTime = calculateEstimatedDeliveryTime();
        for (ShipmentPackage shipmentPackage : shipmentPackages) {
            shipmentPackage.setEstimatedDeliveryTime(estimatedDeliveryTime);
        }
    }

    private void unloadPackages() {
        shipmentPackages.clear();
    }

    private void setAvailability() {
        int maxDistance = shipmentPackages.stream()
                .mapToInt(ShipmentPackage::getDistanceInKm)
                .max()
                .orElse(0);

        double tripTime = calculateTripTime(maxDistance);
        setNextAvailableTime(getNextAvailabilityInHour() + tripTime);
    }

    private void setNextAvailableTime(double nextAvailableTime) {
        this.nextAvailableTime = nextAvailableTime;
    }

    private double calculateTripTime(int distance) {
        return (2.0 * distance) / maxSpeed;
    }

    private double calculateEstimatedDeliveryTime() {
        int maxDistance = shipmentPackages.stream()
                .mapToInt(ShipmentPackage::getDistanceInKm)
                .max()
                .orElse(0);
        double tripTime = maxDistance / maxSpeed;
        BigDecimal bigDecimal = new BigDecimal(nextAvailableTime + tripTime).setScale(2, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }
}
