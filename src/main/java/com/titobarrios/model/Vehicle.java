package com.titobarrios.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.titobarrios.constants.VType;
import com.titobarrios.constants.Value;
import com.titobarrios.db.CurrentDate;
import com.titobarrios.db.DB;
import com.titobarrios.model.interfaces.Id;

public abstract class Vehicle implements Id {

	private final static int STATISTICS_TYPES = 4;
	private final static int MAX_CAPACITY_X = 2;

	private VType type;
	private int[] revenue;
	private ArrayList<Ticket> tickets;
	private ArrayList<Coupon> coupons;
	private int[] capacity;
	private Company company;
	private RouteSequence routeSeq;
	private String plate;
	private int price;
	private boolean isAvailable;

	private LocalDateTime lastCheck;

	public Vehicle(VType type, Company company, String plate, RouteSequence routeSeq, int price, int capacity) {
		this.type = type;
		revenue = new int[STATISTICS_TYPES];
		tickets = new ArrayList<Ticket>();
		coupons = new ArrayList<Coupon>();
		this.capacity = new int[MAX_CAPACITY_X];
		this.capacity[Value.MAXIMUM.value()] = capacity;
		this.company = company;
		this.routeSeq = routeSeq;
		this.plate = plate;
		this.price = price;
		company.add(this);
		routeSeq.add(this);
		DB.store(this);
	}

	public void add(Ticket ticket) {
		changeCurrentCapacity(1);
		refreshRevenue();
		for (int i = 0; i < revenue.length; i++) {
			revenue[i] += ticket.getPrice()[Ticket.PriceType.PAID.ordinal()];
			company.getRevenue()[i] += ticket.getPrice()[Ticket.PriceType.PAID.ordinal()];
		}
		tickets.add(ticket);
	}

	public void add(Coupon coupon) {
		coupons.add(coupon);
	}

	public void remove(Coupon coupon) {
		coupons.remove(coupon);
	}

	public void remove(Ticket ticket) {
		tickets.remove(ticket);
	}

	public void refresh() {
		routeSeq.refresh();
		for (Ticket ticket : tickets)
			if (ticket.getRoutes()[Route.StopType.EXIT.ordinal()].getStops()[Route.StopType.EXIT.ordinal()]
					.isAfter(CurrentDate.get())) {
				changeCurrentCapacity(-1);
				tickets.remove(ticket);
			}
		refreshRevenue();
		this.setAvailable(capacity[Value.MAXIMUM.value()] > capacity[Value.CURRENT.value()] && routeSeq.isAvailable());
	}

	private void refreshRevenue() {
		if (CurrentDate.get().getYear() != lastCheck.getYear())
			revenue[Value.YEARLY.value()] = 0;
		if (CurrentDate.get().getMonth() != lastCheck.getMonth())
			revenue[Value.MONTHLY.value()] = 0;
		if (CurrentDate.get().getDayOfMonth() != lastCheck.getDayOfMonth())
			revenue[Value.DAILY.value()] = 0;
		company.refreshRevenue();
		lastCheck = CurrentDate.get();
	}

	private void changeCurrentCapacity(int variation) {
		this.capacity[Value.CURRENT.value()] += variation;
	}

	public VType getType() {
		return type;
	}

	public int[] getRevenue() {
		return revenue;
	}

	public Ticket[] getTickets() {
		return tickets.toArray(Ticket[]::new);
	}

	public Coupon[] getCoupons() {
		return coupons.toArray(Coupon[]::new);
	}

	public int[] getCapacity() {
		return capacity;
	}

	public void setCapacity(int[] capacity) {
		this.capacity = capacity;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public RouteSequence getRouteSeq() {
		return routeSeq;
	}

	public void setRouteSequence(RouteSequence routeSeq) {
		this.routeSeq = routeSeq;
	}

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public String getId() {
		return plate;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public void delete() {
		DB.remove(this);
		company.remove(this);
		routeSeq.remove(this);
		for (Coupon coupon : coupons)
			coupon.remove(this);
	}

	public String info() {
		StringBuilder builder = new StringBuilder();
		builder.append(plate).append("		").append(company.getId()).append("\n Precio: ").append(price)
				.append("		").append(isAvailable ? "Disponible" : "No Disponible").append("\n Secuencia: ")
				.append(routeSeq.getId()).append("		Máxima capacidad: ")
				.append(capacity[Value.MAXIMUM.value()]);
		return builder.toString();
	}
}
