package model;

import java.time.LocalDateTime;

public class Route {
	String name;
	LocalDateTime[] stops;
	boolean disponibility;
	
	public static final int MAX_TICKETS = 80;
	public static final int MAX_STOPS = 2;
	
	public Route(String name, LocalDateTime[] stops) {
		this.name = name;
		this.stops = new LocalDateTime[MAX_STOPS];
		this.stops = stops;
	}
	
	public void setDisponibility(boolean disponibility) {
		this.disponibility = disponibility;
	}
	
	public String getName() {
		return name;
	}
	
	public LocalDateTime[] getStops() {
		return stops;
	}
	
	public LocalDateTime getStop(int number) {
		return stops[number];
	}
	
	public boolean getDisponibility() {
		return disponibility;
	}
}
