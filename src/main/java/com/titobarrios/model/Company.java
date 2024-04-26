package com.titobarrios.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.titobarrios.constants.Value;
import com.titobarrios.db.CurrentDate;

public class Company extends Account {
	private ArrayList<Vehicle> vehicles;
	private ArrayList<RouteSequence> routeSeqs;
	private ArrayList<Coupon> coupons;
	private int[] revenue;
	private String description;

	private LocalDateTime lastCheck;

	public Company(String id, String password) {
		super(id, password);
		vehicles = new ArrayList<Vehicle>();
		routeSeqs = new ArrayList<RouteSequence>();
		coupons = new ArrayList<Coupon>();
		revenue = new int[4];
	}

	protected void refreshRevenue() {
		if (CurrentDate.get().getYear() != lastCheck.getYear())
			revenue[Value.YEARLY.value()] = 0;
		if (CurrentDate.get().getMonth() != lastCheck.getMonth())
			revenue[Value.MONTHLY.value()] = 0;
		if (CurrentDate.get().getDayOfMonth() != lastCheck.getDayOfMonth())
			revenue[Value.DAILY.value()] = 0;
		lastCheck = CurrentDate.get();
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
