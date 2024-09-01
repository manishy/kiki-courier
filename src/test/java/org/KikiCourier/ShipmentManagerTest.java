package org.KikiCourier;

import org.KikiCourier.Offer.Offer;
import org.KikiCourier.Offer.OfferRule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShipmentManagerTest {
    private final String offerCode = "OFR001";
    @Mock
    private OfferManager offerManager;

    @InjectMocks
    private ShipmentManager shipmentManager;

    @Test
    public void createShipment_createsShipmentWithoutAnyOffer() {
        when(offerManager.isValidOffer(offerCode)).thenReturn(false);
        ShipmentPackage shipment = shipmentManager.createShipment("ID1", 100.00, 20, 120, offerCode);
        ShipmentPricingSummary shipmentPricing = shipment.getShipmentPricing();
        assertEquals(900.0, shipmentPricing.getActualCost());
        assertEquals(0.0, shipmentPricing.getDiscount());
    }

    @Test
    public void createShipment_createsShipmentWithOFR001() {
        Offer ofr001 = new Offer("OFR001", 10, new OfferRule(70, 200, 0, 200));
        when(offerManager.isValidOffer(offerCode)).thenReturn(true);
        when(offerManager.isApplicable(offerCode, 80, 120)).thenReturn(true);
        when(offerManager.getOfferBy(offerCode)).thenReturn(ofr001);
        ShipmentPackage shipment = shipmentManager.createShipment("ID1", 100.00, 80, 120, offerCode);
        ShipmentPricingSummary shipmentPricing = shipment.getShipmentPricing();
        assertEquals(1350.0, shipmentPricing.getActualCost());
        assertEquals(150.0, shipmentPricing.getDiscount());
    }
}