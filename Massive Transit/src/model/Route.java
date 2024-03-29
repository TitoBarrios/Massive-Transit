package model;

import java.time.LocalDateTime;
import java.util.Arrays;

public class Route {

	public static enum StopType {
		ENTRY, EXIT;
	}

	private LocalDateTime[] stops;
	private String[] stopsName;
	private Coupon[] applicableCoupons;
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
		applicableCoupons = new Coupon[100];
	}

	public void addCoupon(Coupon coupon) {
		for (int i = 0; i < applicableCoupons.length; i++) {
			if (applicableCoupons[i] == null) {
				applicableCoupons[i] = coupon;
				break;
			}
		}
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

	public Coupon[] getApplicableCoupons() {
		return applicableCoupons;
	}

	public void setApplicableCoupons(Coupon[] applicableCoupons) {
		this.applicableCoupons = applicableCoupons;
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

	@Override
	public String toString() {
		return "Route [stops=" + Arrays.toString(stops) + ", stopsName=" + Arrays.toString(stopsName)
				+ ", applicableCoupons=" + Arrays.toString(applicableCoupons) + ", name=" + name + ", isAvailable="
				+ isAvailable + "]";
	}
}
