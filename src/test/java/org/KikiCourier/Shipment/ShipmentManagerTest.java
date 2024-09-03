package org.KikiCourier.Shipment;

import org.KikiCourier.Offer.IOffer;
import org.KikiCourier.Offer.Offer;
import org.KikiCourier.Offer.OfferManager;
import org.KikiCourier.Vehicle.VehicleManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShipmentManagerTest {
    private final PrintStream standardErr = System.err;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Mock
    private OfferManager offerManager;
    private ShipmentManager shipmentManager;

    @BeforeEach
    public void setUp() {
        shipmentManager = new ShipmentManager(offerManager, 100.00, new VehicleManager(2, 12.00, 130));
        System.setErr(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setErr(standardErr);
    }

    @Test
    public void preparePackagesForShipment_shouldPreparePackagesToBeShipped() {
        List<String> instructions = Arrays.asList(
                "PKG1 50 100 OFR001",
                "PKG2 75 125 OFR002"
        );

        IOffer mockOffer = mock(Offer.class);
        when(offerManager.isValidOffer("OFR001")).thenReturn(true);
        when(offerManager.isApplicable("OFR001", 50, 100)).thenReturn(true);
        when(offerManager.getOfferBy("OFR001")).thenReturn(mockOffer);
        when(offerManager.isValidOffer("OFR002")).thenReturn(true);
        when(offerManager.isApplicable("OFR002", 75, 125)).thenReturn(true);
        when(offerManager.getOfferBy("OFR002")).thenReturn(mockOffer);

        shipmentManager.preparePackagesForShipment(instructions);

        verify(offerManager, times(2)).isValidOffer(anyString());
        verify(offerManager, times(2)).isApplicable(anyString(), anyInt(), anyInt());
        verify(offerManager, times(2)).getOfferBy(anyString());
    }

    @Test
    public void preparePackagesForShipment_shouldProcessPackagesWithoutApplyingOffer() {
        List<String> instructions = Collections.singletonList("PKG1 50 100 OFR001");

        when(offerManager.isValidOffer("OFR001")).thenReturn(true);
        when(offerManager.isApplicable("OFR001", 50, 100)).thenReturn(false);

        shipmentManager.preparePackagesForShipment(instructions);
        verify(offerManager, times(0)).getOfferBy(anyString());
    }

    @Test
    public void preparePackagesForShipment_shouldNotApplyOfferIdOfferCodeIsInvalid() {
        List<String> instructions = Collections.singletonList("PKG1 50 100 INVALID");

        when(offerManager.isValidOffer("INVALID")).thenReturn(false);

        shipmentManager.preparePackagesForShipment(instructions);

        verify(offerManager).isValidOffer("INVALID");
        verify(offerManager, never()).getOfferBy(anyString());
    }

    @Test
    public void preparePackagesForShipment_shouldLogErrorIfInvalidShipmentDataProvided() {
        List<String> instructions = Collections.singletonList("PKG1 XYZ 100 OFR001");
        assertDoesNotThrow(() -> shipmentManager.preparePackagesForShipment(instructions));
        assertEquals("Error processing shipment: PKG1 XYZ 100 OFR001\n", outputStreamCaptor.toString());
    }

    @Test
    public void createShipmentPackagesWithEstimatedDeliveryTime_shouldCreateShipmentPackagesWithSameEstimatedDeliveryTimeIfShipmentsWeightDoesNotExceedTheMaxCarriableWeightForVehicles() {
        List<String> instructions = Arrays.asList("PKG1 50 100 OFR001", "PKG2 50 125 OFR002");

        when(offerManager.isValidOffer("OFR001")).thenReturn(false);

        shipmentManager.preparePackagesForShipment(instructions);
        List<ShipmentPackage> shipmentPackages = shipmentManager.createShipmentPackagesWithEstimatedDeliveryTime();
        assertEquals("PKG1 0 1100 10.42", shipmentPackages.get(0).toString());
        assertEquals("PKG2 0 1225 10.42", shipmentPackages.get(1).toString());
    }

    @Test
    public void createShipmentPackagesWithEstimatedDeliveryTime_shouldCreateShipmentPackagesWithDifferentEstimatedDeliveryTimeIfShipmentsWeightIsExceedingTheMaxCarriableWeightForVehicles() {
        List<String> instructions = Arrays.asList("PKG1 120 100 OFR001", "PKG2 50 125 OFR002");

        when(offerManager.isValidOffer("OFR001")).thenReturn(false);

        shipmentManager.preparePackagesForShipment(instructions);
        List<ShipmentPackage> shipmentPackages = shipmentManager.createShipmentPackagesWithEstimatedDeliveryTime();
        assertEquals("PKG1 0 1800 8.33", shipmentPackages.get(0).toString());
        assertEquals("PKG2 0 1225 10.42", shipmentPackages.get(1).toString());
    }
}