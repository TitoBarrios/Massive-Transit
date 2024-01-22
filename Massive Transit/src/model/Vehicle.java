package model;

public abstract class Vehicle {
	private final static int MAX_CAPACITIES = 2;
	private final static int REVENUE_Y = 2;
	private final static int STATISTICS_TYPES = 4;
	private final static int MAX_TICKETS = 200;

	private VehicleType vehicleType;
	private int[][] revenue;
	private Ticket[] tickets;
	private int[] capacity;
	private Company company;
	private RouteSequence routeSeq;
	private String plate;
	private int price;
	private boolean availability;

	public Vehicle(VehicleType vehicleType, Company company, String plate, RouteSequence routeSeq, int price,
			int capacity) {
		this.vehicleType = vehicleType;
		revenue = new int[STATISTICS_TYPES][REVENUE_Y];
		tickets = new Ticket[MAX_TICKETS];
		this.capacity = new int[MAX_CAPACITIES];
		this.capacity[Value.MAXIMUM.getValue()] = capacity;
		this.company = company;
		this.routeSeq = routeSeq;
		this.plate = plate;
		this.price = price;
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

	public void changeCurrentCapacity(int currentCapacity) {
		this.capacity[Value.CURRENT.getValue()] = currentCapacity;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	public int[][] getRevenue() {
		return revenue;
	}

	public void setRevenue(int[][] revenue) {
		this.revenue = revenue;
	}

	public Ticket[] getTickets() {
		return tickets;
	}

	public void setTickets(Ticket[] tickets) {
		this.tickets = tickets;
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
