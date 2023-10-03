package model;

import java.time.DayOfWeek;

public class Subscription {
	DayOfWeek dayOfWeek;
	VehicleType vehicleType;
	int vehicleArrayNumber;
	int routeEntryArrayNumber;
	int routeExitArrayNumber;

	public Subscription(DayOfWeek dayOfWeek, VehicleType vehicleType, int vehicleArrayNumber, int routeEntryArrayNumber,
			int routeExitArrayNumber) {
		this.dayOfWeek = dayOfWeek;
		this.vehicleType = vehicleType;
		this.vehicleArrayNumber = vehicleArrayNumber;
		this.routeEntryArrayNumber = routeEntryArrayNumber;
		this.routeExitArrayNumber = routeExitArrayNumber;
	}

	public DayOfWeek getDayOfWeek() {
		return dayOfWeek;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	public int getVehicleArrayNumber() {
		return vehicleArrayNumber;
	}

	public int getRouteEntryArrayNumber() {
		return routeEntryArrayNumber;
	}

	public int getRouteExitArrayNumber() {
		return routeExitArrayNumber;
	}
}
