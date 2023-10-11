package model;

public class Bus extends Vehicle {
	public Bus(String company, String plate, Route[] routes, int price, int capacity) {
		super(VehicleType.BUS, company, plate, routes, price, capacity);
	}
}