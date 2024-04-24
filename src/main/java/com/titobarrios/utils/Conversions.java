package com.titobarrios.utils;

import com.titobarrios.constants.VType;

public class Conversions {
	public static VType convertIntToVehicleType(int vehicleTypeInt) {
		switch (vehicleTypeInt) {
			case 0:
				return VType.AIRPLANE;
			case 1:
				return VType.BUS;
			case 2:
				return VType.SHIP;
			case 3:
				return VType.TRAVEL_BUS;
			default:
				return null;
		}
	}
}
