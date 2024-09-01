package org.KikiCourier;

import org.KikiCourier.Offer.Offer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShipmentPackageTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    public void calculateDeliveryCost_shouldCalculateDeliveryCostWhenNoOfferIsApplied() {
        ShipmentPackage shipmentPackage = new ShipmentPackage("PKG1", 100.00, 15, 5);
        Double deliveryCost = shipmentPackage.calculateDeliveryCost();
        assertEquals(275, deliveryCost);
        assertEquals("PKG1 0 275\n", outputStreamCaptor.toString());
    }

    @Test
    public void applyOffer_shouldApplyDiscountAfterAnOfferIsApplied() {
        ShipmentPackage shipmentPackage = new ShipmentPackage("PKG1", 100.00, 15, 5);
        Offer ofr001 = new Offer("OFR001", 10);
        shipmentPackage.applyOffer(ofr001);
        Double deliveryCost = shipmentPackage.calculateDeliveryCost();
        assertEquals(247.5, deliveryCost);
        assertEquals("PKG1 28 248\n", outputStreamCaptor.toString());
    }

    @Test
    public void applyOffer_shouldNotApplyDiscountForMultipleOffers() {
        ShipmentPackage shipmentPackage = new ShipmentPackage("PKG1", 100.00, 15, 5);
        Offer ofr001 = new Offer("OFR001", 10);
        Offer ofr002 = new Offer("OFR002", 7);
        shipmentPackage.applyOffer(ofr001);
        shipmentPackage.applyOffer(ofr002);
        Double deliveryCost = shipmentPackage.calculateDeliveryCost();
        assertEquals(247.5, deliveryCost);
        assertEquals("PKG1 28 248\n", outputStreamCaptor.toString());
    }
}