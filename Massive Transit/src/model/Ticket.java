package model;

public class Ticket {
	private Route[] routes;
	private User buyer;
	private User owner;
	private Vehicle vehicle;
	private String name;
	private int price;
	private boolean availability;

	public Ticket(User owner, User buyer, Vehicle vehicle, Route[] routes, int price) {
		this.owner = owner;
		this.buyer = buyer;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public boolean getAvailability() {
		return availability;
	}

	public void setAvailability(boolean availability) {
		this.availability = availability;
	}
}
