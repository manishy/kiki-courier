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

    private final double BASE_PRICE = 100.00;

    @Mock
    private OfferManager offerManager;
    private ShipmentManager shipmentManagerWithMockedOfferManager;
    private ShipmentManager shipmentManager;
    VehicleManager vehicleManager;


    @BeforeEach
    public void setUp() {
        vehicleManager = new VehicleManager(2, 70.00, 200);
        shipmentManager = new ShipmentManager(new OfferManager(), BASE_PRICE, vehicleManager);
        shipmentManagerWithMockedOfferManager = new ShipmentManager(offerManager, BASE_PRICE, new VehicleManager(2, 12.00, 130));
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

        shipmentManagerWithMockedOfferManager.preparePackagesForShipment(instructions);

        verify(offerManager, times(2)).isValidOffer(anyString());
        verify(offerManager, times(2)).isApplicable(anyString(), anyInt(), anyInt());
        verify(offerManager, times(2)).getOfferBy(anyString());
    }

    @Test
    public void preparePackagesForShipment_shouldProcessPackagesWithoutApplyingOffer() {
        List<String> instructions = Collections.singletonList("PKG1 50 100 OFR001");

        when(offerManager.isValidOffer("OFR001")).thenReturn(true);
        when(offerManager.isApplicable("OFR001", 50, 100)).thenReturn(false);

        shipmentManagerWithMockedOfferManager.preparePackagesForShipment(instructions);
        verify(offerManager, times(0)).getOfferBy(anyString());
    }

    @Test
    public void preparePackagesForShipment_shouldNotApplyOfferIdOfferCodeIsInvalid() {
        List<String> instructions = Collections.singletonList("PKG1 50 100 INVALID");

        when(offerManager.isValidOffer("INVALID")).thenReturn(false);

        shipmentManagerWithMockedOfferManager.preparePackagesForShipment(instructions);

        verify(offerManager).isValidOffer("INVALID");
        verify(offerManager, never()).getOfferBy(anyString());
    }

    @Test
    public void preparePackagesForShipment_shouldLogErrorIfInvalidShipmentDataProvided() {
        List<String> instructions = Collections.singletonList("PKG1 XYZ 100 OFR001");
        assertDoesNotThrow(() -> shipmentManagerWithMockedOfferManager.preparePackagesForShipment(instructions));
        assertEquals("Error processing shipment: PKG1 XYZ 100 OFR001\n", outputStreamCaptor.toString());
    }

    @Test
    public void createShipmentPackagesWithEstimatedDeliveryTime_shouldCreateShipmentPackages() {
        shipmentManagerWithMockedOfferManager = new ShipmentManager(new OfferManager(), BASE_PRICE, vehicleManager);

        List<String> instructions = Arrays.asList(
                "PKG1 50 30 OFR001",
                "PKG2 75 125 OFFR0008",
                "PKG3 175 100 OFR003",
                "PKG4 110 60 OFR002",
                "PKG5 155 95 NA"
        );

        shipmentManagerWithMockedOfferManager.preparePackagesForShipment(instructions);
        List<ShipmentPackage> shipmentPackages = shipmentManagerWithMockedOfferManager.createShipmentPackagesWithEstimatedDeliveryTime();
        assertEquals("PKG1 0 750 3.99", shipmentPackages.get(0).toString());
        assertEquals("PKG2 0 1475 1.78", shipmentPackages.get(1).toString());
        assertEquals("PKG3 0 2350 1.42", shipmentPackages.get(2).toString());
        assertEquals("PKG4 105 1395 0.85", shipmentPackages.get(3).toString());
        assertEquals("PKG5 0 2125 4.20", shipmentPackages.get(4).toString());
    }

    @Test
    public void createShipmentPackagesWithEstimatedDeliveryTime_shouldCreateShipmentPackagesWithTheSameWeight() {
        List<String> instructions = Arrays.asList("PKG1 50 30 OFR001", "PKG2 50 125 OFFR0008");
        shipmentManager.preparePackagesForShipment(instructions);
        List<ShipmentPackage> shipmentPackages = shipmentManager.createShipmentPackagesWithEstimatedDeliveryTime();

        assertEquals("PKG1 0 750 0.42", shipmentPackages.get(0).toString());
        assertEquals("PKG2 0 1225 1.78", shipmentPackages.get(1).toString());
    }

    @Test
    public void createShipmentPackagesWithEstimatedDeliveryTime_shouldCreateShipmentPackagesWithoutEstimatedTimeIfNoVehicleIsAvailable() {
        VehicleManager vehicleManager = new VehicleManager(0, 70.00, 200);
        shipmentManager = new ShipmentManager(new OfferManager(), BASE_PRICE, vehicleManager);

        List<String> instructions = Arrays.asList("PKG1 50 30 OFR001", "PKG2 75 125 OFFR0008");

        shipmentManager.preparePackagesForShipment(instructions);
        List<ShipmentPackage> shipmentPackages = shipmentManager.createShipmentPackagesWithEstimatedDeliveryTime();
        assertEquals("PKG1 0 750 0.00", shipmentPackages.get(0).toString());
        assertEquals("PKG2 0 1475 0.00", shipmentPackages.get(1).toString());
    }

    @Test
    public void createShipmentPackagesWithEstimatedDeliveryTime_shouldCreateShipmentPackagesWithoutEstimatedTimeIfShipmentsHaveMoreWeightsThanVehicleCapacity() {
        List<String> instructions = Arrays.asList("PKG1 50 30 OFR001", "PKG2 1000 125 OFFR0008");
        shipmentManager.preparePackagesForShipment(instructions);
        List<ShipmentPackage> shipmentPackages = shipmentManager.createShipmentPackagesWithEstimatedDeliveryTime();

        assertEquals("PKG1 0 750 0.42", shipmentPackages.get(0).toString());
        assertEquals("PKG2 0 10725 0.00", shipmentPackages.get(1).toString());
    }
}