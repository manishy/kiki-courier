package org.KikiCourier;

import org.KikiCourier.Offer.OfferManager;
import org.KikiCourier.Shipment.ShipmentManager;
import org.KikiCourier.Shipment.ShipmentPackage;
import org.KikiCourier.Vehicle.VehicleManager;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("No input file provided.");
            return;
        }
        Path inputFilePath = Paths.get(args[0]);
        try {
            List<String> instructions = Files.readAllLines(inputFilePath);
            String baseInstruction = instructions.get(0);
            List<String> shipmentInstructions = instructions.subList(1, instructions.size() - 1);
            String[] vehicleInstructions = instructions.get(instructions.size() - 1).split(" ");
            double baseDeliveryCost = parseBaseDeliveryCost(baseInstruction);
            VehicleManager vehicleManager = VehicleManager.create(vehicleInstructions);
            ShipmentManager shipmentManager = new ShipmentManager(new OfferManager(), baseDeliveryCost, vehicleManager);

            shipmentManager.preparePackagesForShipment(shipmentInstructions);
            List<ShipmentPackage> shipments = shipmentManager.createShipmentPackagesWithEstimatedDeliveryTime();
            printShipments(shipments);
        } catch (Exception e) {
            System.out.println("SOMETHING_WENT_WRONG");
        }
    }

    private static double parseBaseDeliveryCost(String baseInstruction) {
        String[] splitArgs = baseInstruction.split(" ");
        return Double.parseDouble(splitArgs[0]);
    }

    private static void printShipments(List<ShipmentPackage> shipments) {
        for (ShipmentPackage shipmentPackage : shipments) {
            System.out.println(shipmentPackage);
        }
    }
}