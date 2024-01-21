package model;

public class TravelBus extends Vehicle {

	public TravelBus(Company company, String plate, RouteSequence routeSeq, int price, int capacity) {
		super(VehicleType.TRAVEL_BUS, company, plate, routeSeq, price, capacity);
	}
}
