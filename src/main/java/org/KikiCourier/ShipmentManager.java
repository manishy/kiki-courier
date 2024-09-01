package org.KikiCourier;

public class ShipmentManager {
    private final OfferManager offerManager;

    public ShipmentManager(OfferManager offerManager) {
        this.offerManager = offerManager;
    }

    public ShipmentPackage createShipment(String id, Double basePrice, int weight, int distanceInKm, String offerCode) {
        ShipmentPackage shipmentPackage = new ShipmentPackage(id, basePrice, weight, distanceInKm);
        if (offerManager.isValidOffer(offerCode) && offerManager.isApplicable(offerCode, weight, distanceInKm)) {
            shipmentPackage.applyOffer(offerManager.getOfferBy(offerCode));
        }

        return shipmentPackage;
    }
}
