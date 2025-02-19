package com.titobarrios.db;

import java.util.ArrayList;

import com.titobarrios.model.Coupon;
import com.titobarrios.model.RouteSequence;
import com.titobarrios.model.Account;
import com.titobarrios.model.Admin;
import com.titobarrios.model.Vehicle;

public class Archive {

	private static ArrayList<Admin> admins;
	private static ArrayList<Account> accounts;
	private static ArrayList<RouteSequence> routeSeqs;
	private static ArrayList<Vehicle> vehicles;
	private static ArrayList<Coupon> coupons;

	public static void initialize() {
		admins = new ArrayList<Admin>();
		accounts = new ArrayList<Account>();
		routeSeqs = new ArrayList<RouteSequence>();
		vehicles = new ArrayList<Vehicle>();
		coupons = new ArrayList<Coupon>();
	}

	public static void store(Admin admin) {
		admins.add(admin);
	}

	public static void store(Account account) {
		accounts.add(account);
	}

	public static void store(RouteSequence routeSeq) {
		routeSeqs.add(routeSeq);
	}

	public static void store(Vehicle vehicle) {
		vehicles.add(vehicle);
	}

	public static void store(Coupon coupon) {
		coupons.add(coupon);
	}

	public static Admin[] getAdmins() {
		return admins.toArray(Admin[]::new);
	}

	public static Account[] getAccounts() {
		return accounts.toArray(Account[]::new);
	}

	public static RouteSequence[] getRouteSeqs() {
		return routeSeqs.toArray(RouteSequence[]::new);
	}

	public static Vehicle[] getVehicles() {
		return vehicles.toArray(Vehicle[]::new);
	}

	public static Coupon[] getCoupons() {
		return coupons.toArray(Coupon[]::new);
	}

	public static ArrayList<Admin> getAdminsArrayList() {
		return admins;
	}

	public static ArrayList<Account> getAccountsArrayList() {
		return accounts;
	}

	public static ArrayList<RouteSequence> getRouteSeqsArrayList() {
		return routeSeqs;
	}

	public static ArrayList<Vehicle> getVehiclesArrayList() {
		return vehicles;
	}

	public static ArrayList<Coupon> getCouponsArrayList() {
		return coupons;
	}

	public static void setAdmins(ArrayList<Admin> admins) {
		Archive.admins = admins;
	}

	public static void setAccounts(ArrayList<Account> accounts) {
		Archive.accounts = accounts;
	}

	public static void setRouteSeqs(ArrayList<RouteSequence> routeSeqs) {
		Archive.routeSeqs = routeSeqs;
	}

	public static void setVehicles(ArrayList<Vehicle> vehicles) {
		Archive.vehicles = vehicles;
	}

	public static void setCoupons(ArrayList<Coupon> coupons) {
		Archive.coupons = coupons;
	}
}
