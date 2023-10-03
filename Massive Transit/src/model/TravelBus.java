package model;

public class TravelBus extends Vehicle {

	public TravelBus(String plate, String company, int price, int capacity, Route[] routes) {
		super(plate, VehicleType.TRAVEL_BUS, company, price, capacity, routes);
	}

}
