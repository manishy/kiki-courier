package org.KikiCourier.Utils;

import org.KikiCourier.Shipment.ShipmentPackage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShipmentsGenerator {
    private static void buildPossibleShipments(List<ShipmentPackage> set, int index, List<ShipmentPackage> currentSubset, List<List<ShipmentPackage>> allSubsets) {
        if (index == set.size()) {
            allSubsets.add(new ArrayList<>(currentSubset));
            return;
        }
        buildPossibleShipments(set, index + 1, currentSubset, allSubsets);
        currentSubset.add(set.get(index));
        buildPossibleShipments(set, index + 1, currentSubset, allSubsets);
        currentSubset.remove(currentSubset.size() - 1);
    }

    public static List<List<ShipmentPackage>> getAllPossibleShipments(List<ShipmentPackage> shipmentPackages) {
        List<List<ShipmentPackage>> allSubsets = new ArrayList<>();
        buildPossibleShipments(shipmentPackages, 0, new ArrayList<>(), allSubsets);
        return allSubsets;
    }
}
