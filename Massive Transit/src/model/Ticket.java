package model;

import java.util.Arrays;

public class Ticket {

	public static enum PriceType{
		PAID, REAL;
	}

	private Route[] routes;
	private User buyer;
	private User owner;
	private Vehicle vehicle;
	private RedeemedCoupon coupon;
	private String name;
	private int[] price;
	private boolean isAvailable;

	public Ticket(User owner, User buyer, RedeemedCoupon coupon, Vehicle vehicle, Route[] routes, int[] price) {
		this.owner = owner;
		this.buyer = buyer;
		this.coupon = coupon;
		this.routes = routes;
		this.price = price;
		this.vehicle = vehicle;
	}

	public Route[] getRoutes() {
		return routes;
	}

	public void setRoutes(Route[] routes) {
		this.routes = routes;
	}

	public User getBuyer() {
		return buyer;
	}

	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	
	public RedeemedCoupon getCoupon() {
		return coupon;
	}
	
	public void setCoupon(RedeemedCoupon coupon) {
		this.coupon = coupon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int[] getPrice() {
		return price;
	}

	public void setPrice(int[] price) {
		this.price = price;
	}

	public boolean getIsAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	@Override
	public String toString() {
		return "Ticket [routes=" + Arrays.toString(routes) + ", buyer=" + buyer + ", owner=" + owner + ", vehicle="
				+ vehicle + ", coupon=" + coupon + ", name=" + name + ", price=" + price + ", isAvailable="
				+ isAvailable + "]";
	}
}
