package model;

public class Ticket {
	private String name;
	private User owner;
	private Vehicle vehicle;
	private Route[] routes;
	private int price;
	private boolean availability;

	public Ticket(User owner, Vehicle vehicle, Route[] routes, int price) {
		this.routes = routes;
		this.price = price;
		this.owner = owner;
		this.vehicle = vehicle;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public void setAvailability(boolean availability) {
		this.availability = availability;
	}

	public String getName() {
		return name;
	}

	public User getOwner() {
		return owner;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public Route[] getRoutes() {
		return routes;
	}

	public int getPrice() {
		return price;
	}

	public boolean getAvailability() {
		return availability;
	}
}
