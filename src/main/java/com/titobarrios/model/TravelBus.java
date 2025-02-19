package com.titobarrios.model;

import com.titobarrios.constants.VType;

public class TravelBus extends Vehicle {
	public TravelBus(Company company, String plate, RouteSequence routeSeq, int price, int capacity) {
		super(VType.TRAVEL_BUS, company, plate, routeSeq, price, capacity);
	}
}
