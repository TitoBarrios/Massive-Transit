package com.titobarrios.services;

import java.util.ArrayList;

import com.titobarrios.constants.VType;
import com.titobarrios.constants.Value;
import com.titobarrios.model.Airplane;
import com.titobarrios.model.Bus;
import com.titobarrios.model.Company;
import com.titobarrios.model.RouteSequence;
import com.titobarrios.model.Ship;
import com.titobarrios.model.TravelBus;
import com.titobarrios.model.Vehicle;

public class VehicleServ {
	public static void checkVehiclesAvailability(Vehicle[] vehicles) {
		for (Vehicle vehicle : vehicles)
			vehicle.refresh();
	}

	public static void createVehicle(VType type, Company company, String plate, RouteSequence routeSeq, int price,
			int capacity) {
		switch (type) {
			case AIRPLANE:
				new Airplane(company, plate, routeSeq, price, capacity);
				break;
			case BUS:
				new Bus(company, plate, routeSeq, price, capacity);
				break;
			case TRAVEL_BUS:
				new TravelBus(company, plate, routeSeq, price, capacity);
				break;
			case SHIP:
				new Ship(company, plate, routeSeq, price, capacity);
				break;
		}
	}

	public static Vehicle[] filterByType(VType type, Vehicle[] vehicles) {
		ArrayList<Vehicle> filtered = new ArrayList<Vehicle>();
		for (Vehicle vehicle : vehicles)
			if (vehicle.getType() == type)
				filtered.add(vehicle);
		return filtered.toArray(Vehicle[]::new);
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

	public static Vehicle[] searchAvailableVehicles(Vehicle[] vehicles) {
		ArrayList<Vehicle> available = new ArrayList<Vehicle>();
		for (Vehicle vehicle : vehicles)
			if (vehicle.isAvailable() == true)
				available.add(vehicle);
		return available.toArray(Vehicle[]::new);
	}

	private static Vehicle emptiestVehicle(Vehicle vehicle1, Vehicle vehicle2) {
		if (vehicle1.getCapacity()[Value.CURRENT.value()]
				/ vehicle1.getCapacity()[Value.MAXIMUM.value()] < vehicle2.getCapacity()[Value.CURRENT.value()]
						/ vehicle2.getCapacity()[Value.MAXIMUM.value()])
			return vehicle1;
		return vehicle2;
	}
}
