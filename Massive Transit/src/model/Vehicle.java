package model;

public abstract class Vehicle {
	private VehicleType vehicleType;
	private Company company;
	private String plate;
	private RouteSequence routeSeq;
	private int price;
	private int[] capacity;

	private Ticket[] tickets;
	private boolean availability;

	private int[][] revenue;

	private final static int MAX_CAPACITIES = 2;
	private final static int REVENUE_Y = 2;
	private final static int STATISTICS_TYPES = 4;
	private final static int MAX_TICKETS = 200;

	public Vehicle(VehicleType vehicleType, Company company, String plate, RouteSequence routeSeq, int price,
			int capacity) {
		this.plate = plate;
		this.vehicleType = vehicleType;
		this.company = company;
		this.routeSeq = routeSeq;
		this.price = price;
		this.capacity = new int[MAX_CAPACITIES];
		this.capacity[Value.MAXIMUM.getValue()] = capacity;
		tickets = new Ticket[MAX_TICKETS];
		revenue = new int[STATISTICS_TYPES][REVENUE_Y];
	}

	public void addTicket(Ticket ticket) {
		for (int i = 0; i < tickets.length; i++) {
			if (tickets[i] == null) {
				tickets[i] = ticket;
				break;
			}
		}
	}

	public void deleteTicket(int ticketPosition) {
		tickets[ticketPosition] = null;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public void setRouteSequence(RouteSequence routeSeq) {
		this.routeSeq = routeSeq;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public void setCurrentCapacity(int currentCapacity) {
		this.capacity[Value.CURRENT.getValue()] = currentCapacity;
	}

	public void setAvailability(boolean availability) {
		this.availability = availability;
	}

	public void setRevenue(int[][] revenue) {
		this.revenue = revenue;
	}

	public String getPlate() {
		return plate;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	public Company getCompany() {
		return company;
	}

	public RouteSequence getRouteSeq() {
		return routeSeq;
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

	public int[][] getRevenue() {
		return revenue;
	}
}
