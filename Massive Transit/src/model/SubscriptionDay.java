package model;

import java.time.DayOfWeek;

public class SubscriptionDay {
	DayOfWeek dayOfWeek;
	int busArrayNumber;
	int routeEntryArrayNumber;
	int routeExitArrayNumber;

	public SubscriptionDay(DayOfWeek dayOfWeek, int busArrayNumber, int routeEntryArrayNumber,
			int routeExitArrayNumber) {
		this.dayOfWeek = dayOfWeek;
		this.busArrayNumber = busArrayNumber;
		this.routeEntryArrayNumber = routeEntryArrayNumber;
		this.routeExitArrayNumber = routeExitArrayNumber;
	}

	public DayOfWeek getDayOfWeek() {
		return dayOfWeek;
	}

	public int getBusArrayNumber() {
		return busArrayNumber;
	}

	public int getRouteEntryArrayNumber() {
		return routeEntryArrayNumber;
	}

	public int getRouteExitArrayNumber() {
		return routeExitArrayNumber;
	}
}
