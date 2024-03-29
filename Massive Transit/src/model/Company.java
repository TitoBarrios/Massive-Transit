package model;

import java.util.Arrays;

public class Company extends User {
	private static final int MAX_VEHICLES_X = 4;
	private static final int MAX_VEHICLES_Y = 50;
	private static final int MAX_ROUTES = 50;
	private static final int MAX_COUPONS = 100;
	private static final int MAX_REVENUE_X = 4;

	private Vehicle[][] vehicles;
	private RouteSequence[] routeSeqs;
	private Coupon[] coupons;
	private int[] revenue;
	private String description;

	public Company(String name, String password) {
		super(name, password);
		vehicles = new Vehicle[MAX_VEHICLES_X][MAX_VEHICLES_Y];
		routeSeqs = new RouteSequence[MAX_ROUTES];
		coupons = new Coupon[MAX_COUPONS];
		revenue = new int[MAX_REVENUE_X];
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

	public void addCoupon(Coupon coupon){
		for(int i = 0; i < coupons.length; i++){
			if(coupons[i] == null){
				coupons[i] = coupon;
				break;
			}
		}
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

	@Override
	public String toString() {
		return "Company [vehicles=" + Arrays.toString(vehicles) + ", routeSeqs=" + Arrays.toString(routeSeqs)
				+ ", coupons=" + Arrays.toString(coupons) + ", revenue=" + Arrays.toString(revenue) + ", description="
				+ description + "]";
	}

}
