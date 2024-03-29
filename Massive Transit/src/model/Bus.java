package model;

public class Bus extends Vehicle {
	public Bus(Company company, String plate, RouteSequence routeSeq, int price, int capacity) {
		super(Type.BUS, company, plate, routeSeq, price, capacity);
	}
}