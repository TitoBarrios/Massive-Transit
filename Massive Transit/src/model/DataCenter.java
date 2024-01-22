package model;

import java.time.LocalDateTime;

public class DataCenter {
	public static final int MAX_ROUTE_SEQS = 30;
	public static final int MAX_USERS = 100;
	public static final int MAX_USERS_TYPE = 2;
	public static final int MAX_VEHICLES_X = 4;
	public static final int MAX_VEHICLES_Y = 100;

	private User[][] users;
	private Vehicle[][] vehicles;
	private RouteSequence[] routeSeqs;
	private LocalDateTime currentDate;

	public DataCenter() {
		users = new User[MAX_USERS_TYPE][MAX_USERS];
		vehicles = new Vehicle[MAX_VEHICLES_X][MAX_VEHICLES_Y];
		routeSeqs = new RouteSequence[MAX_ROUTE_SEQS];
		currentDate = LocalDateTime.now();
	}

	public void addUser(Value type, User user) {
		for (int i = 0; i < users[type.getValue()].length; i++) {
			if (users[type.getValue()][i] == null) {
				users[type.getValue()][i] = user;
				break;
			}
		}
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

	public User[][] getUsers() {
		return users;
	}

	public void setUsers(User[][] users) {
		this.users = users;
	}

	public Vehicle[][] getVehicles() {
		return vehicles;
	}

	public void setVehicles(Vehicle[][] vehicles) {
		this.vehicles = vehicles;
	}

	public RouteSequence[] getRouteSeqs() {
		return routeSeqs;
	}

	public void setRouteSeqs(RouteSequence[] routeSeqs) {
		this.routeSeqs = routeSeqs;
	}

	public LocalDateTime getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(LocalDateTime currentDate) {
		this.currentDate = currentDate;
	}
}
