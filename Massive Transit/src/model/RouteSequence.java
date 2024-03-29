package model;

import java.time.DayOfWeek;
import java.util.Arrays;

public class RouteSequence {
	private DayOfWeek[] laboralDays;
	private Route[] routes;
	private Coupon[] applicableCoupons;
	private Company owner;
	private String name;
	private boolean isAvailable;

	public RouteSequence(String name, Company owner, DayOfWeek[] laboralDays, Route[] routes) {
		this.name = name;
		this.owner = owner;
		this.laboralDays = laboralDays;
		this.routes = routes;
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

	public void editRoute(Route newRoute, int routeNumber) {
		routes[routeNumber] = newRoute;
	}

	public DayOfWeek[] getLaboralDays() {
		return laboralDays;
	}

	public void setLaboralDays(DayOfWeek[] laboralDays) {
		this.laboralDays = laboralDays;
	}

	public Route[] getRoutes() {
		return routes;
	}

	public void setRoutes(Route[] routes) {
		this.routes = routes;
	}

	public Coupon[] getApplicableCoupons() {
		return applicableCoupons;
	}

	public void setApplicableCoupons(Coupon[] applicableCoupons) {
		this.applicableCoupons = applicableCoupons;
	}

	public Company getOwner() {
		return owner;
	}

	public void setOwner(Company owner) {
		this.owner = owner;
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

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	@Override
	public String toString() {
		return "RouteSequence [laboralDays=" + Arrays.toString(laboralDays) + ", routes=" + Arrays.toString(routes)
				+ ", applicableCoupons=" + Arrays.toString(applicableCoupons) + ", owner=" + owner + ", name=" + name
				+ ", isAvailable=" + isAvailable + "]";
	}
}
