package com.titobarrios.db;

import java.util.ArrayList;

import com.titobarrios.model.Coupon;
import com.titobarrios.model.RouteSequence;
import com.titobarrios.model.Account;
import com.titobarrios.model.Vehicle;

public class Archive {

	private static ArrayList<Account> users;
	private static ArrayList<Vehicle> vehicles;
	private static ArrayList<RouteSequence> routeSeqs;
	private static ArrayList<Coupon> coupons;

	public Archive() {}

	public static void initialize() {
		users = new ArrayList<Account>();
		vehicles = new ArrayList<Vehicle>();
		routeSeqs = new ArrayList<RouteSequence>();
		coupons = new ArrayList<Coupon>();
	}

	public static void store(Account user) {
		users.add(user);
	}

	public static void store(Vehicle vehicle) {
		vehicles.add(vehicle);
	}

	public static void store(RouteSequence routeSeq) {
		routeSeqs.add(routeSeq);
	}

	public static void store(Coupon coupon) {
		coupons.add(coupon);
	}

	public static Account[] getUsers() {
		return users.toArray(Account[]::new);
	}

	public static Vehicle[] getVehicles() {
		return vehicles.toArray(Vehicle[]::new);
	}

	public static RouteSequence[] getRouteSeqs() {
		return routeSeqs.toArray(RouteSequence[]::new);
	}

	public static Coupon[] getCoupons() {
		return coupons.toArray(Coupon[]::new);
	}

	
}
