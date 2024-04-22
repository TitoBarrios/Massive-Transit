package com.titobarrios.model;

import java.time.DayOfWeek;

public class Subscription {
	private Route[] routes;
	private DayOfWeek dayOfWeek;
	private Vehicle vehicle;

	public Subscription(User user, DayOfWeek dayOfWeek, Vehicle vehicle, Route[] routes) {
		this.dayOfWeek = dayOfWeek;
		this.vehicle = vehicle;
		this.routes = routes;
		user.add(this);
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

	public String info() {
		StringBuilder builder = new StringBuilder();
		builder.append("Vehículo: ").append(vehicle.getType().getUpperCaseName()).append(' ').append(vehicle.getPlate())
				.append("\nEmpresa: ").append(vehicle.getCompany().getId()).append("\nEntrada: ")
				.append(routes[Route.StopType.ENTRY.ordinal()].getStopsName()[Route.StopType.ENTRY.ordinal()])
				.append(' ').append(routes[Route.StopType.ENTRY.ordinal()].getStops()[Route.StopType.ENTRY.ordinal()])
				.append("\nSalida: ")
				.append(routes[Route.StopType.EXIT.ordinal()].getStopsName()[Route.StopType.EXIT.ordinal()]).append(' ')
				.append(routes[Route.StopType.EXIT.ordinal()].getStops()[Route.StopType.EXIT.ordinal()])
				.append("\nPrecio: ").append(vehicle.getPrice()).append("\n");
		return builder.toString();
	}
}
