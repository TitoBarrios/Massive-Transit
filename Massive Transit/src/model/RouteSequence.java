package model;

public class RouteSequence {
	String name;
	Company owner;
	Route[] routes;

	public RouteSequence(String name, Company owner, Route[] routes) {
		this.name = name;
		this.owner = owner;
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

	public Route[] getRoutes() {
		return routes;
	}

	public void setRoutes(Route[] routes) {
		this.routes = routes;
	}

	public void editRoute(Route newRoute, int routeNumber) {
		routes[routeNumber] = newRoute;
	}
}
