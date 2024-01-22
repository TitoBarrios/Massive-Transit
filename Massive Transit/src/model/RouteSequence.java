package model;

import java.time.DayOfWeek;

public class RouteSequence {
	String name;
	Company owner;
	DayOfWeek[] laboralDays;
	Route[] routes;
	boolean availability;

	public RouteSequence(String name, Company owner, DayOfWeek[] laboralDays, Route[] routes) {
		this.name = name;
		this.owner = owner;
		this.laboralDays = laboralDays;
		this.routes = routes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Company getOwner() {
		return owner;
	}

	public void setOwner(Company owner) {
		this.owner = owner;
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

	public void editRoute(Route newRoute, int routeNumber) {
		routes[routeNumber] = newRoute;
	}
	
	public boolean getAvailability() {
		return availability;
	}
	
	public void setAvailability(boolean availability) {
		this.availability = availability;
	}
}
