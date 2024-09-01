package org.KikiCourier;

import org.KikiCourier.Offer.Offer;

import java.util.HashSet;
import java.util.Set;

public class OfferManager {
    private static final Set<Offer> availableOffers = new HashSet<>();

    public OfferManager() {
        availableOffers.add(new Offer("OFR001", 10));
        availableOffers.add(new Offer("OFR002", 7));
        availableOffers.add(new Offer("OFR003", 5));
    }


    public boolean validateOffer(String offerCode) {
        return availableOffers.stream().anyMatch(offer -> offer.isValid(offerCode));
    }

    public Offer getOfferBy(String offerCode) {
        return availableOffers.stream().filter(offer -> offer.isValid(offerCode)).findFirst().orElse(null);
    }
}
