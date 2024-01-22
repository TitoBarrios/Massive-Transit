package model;

import java.time.DayOfWeek;

public class Subscription {
	private Route[] routes;
	private DayOfWeek dayOfWeek;
	private Vehicle vehicle;

	public Subscription(DayOfWeek dayOfWeek, Vehicle vehicle, Route[] routes) {
		this.dayOfWeek = dayOfWeek;
		this.vehicle = vehicle;
		this.routes = routes;
	}

	public DayOfWeek getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(DayOfWeek dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public Route[] getRoutes() {
		return routes;
	}

	public void setRoutes(Route[] routes) {
		this.routes = routes;
	}
}
