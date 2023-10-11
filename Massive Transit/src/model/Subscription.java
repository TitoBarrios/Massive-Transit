package model;

import java.time.DayOfWeek;

public class Subscription {
	private DayOfWeek dayOfWeek;
	private Vehicle vehicle;
	private Route[] routes;

	public Subscription(DayOfWeek dayOfWeek, Vehicle vehicle, Route[] routes) {
		this.dayOfWeek = dayOfWeek;
		this.vehicle = vehicle;
		this.routes = routes;
	}

	public DayOfWeek getDayOfWeek() {
		return dayOfWeek;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public Route[] getRoutes() {
		return routes;
	}
}
