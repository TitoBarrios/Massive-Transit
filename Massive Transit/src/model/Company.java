package model;

public class Company extends User {
	private String description;
	private Vehicle[][] vehicles;
	private RouteSequence[] routeSeqs;

	private static final int MAX_VEHICLES_X = 4;
	private static final int MAX_VEHICLES_Y = 50;
	private static final int MAX_ROUTES = 50;

	public Company(String name, String password) {
		super(name, password);
		vehicles = new Vehicle[MAX_VEHICLES_X][MAX_VEHICLES_Y];
		routeSeqs = new RouteSequence[MAX_ROUTES];
	}

	public void addVehicle(VehicleType type, Vehicle vehicle) {
		for (int i = 0; i < vehicles[type.getValue()].length; i++) {
			if (vehicles[type.getValue()][i] == null) {
				vehicles[type.getValue()][i] = vehicle;
				break;
			}
		}
	}

	public void addRouteSeq(RouteSequence routeSeq) {
		for (int i = 0; i < routeSeqs.length; i++) {
			if (routeSeqs[i] == null) {
				routeSeqs[i] = routeSeq;
				break;
			}
		}
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Vehicle[][] getVehicles() {
		return vehicles;
	}

	public RouteSequence[] getRouteSeqs() {
		return routeSeqs;
	}

	public void setVehicles(Vehicle[][] vehicles) {
		this.vehicles = vehicles;
	}

	public void setRouteSeqs(RouteSequence[] routeSeqs) {
		this.routeSeqs = routeSeqs;
	}

}
