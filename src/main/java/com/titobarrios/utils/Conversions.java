package com.titobarrios.utils;

import com.titobarrios.model.Vehicle;

public class Conversions {
    public static Vehicle.Type convertIntToVehicleType(int vehicleTypeInt) {
		switch (vehicleTypeInt) {
			case 0:
				return Vehicle.Type.AIRPLANE;
			case 1:
				return Vehicle.Type.BUS;
			case 2:
				return Vehicle.Type.SHIP;
			case 3:
				return Vehicle.Type.TRAVEL_BUS;
			default:
				return null;
		}
	}
}
