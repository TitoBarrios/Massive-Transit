package model;

import java.time.LocalDateTime;

public class Route {
	private LocalDateTime[] stops;
	private String[] stopsName;
	private String name;
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
	
	public LocalDateTime[] getStops() {
		return stops;
	}
	
	public void setStops(LocalDateTime[] stops) {
		this.stops = stops;
	}
	
	public String[] getStopsName() {
		return stopsName;
	}
	
	public void setStopsName(String[] stopsName) {
		this.stopsName = stopsName;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public boolean getAvailability() {
		return availability;
	}

	public void setAvailability(boolean availability) {
		this.availability = availability;
	}

}
