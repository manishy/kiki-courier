package org.KikiCourier;

import org.KikiCourier.Offer.Offer;
import org.KikiCourier.Offer.OfferRule;

import java.util.HashSet;
import java.util.Set;

public class OfferManager {
    private static final Set<Offer> availableOffers = new HashSet<>();

    public OfferManager() {
        availableOffers.add(new Offer("OFR001", 10, new OfferRule(70, 200, 0, 200)));
        availableOffers.add(new Offer("OFR002", 7, new OfferRule(100, 250, 50, 150)));
        availableOffers.add(new Offer("OFR003", 5, new OfferRule(10, 150, 50, 250)));
    }


    public boolean isValidOffer(String offerCode) {
        return availableOffers.stream().anyMatch(offer -> offer.isValid(offerCode));
    }

    public boolean isApplicable(String offerCode, int weight, int distanceInKm) {
        Offer offer = getOfferBy(offerCode);
        return offer != null && offer.isApplicable(weight, distanceInKm);
    }

    public Offer getOfferBy(String offerCode) {
        return availableOffers.stream().filter(offer -> offer.isValid(offerCode)).findFirst().orElse(null);
    }
}
