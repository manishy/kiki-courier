package org.KikiCourier.Shipment;

import org.KikiCourier.Offer.OfferManager;

import java.util.List;

public class ShipmentManager {
    private final OfferManager offerManager;
    private final double basePrice;

    public ShipmentManager(OfferManager offerManager, double basePrice) {
        this.offerManager = offerManager;
        this.basePrice = basePrice;
    }

    public void processShipments(List<String> instructions) {
        for (String instruction : instructions) {
            try {
                ShipmentPackage shipment = createShipmentFromInstruction(instruction);
                System.out.println(shipment.getShipmentPricing().toString());
            } catch (Exception e) {
                System.err.println("Error processing shipment: " + instruction);
            }
        }
    }

    private ShipmentPackage createShipmentFromInstruction(String instruction) {
        String[] split = instruction.split(" ");
        String packageId = split[0];
        int packageWeightInKg = Integer.parseInt(split[1]);
        int distanceInKm = Integer.parseInt(split[2]);
        String offerCode = split[3];

        return createShipment(packageId, packageWeightInKg, distanceInKm, offerCode);
    }

    private ShipmentPackage createShipment(String id, int weight, int distanceInKm, String offerCode) {
        ShipmentPackage shipmentPackage = new ShipmentPackage(id, basePrice, weight, distanceInKm);
        if (offerManager.isValidOffer(offerCode) && offerManager.isApplicable(offerCode, weight, distanceInKm)) {
            shipmentPackage.applyOffer(offerManager.getOfferBy(offerCode));
        }

        return shipmentPackage;
    }
}
