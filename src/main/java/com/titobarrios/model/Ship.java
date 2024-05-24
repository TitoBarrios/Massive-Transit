package com.titobarrios.model;

import com.titobarrios.constants.VType;

public class Ship extends Vehicle {
	// private Product[] catalogue;

	public Ship(Company company, String plate, RouteSequence routeSeq, int price, int capacity) {
		super(VType.SHIP, company, plate, routeSeq, price, capacity);
	}
	/*
	 * public void addProduct(Product product) {
	 * for (int i = 0; i < catalogue.length; i++) {
	 * if (catalogue[i] == null) {
	 * catalogue[i] = product;
	 * break;
	 * }
	 * }
	 * }
	 * 
	 * public Product[] getCatalogue() {
	 * return catalogue;
	 * }
	 * 
	 * public void setCatalogue(Product[] catalogue) {
	 * this.catalogue = catalogue;
	 * }
	 */
}
