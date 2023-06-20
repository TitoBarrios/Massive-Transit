package model;

public class Route {
	String name;
	Ticket[] capacity;
	Stop[] stops;
	
	public static final int MAX_CAPACITY = 80;
	public static final int MAX_STOPS = 1;
	
	public Route(String name, Stop[] stops) {
		this.name = name;
		this.stops = stops;
		capacity = new Ticket[MAX_CAPACITY];
	}
	
	public void setCapacity(Ticket[] capacity) {
		this.capacity = capacity;
	}
	
	public String getName() {
		return name;
	}
	
	public Stop[] getStops() {
		return stops;
	}
	
	public Ticket[] getCapacity() {
		return capacity;
	}
}
