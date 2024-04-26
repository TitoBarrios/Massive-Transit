package com.titobarrios.controller;

import java.util.ArrayList;

import com.titobarrios.constants.Value;
import com.titobarrios.model.Vehicle;

public class VehicleCtrl {
	public void checkVehiclesAvailability(Vehicle[] vehicles) {
		for (Vehicle vehicle : vehicles)
			vehicle.refresh();
	}

	public static Vehicle[] searchAvailableVehicles(Vehicle[] vehicles) {
		ArrayList<Vehicle> available = new ArrayList<Vehicle>();
		for (Vehicle vehicle : vehicles)
			if (vehicle.isAvailable() == true)
				available.add(vehicle);
		return available.toArray(Vehicle[]::new);
	}

	public static Vehicle findBestVehicle(Vehicle[] vehicles) {
		Vehicle best = null;
		int bestPrice = Integer.MAX_VALUE;
		for (Vehicle vehicle : vehicles) {
			if (vehicle.getPrice() < bestPrice) {
				bestPrice = vehicle.getPrice();
				best = vehicle;
				continue;
			}
			if (vehicle.getPrice() == bestPrice)
				best = emptiestVehicle(best, vehicle);
		}
		return best;
	}

	private static Vehicle emptiestVehicle(Vehicle vehicle1, Vehicle vehicle2) {
		if (vehicle1.getCapacity()[Value.CURRENT.value()]
				/ vehicle1.getCapacity()[Value.MAXIMUM.value()] < vehicle2.getCapacity()[Value.CURRENT.value()]
						/ vehicle2.getCapacity()[Value.MAXIMUM.value()])
			return vehicle1;
		return vehicle2;
	}

}
