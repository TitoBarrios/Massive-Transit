package model;

public class Vehicle {
	private VehicleType vehicleType;
	private String company;
	private String plate;
	private Route[] routes;
	private int price;
	private int[] capacity;

	private Ticket[] tickets;
	private boolean availability;

	private final static int MAX_CAPACITIES = 2;
	private final static int MAX_TICKETS = 200;

	public Vehicle(VehicleType vehicleType, String company, String plate, Route[] routes, int price, int capacity) {
		this.plate = plate;
		this.vehicleType = vehicleType;
		this.company = company;
		this.routes = routes;
		tickets = new Ticket[MAX_TICKETS];
		this.price = price;
		this.capacity = new int[MAX_CAPACITIES];
		this.capacity[Value.MAXIMUM.getValue()] = capacity;
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
				break;
			}
		}
	}

	public void setCurrentCapacity(int currentCapacity) {
		this.capacity[Value.CURRENT.getValue()] = currentCapacity;
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

	public int[] getCapacity() {
		return capacity;
	}

	public boolean getAvailability() {
		return availability;
	}
}
