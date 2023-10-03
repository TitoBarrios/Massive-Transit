package model;

import java.time.LocalDateTime;

public class Ticket {
	private String name;
	private User owner;
	private LocalDateTime[] dates;
	private Vehicle vehicle;
	private int[] routesNumber;
	private boolean availability;

	public static final int MAX_DATES = 2;
	private static final int MAX_ROUTES_NUMBER = 2;

	public Ticket(User owner, Vehicle vehicle, LocalDateTime startingDate, LocalDateTime expirationDate, int routeEntryNumber,
			int routeExitNumber) {
		routesNumber = new int[MAX_ROUTES_NUMBER];
		routesNumber[0] = routeEntryNumber + 1;
		routesNumber[1] = routeExitNumber + 1;
		dates = new LocalDateTime[MAX_DATES];
		dates[0] = startingDate;
		dates[1] = expirationDate;
		this.owner = owner;
		this.vehicle = vehicle;
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

	public User getOwner() {
		return owner;
	}

	public LocalDateTime[] getDates() {
		return dates;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public boolean getAvailability() {
		return availability;
	}

	public int[] getRoutesNumber() {
		return routesNumber;
	}
}
