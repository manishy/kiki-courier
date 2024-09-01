package org.KikiCourier;

import org.KikiCourier.Offer.OfferManager;
import org.KikiCourier.Shipment.ShipmentManager;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            Path inputFilePath = Paths.get(args[0]);
            List<String> instructions = Files.readAllLines(inputFilePath);
            String baseInstruction = instructions.get(0);

            List<String> shipmentInstructions = instructions.subList(1, instructions.size());
            double baseDeliveryCost = parseBaseDeliveryCost(baseInstruction);
            ShipmentManager shipmentManager = new ShipmentManager(new OfferManager(), baseDeliveryCost);
            shipmentManager.processShipments(shipmentInstructions);
        } catch (Exception e) {
            System.out.println("SOMETHING_WENT_WRONG");
        }
    }

    private static double parseBaseDeliveryCost(String baseInstruction) {
        String[] splitArgs = baseInstruction.split(" ");
        return Double.parseDouble(splitArgs[0]);
    }
}