package model;

public class Bus extends Vehicle {
	public Bus(String plate, String company, int price, int capacity, Route[] routes) {
		super(plate, VehicleType.BUS, company, price, capacity, routes);
	}
}