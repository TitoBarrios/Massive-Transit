package com.titobarrios.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.titobarrios.db.CurrentDate;

public class Route {

	public static enum StopType {
		ENTRY, EXIT;
	}

	private LocalDateTime[] stops;
	private String[] stopsName;
	private ArrayList<Coupon> coupons;
	private String name;
	private boolean isAvailable;

	public Route(Route route) {
		this.name = route.getName();
		this.stops = route.getStops();
		this.stopsName = route.getStopsName();
	}

	public Route(String name, LocalDateTime[] stops, String[] stopsName) {
		this.name = name;
		this.stops = stops;
		this.stopsName = stopsName;
		coupons = new ArrayList<Coupon>();
	}

	public void add(Coupon coupon) {
		coupons.add(coupon);
	}

	public void checkAvailability() {
		if (CurrentDate.get().isAfter(this.getStops()[Route.StopType.ENTRY.ordinal()])
				|| CurrentDate.get().isEqual(this.getStops()[Route.StopType.ENTRY.ordinal()])) {
			this.setIsAvailable(false);
			return;
		}
		this.setIsAvailable(true);
	}

	public LocalDateTime[] getStops() {
		return stops;
	}

	public void setStops(LocalDateTime[] stops) {
		this.stops = stops;
	}

	public String[] getStopsName() {
		return stopsName;
	}

	public void setStopsName(String[] stopsName) {
		this.stopsName = stopsName;
	}

	public Coupon[] getCoupons() {
		return coupons.toArray(Coupon[]::new);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public String info() {
		StringBuilder builder = new StringBuilder();
		builder.append("Nombre: ").append(name).append("\nEntrada: ").append(stopsName[StopType.ENTRY.ordinal()])
				.append(" ")
				.append(stops[StopType.ENTRY.ordinal()].toLocalTime()).append("\nSalida: ")
				.append(stopsName[StopType.EXIT.ordinal()]).append(" ")
				.append(stops[StopType.EXIT.ordinal()].toLocalTime()).append("\n");
		return builder.toString();
	}
}
