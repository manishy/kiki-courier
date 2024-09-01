package org.KikiCourier;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShipmentPricingSummaryTest {

    @Test
    public void shouldInitialiseValues() {
        ShipmentPricingSummary result = new ShipmentPricingSummary("PKG1", 50.0, 150.0);

        assertEquals("PKG1", result.getId());
        assertEquals(50.0, result.getDiscount());
        assertEquals(150.0, result.getActualCost());
    }

    @Test
    public void toString_shouldReturnDetailsInAProperFormat() {
        ShipmentPricingSummary result = new ShipmentPricingSummary("PKG2", 30.0, 120.0);
        assertEquals("PKG2 30 120\n", result.toString());
    }
}
