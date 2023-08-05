package model;

public class Bus {
	private String plate;
	private Route[] routes;
	private Ticket[] tickets;
	private int price;
	private int capacity;
	private int currentCapacity;
	private boolean disponibility;

	private final static int MAX_TICKETS = 200;
	private final static int MAX_ROUTES = 30;

	public Bus(String plate, int price, int capacity, Route[] routes) {
		this.plate = plate;
		this.routes = new Route[MAX_ROUTES];
		this.routes = routes;
		tickets = new Ticket[MAX_TICKETS];
		this.price = price;
		this.capacity = capacity;
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

	public void eliminateTicket(int ticketPosition) {
		tickets[ticketPosition] = null;
	}
	
	public void setCurrentCapacity(int currentCapacity) {
		this.currentCapacity = currentCapacity;
	}

	public void setDisponibility(boolean disponibility) {
		this.disponibility = disponibility;
	}

	public String getPlate() {
		return plate;
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

	public boolean getDisponiblility() {
		return disponibility;
	}
}