package com.titobarrios.model;

import java.time.LocalDateTime;
import java.util.Arrays;

import com.titobarrios.controller.CouponCtrl;
import com.titobarrios.db.CurrentDate;

public class Ticket {

	public static enum PriceType {
		PAID, REAL;
	}

	private Account buyer;
	private Account owner;
	private Vehicle vehicle;
	private Route[] routes;
	private Coupon coupon;
	private String name;
	private int[] price;
	private boolean isAvailable;

	private Subscription subscription;

	public Ticket(Account owner, Account buyer, Coupon coupon, Vehicle vehicle, Route[] routes) {
		this.owner = owner;
		this.buyer = buyer;
		this.vehicle = vehicle;
		this.routes = Arrays.copyOf(routes, routes.length);
		this.coupon = new Coupon(coupon);
		initialize();
	}

	public Ticket(Account owner, Account buyer, Coupon coupon, Vehicle vehicle, Route[] routes,
			Subscription subscription) {
		this.owner = owner;
		this.buyer = buyer;
		this.vehicle = vehicle;
		this.routes = Arrays.copyOf(routes, routes.length);
		this.coupon = new Coupon(coupon);
		this.subscription = subscription;
		subscription.add(this);
		initialize();
	}

	private void initialize() {
		LocalDateTime entry = this.getRoutes()[Route.StopType.ENTRY.ordinal()].getStops()[Route.StopType.ENTRY
				.ordinal()];
		String name = new StringBuilder().append(CurrentDate.get().getYear()).append("_")
				.append(vehicle.getType().ordinal()).append(entry.getDayOfMonth()).append(entry.getMonth())
				.append(entry.toLocalTime())
				.append(this.getRoutes()[Route.StopType.EXIT.ordinal()].getStops()[Route.StopType.EXIT.ordinal()]
						.toLocalTime())
				.toString();
		this.setName(name);
		this.setPrice(new int[] { CouponCtrl.discountedPrice(coupon, vehicle.getPrice()), vehicle.getPrice() });
		vehicle.add(this);
		buyer.add(this);
		if (!buyer.equals(owner))
			owner.add(this);
	}

	public void refresh() {
		int entry = Route.StopType.ENTRY.ordinal(), exit = Route.StopType.EXIT.ordinal();
		isAvailable = routes[entry].getStops()[entry].isBefore(CurrentDate.get())
				&& routes[exit].getStops()[exit].isAfter(CurrentDate.get());
	}

	public Route[] getRoutes() {
		return routes;
	}

	public void setRoutes(Route[] routes) {
		this.routes = routes;
	}

	public Account getBuyer() {
		return buyer;
	}

	public void setBuyer(Account buyer) {
		this.buyer = buyer;
	}

	public Account getOwner() {
		return owner;
	}

	public void setOwner(Account owner) {
		this.owner = owner;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public Coupon getCoupon() {
		return coupon;
	}

	public void setCoupon(Coupon coupon) {
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

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public Subscription getSubscription() {
		return subscription;
	}

	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
	}

	public String bill() {
		StringBuilder builder = new StringBuilder();
		builder.append("Número de Ticket: ").append(name).append("\n").append(vehicle.getType().getUpperCaseName())
				.append(": ").append(vehicle.getPlate()).append("\nEmpresa: ").append(vehicle.getCompany().getId())
				.append("\nPrecio pagado: ").append(price[Ticket.PriceType.PAID.ordinal()]).append("\nEntrada: Ruta")
				.append(routes[Route.StopType.ENTRY.ordinal()].getStopsName()[Route.StopType.ENTRY.ordinal()])
				.append(' ').append(routes[Route.StopType.ENTRY.ordinal()].getStops()[Route.StopType.ENTRY.ordinal()])
				.append("\nSalida: Ruta ")
				.append(routes[Route.StopType.EXIT.ordinal()].getStopsName()[Route.StopType.EXIT.ordinal()]).append(' ')
				.append(routes[Route.StopType.EXIT.ordinal()].getStops()[Route.StopType.EXIT.ordinal()]).append(
						"\nEstado Actual: ")
				.append(isAvailable ? "Activo"
						: CurrentDate.get()
								.isBefore(routes[Route.StopType.ENTRY.ordinal()]
										.getStops()[Route.StopType.ENTRY.ordinal()])
								|| CurrentDate.get().isEqual(routes[Route.StopType.ENTRY.ordinal()]
										.getStops()[Route.StopType.ENTRY.ordinal()]) ? "Confirmado" : "Inactivo");
		if (!owner.getId().equals(buyer.getId()))
			builder.append("Comprador: ").append(buyer.getId());
		builder.append("\n");
		return builder.toString();
	}

}
