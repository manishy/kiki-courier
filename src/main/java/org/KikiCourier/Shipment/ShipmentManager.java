package org.KikiCourier.Shipment;

import org.KikiCourier.Offer.OfferManager;
import org.KikiCourier.Utils.ShipmentsGenerator;
import org.KikiCourier.Vehicle.Vehicle;
import org.KikiCourier.Vehicle.VehicleManager;

import java.util.ArrayList;
import java.util.Collections;
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
            if (availableVehicle == null) {
                System.out.println("Shipments can't be processed as no vehicle is available!");
                break;
            }
            List<ShipmentPackage> optimalShipment = findOptimalShipment(availableVehicle.getMaxCarriableWeight());
            if (!optimalShipment.isEmpty()) {
                availableVehicle.loadPackages(optimalShipment);
                availableVehicle.updateEstimatedTimeInPackages();
                vehicleManager.completeShipment(availableVehicle);
                shipmentPackages.removeAll(optimalShipment);
            }
        }
        return shipments;
    }

    private List<ShipmentPackage> findOptimalShipment(int maxCarriableWeight) {
        List<ShipmentPackage> mostAppropriatePackage = Collections.emptyList();
        List<List<ShipmentPackage>> allPossibleShipments = ShipmentsGenerator.getAllPossibleShipments(shipmentPackages);
        for (List<ShipmentPackage> shipmentPackages : allPossibleShipments) {
            int overallWeightOfPackage = shipmentPackages.stream().mapToInt(ShipmentPackage::getWeightInKg).sum();
            int currentMaxWeight = mostAppropriatePackage.stream().mapToInt(ShipmentPackage::getWeightInKg).sum();
            if (overallWeightOfPackage <= maxCarriableWeight && overallWeightOfPackage > currentMaxWeight) {
                mostAppropriatePackage = shipmentPackages;
            }
        }
        return mostAppropriatePackage;
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
