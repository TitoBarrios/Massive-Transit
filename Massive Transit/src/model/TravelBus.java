package model;

public class TravelBus extends Vehicle {

	public TravelBus(String company, String plate, Route[] routes, int price, int capacity) {
		super(VehicleType.TRAVEL_BUS, company, plate, routes, price, capacity);
	}
}
