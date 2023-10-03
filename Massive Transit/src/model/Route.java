package model;

import java.time.LocalDateTime;

public class Route {
	String name;
	LocalDateTime[] stops;
	boolean availability;

	public static final int MAX_TICKETS = 80;
	public static final int MAX_STOPS = 2;

	public Route(String name, LocalDateTime[] stops) {
		this.name = name;
		this.stops = new LocalDateTime[MAX_STOPS];
		this.stops = stops;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAvailability(boolean availability) {
		this.availability = availability;
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

	public boolean getAvailability() {
		return availability;
	}
}
