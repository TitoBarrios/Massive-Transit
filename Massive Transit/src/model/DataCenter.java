package model;

import java.time.LocalDateTime;

public class DataCenter {

	private LocalDateTime currentDate;
	Bus[] buses;
	Airplane[] airplanes;
	Ship[] ships;
	TravelBus[] travelBuses;
	Route[][] routes;
	User[] users;
	public static final int MAX_BUSES = 30;
	public static final int MAX_AIRPLANES = 30;
	public static final int MAX_SHIPS = 30;
	public static final int MAX_TRAVEL_BUSES = 30;
	public static final int MAX_ROUTES = 30;
	public static final int MAX_USERS = 100;

	public DataCenter() {
		currentDate = LocalDateTime.now();
		buses = new Bus[MAX_BUSES];
		airplanes = new Airplane[MAX_AIRPLANES];
		ships = new Ship[MAX_SHIPS];
		travelBuses = new TravelBus[MAX_TRAVEL_BUSES];
		routes = new Route[MAX_ROUTES][];
		users = new User[MAX_USERS];
	}

	public void setBuses(Bus[] buses) {
		this.buses = buses;
	}

	public void setAirplanes(Airplane[] airplanes) {
		this.airplanes = airplanes;
	}

	public void setShips(Ship[] ships) {
		this.ships = ships;
	}

	public void setTravelBuses(TravelBus[] travelBuses) {
		this.travelBuses = travelBuses;
	}

	public LocalDateTime getCurrentDate() {
		return currentDate;
	}

	public Bus[] Ships() {
		return buses;
	}

	public Airplane[] getAirplanes() {
		return airplanes;
	}

	public Bus[] getBuses() {
		return buses;
	}

	public Ship[] getShips() {
		return ships;
	}

	public TravelBus[] getTravelBuses() {
		return travelBuses;
	}

	public Route[][] getRoutes() {
		return routes;
	}

	public User[] getUsers() {
		return users;
	}
}
