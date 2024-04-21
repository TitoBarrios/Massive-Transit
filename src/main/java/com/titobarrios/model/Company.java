package com.titobarrios.model;

import java.util.ArrayList;

import com.titobarrios.constants.Value;

public class Company extends Account {
	private ArrayList<Vehicle> vehicles;
	private ArrayList<RouteSequence> routeSeqs;
	private ArrayList<Coupon> coupons;
	private int[] revenue;
	private String description;

	public Company(String id, String password) {
		super(id, password);
		vehicles = new ArrayList<Vehicle>();
		routeSeqs = new ArrayList<RouteSequence>();
		coupons = new ArrayList<Coupon>();
		revenue = new int[4];
	}

	public void checkRevenue(Company company) {
		for (Vehicle vehicle : vehicles) {
			vehicle.checkRevenue(Value.GENERAL);
			company.getRevenue()[Value.GENERAL
					.value()] += vehicle.getRevenue()[Value.GENERAL.value()][Value.REVENUE.value()];
			company.getRevenue()[Value.YEARLY
					.value()] += vehicle.getRevenue()[Value.YEARLY.value()][Value.REVENUE.value()];
			company.getRevenue()[Value.MONTHLY
					.value()] += vehicle.getRevenue()[Value.MONTHLY.value()][Value.REVENUE.value()];
			company.getRevenue()[Value.DAILY
					.value()] += vehicle.getRevenue()[Value.DAILY.value()][Value.REVENUE.value()];
		}
	}

	public void add(Vehicle vehicle) {
		vehicles.add(vehicle);
	}

	public void add(RouteSequence routeSeq) {
		routeSeqs.add(routeSeq);
	}

	public void add(Coupon coupon) {
		coupons.add(coupon);
	}

	public Vehicle[] getVehicles() {
		return vehicles.toArray(Vehicle[]::new);
	}

	public RouteSequence[] getRouteSeqs() {
		return routeSeqs.toArray(RouteSequence[]::new);
	}

	public Coupon[] getCoupons() {
		return coupons.toArray(Coupon[]::new);
	}

	public int[] getRevenue() {
		return revenue;
	}

	public void setRevenue(int[] revenue) {
		this.revenue = revenue;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
