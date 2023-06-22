package model;

public class Bus {
	private String plate;
	private Route[] routes;
	private Ticket[] tickets;
	private int price;
	private boolean disponibility;
	
	private final static int MAX_TICKETS = 80;
	private final static int MAX_ROUTES = 30;
	
	public Bus(String plate, int price) {
		this.plate = plate;
		this.price = price;
		routes = new Route[MAX_ROUTES];
		tickets = new Ticket[MAX_TICKETS];
	}
	
	public void setRoutes(Route[] routes) {
		this.routes = routes;
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
	
	public boolean getDisponiblility() {
		return disponibility;
	}

}
	
	
