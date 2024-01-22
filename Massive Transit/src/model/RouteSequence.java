package model;

import java.time.DayOfWeek;

public class RouteSequence {
	DayOfWeek[] laboralDays;
	Route[] routes;
	Company owner;
	String name;
	boolean availability;

	public RouteSequence(String name, Company owner, DayOfWeek[] laboralDays, Route[] routes) {
		this.name = name;
		this.owner = owner;
		this.laboralDays = laboralDays;
		this.routes = routes;
	}

	public void editRoute(Route newRoute, int routeNumber) {
		routes[routeNumber] = newRoute;
	}

	public DayOfWeek[] getLaboralDays() {
		return laboralDays;
	}

	public void setLaboralDays(DayOfWeek[] laboralDays) {
		this.laboralDays = laboralDays;
	}

	public Route[] getRoutes() {
		return routes;
	}

	public void setRoutes(Route[] routes) {
		this.routes = routes;
	}

	public Company getOwner() {
		return owner;
	}

	public void setOwner(Company owner) {
		this.owner = owner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getAvailability() {
		return availability;
	}

	public void setAvailability(boolean availability) {
		this.availability = availability;
	}
}
