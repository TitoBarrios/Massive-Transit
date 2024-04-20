package com.titobarrios.db;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.titobarrios.model.Coupon;
import com.titobarrios.model.RouteSequence;
import com.titobarrios.model.User;
import com.titobarrios.model.Vehicle;
import com.titobarrios.model.User.Type;

public class Archive {

	public static final int MAX_COUPONS = 100;
	public static final int MAX_ROUTE_SEQS = 30;
	public static final int MAX_USERS = 100;
	public static final int MAX_USERS_TYPE = 2;
	public static final int MAX_VEHICLES_X = 4;
	public static final int MAX_VEHICLES_Y = 100;

	private User[] users;
	private Vehicle[] vehicles;
	private RouteSequence[] routeSeqs;
	private Coupon[] coupons;
	private LocalDateTime currentDate;

	private Generic<T> temp;

	public DataCenter() {
		users = new User[MAX_USERS_TYPE][MAX_USERS];
		vehicles = new Vehicle[MAX_VEHICLES_X][MAX_VEHICLES_Y];
		routeSeqs = new RouteSequence[MAX_ROUTE_SEQS];
		coupons = new Coupon[MAX_COUPONS];
		currentDate = LocalDateTime.now();
	}

	public void addUser(User.Type type, User user) {
		for (int i = 0; i < users[type.ordinal()].length; i++) {
			if (users[type.ordinal()][i] == null) {
				users[type.ordinal()][i] = user;
				break;
			}
		}
	}

	public void addVehicle(Vehicle.Type type, Vehicle vehicle) {
		for (int i = 0; i < vehicles[type.ordinal()].length; i++) {
			if (vehicles[type.ordinal()][i] == null) {
				vehicles[type.ordinal()][i] = vehicle;
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

	public void addCoupon(Coupon coupon) {
		for (int i = 0; i < coupons.length; i++) {
			if (coupons[i] == null) {
				coupons[i] = coupon;
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


	public Coupon[] getCoupons() {
		return coupons;
	}

	public void setCoupons(Coupon[] coupons) {
		this.coupons = coupons;
	}

	public LocalDateTime getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(LocalDateTime currentDate) {
		this.currentDate = currentDate;
	}
	
	@Override
	public String toString() {
		return "DataCenter [users=" + Arrays.toString(users) + ", vehicles=" + Arrays.toString(vehicles)
				+ ", routeSeqs=" + Arrays.toString(routeSeqs) + ", coupons=" + Arrays.toString(coupons)
				+ ", currentDate=" + currentDate + "]";
	}
}
