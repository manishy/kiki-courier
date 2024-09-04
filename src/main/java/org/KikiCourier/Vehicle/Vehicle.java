package org.KikiCourier.Vehicle;

import org.KikiCourier.Shipment.ShipmentPackage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;
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

    public void loadPackages(List<ShipmentPackage> optimalPackages) {
        shipmentPackages.addAll(optimalPackages);
    }

    public double getNextAvailabilityInHour() {
        return truncateToTwoDecimalPlaces(nextAvailableTime);
    }

    public void completeShipment() {
        setAvailability();
        unloadPackages();
    }

    public void updateEstimatedTimeInPackages() {
        for (ShipmentPackage shipmentPackage : shipmentPackages) {
            double estimatedDeliveryTime = calculateEstimatedDeliveryTime(shipmentPackage.getDistanceInKm());
            shipmentPackage.setEstimatedDeliveryTime(estimatedDeliveryTime);
        }
    }
    public int getMaxCarriableWeight(){
        return maxCarriableWeight;
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
        setNextAvailableTime(Double.sum(getNextAvailabilityInHour(), tripTime));
    }

    private void setNextAvailableTime(double nextAvailableTime) {
        this.nextAvailableTime = nextAvailableTime;
    }

    private double calculateTripTime(int distance) {
        double tripTime = (2.0 * distance) / maxSpeed;
        double toTwoDecimalPlaces = truncateToTwoDecimalPlaces(tripTime);
        return Double.sum(nextAvailableTime, toTwoDecimalPlaces);
    }

    private double calculateEstimatedDeliveryTime(int distance) {
        double tripTime = distance / maxSpeed;
        return Double.sum(truncateToTwoDecimalPlaces(tripTime), nextAvailableTime);
    }

    private double truncateToTwoDecimalPlaces(double value) {
        return new BigDecimal(value).setScale(2, RoundingMode.DOWN).doubleValue();
    }
}
