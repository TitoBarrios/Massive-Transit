package com.titobarrios.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.titobarrios.constants.Revenue;
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
			revenue[Revenue.YEARLY.ordinal()] = 0;
		if (CurrentDate.get().getMonth() != lastCheck.getMonth())
			revenue[Revenue.MONTHLY.ordinal()] = 0;
		if (CurrentDate.get().getDayOfMonth() != lastCheck.getDayOfMonth())
			revenue[Revenue.DAILY.ordinal()] = 0;
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

	public void remove(Vehicle vehicle) {
		vehicles.remove(vehicle);
	}

	public void remove(RouteSequence routeSeq) {
		routeSeqs.remove(routeSeq);
	}

	public void remove(Coupon coupon) {
		coupons.remove(coupon);
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

	public LocalDateTime getLastCheck() {
		return lastCheck;
	}

	public String info() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getId()).append("\n").append(description).append("\nVehículos:");
		for (Vehicle vehicle : vehicles)
			builder.append(vehicle.info()).append("\n");
		builder.append("\nSecuencias de Rutas:");
		for (RouteSequence routeSeq : routeSeqs)
			builder.append(routeSeq.info()).append("\n");
		builder.append("\nCupones:");
		for (Coupon coupon : coupons)
			builder.append(coupon.info()).append("\n");
		return builder.toString();
	}
}