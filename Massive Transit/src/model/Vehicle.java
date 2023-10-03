package model;

public class Vehicle {
	private String plate;
	private VehicleType vehicleType;
	private String company;
	private Route[] routes;
	private Ticket[] tickets;
	private int price;
	private int capacity;
	private int currentCapacity;
	private boolean availability;

	private final static int MAX_TICKETS = 200;
	private final static int MAX_ROUTES = 30;

	public Vehicle(String plate, VehicleType vehicleType, String company, int price, int capacity, Route[] routes) {
		this.plate = plate;
		this.vehicleType = vehicleType;
		this.company = company;
		this.routes = new Route[MAX_ROUTES];
		this.routes = routes;
		tickets = new Ticket[MAX_TICKETS];
		this.price = price;
		this.capacity = capacity;
	}
	
	public void deleteTicket(int ticketPosition) {
		tickets[ticketPosition] = null;
	}
	
	public void setCompany(String company) {
		this.company = company;
	}
	
	public void setRoutes(Route[] routes) {
		this.routes = routes;
	}

	public void setTicket(Ticket ticket) {
		for (int i = 0; i < tickets.length; i++) {
			if (tickets[i] == null) {
				tickets[i] = ticket;
			}
		}
	}

	public void setCurrentCapacity(int currentCapacity) {
		this.currentCapacity = currentCapacity;
	}

	public void setAvailability(boolean availability) {
		this.availability = availability;
	}

	public String getPlate() {
		return plate;
	}
	
	public VehicleType getVehicleType() {
		return vehicleType;
	}

	public String getCompany() {
		return company;
	}

	public Route[] getRoutes() {
		return routes;
	}

	public Ticket[] getTickets() {
		return tickets;
	}

	public int getPrice() {
		return price;
	}

	public int getCapacity() {
		return capacity;
	}

	public int getCurrentCapacity() {
		return currentCapacity;
	}

	public boolean getAvailability() {
		return availability;
	}

}
