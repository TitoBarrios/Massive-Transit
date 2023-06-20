package model;

public class Bus {
	private String plate;
	private Route[] routes;
	private Ticket[] capacity;
	private boolean disponibility;
	
	private final static int MAX_CAPACITY = 120;
	private final static int MAX_ROUTES = 30;
	
	public Bus(String plate) {
		this.plate = plate;
		routes = new Route[MAX_ROUTES];
		capacity = new Ticket[MAX_CAPACITY];
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
	
	public Ticket[] getCapacity() {
		return capacity;
	}
	
	public boolean getDisponiblility() {
		return disponibility;
	}

}
	
	
