package model;

import java.time.LocalDateTime;

public class Route {
	private String name;
	private LocalDateTime[] stops;
	private String[] stopsName;
	private boolean availability;

	public Route(Route route) {
		this.name = route.getName();
		this.stops = route.getStops();
		this.stopsName = route.getStopsName();
	}
	
	public Route(String name, LocalDateTime[] stops, String[] stopsName) {
		this.name = name;
		this.stops = stops;
		this.stopsName = stopsName;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setStopsName(String[] stopsName) {
		this.stopsName = stopsName;
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
	
	public String[] getStopsName() {
		return stopsName;
	}

	public boolean getAvailability() {
		return availability;
	}
}
