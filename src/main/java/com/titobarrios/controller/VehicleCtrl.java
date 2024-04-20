package com.titobarrios.controller;

import com.titobarrios.model.Vehicle;

public class VehicleCtrl {
    public void checkVehiclesAvailability(Vehicle[] vehicles) {
		for (Vehicle vehicle : vehicles)
			vehicle.checkAvailability();
	}
}
