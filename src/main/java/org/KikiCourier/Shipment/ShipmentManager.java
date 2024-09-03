package org.KikiCourier.Shipment;

import org.KikiCourier.Offer.OfferManager;
import org.KikiCourier.Vehicle.Vehicle;
import org.KikiCourier.Vehicle.VehicleManager;

import java.util.ArrayList;
import java.util.List;

public class ShipmentManager {
    private final OfferManager offerManager;
    private final double basePrice;
    private final VehicleManager vehicleManager;

    private final List<ShipmentPackage> shipmentPackages;

    public ShipmentManager(OfferManager offerManager, double basePrice, VehicleManager vehicleManager) {
        this.offerManager = offerManager;
        this.basePrice = basePrice;
        this.vehicleManager = vehicleManager;
        this.shipmentPackages = new ArrayList<>();
    }

    public List<ShipmentPackage> createShipmentPackagesWithEstimatedDeliveryTime() {
        List<ShipmentPackage> shipments = new ArrayList<>(shipmentPackages);

        shipmentPackages.sort((p1, p2) -> {
            int weightComparison = Integer.compare(p2.getWeightInKg(), p1.getWeightInKg());
            if (weightComparison != 0) {
                return weightComparison;
            }
            return Integer.compare(p1.getDistanceInKm(), p2.getDistanceInKm());
        });
        while (!shipmentPackages.isEmpty()) {
            Vehicle availableVehicle = vehicleManager.getAvailableVehicle();
            shipmentPackages.removeIf(availableVehicle::loadPackage);
            availableVehicle.updateEstimatedTimeInPackages();
            vehicleManager.completeShipment(availableVehicle);
        }
        return shipments;
    }

    public void preparePackagesForShipment(List<String> instructions) {
        for (String instruction : instructions) {
            try {
                ShipmentPackage shipmentPackage = preparePackageFromInstruction(instruction);
                shipmentPackages.add(shipmentPackage);
            } catch (Exception e) {
                System.err.println("Error processing shipment: " + instruction);
            }
        }
    }

    private ShipmentPackage preparePackageFromInstruction(String instruction) {
        String[] split = instruction.split(" ");
        String packageId = split[0];
        int packageWeightInKg = Integer.parseInt(split[1]);
        int distanceInKm = Integer.parseInt(split[2]);
        String offerCode = split[3];

        return preparePackage(packageId, packageWeightInKg, distanceInKm, offerCode);
    }

    private ShipmentPackage preparePackage(String id, int weight, int distanceInKm, String offerCode) {
        ShipmentPackage shipmentPackage = new ShipmentPackage(id, basePrice, weight, distanceInKm);
        if (offerManager.isValidOffer(offerCode) && offerManager.isApplicable(offerCode, weight, distanceInKm)) {
            shipmentPackage.applyOffer(offerManager.getOfferBy(offerCode));
        }

        return shipmentPackage;
    }
}
