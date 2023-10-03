package model;

public class Ship extends Vehicle {
	private Product[] catalogue;

	public Ship(String plate, String company, int price, int capacity, Route[] routes) {
		super(plate, VehicleType.SHIP, company, price, capacity, routes);
	}

	public void addProduct(Product product) {
		for (int i = 0; i < catalogue.length; i++) {
			if (catalogue[i] == null) {
				catalogue[i] = product;
				break;
			}
		}
	}

	public void setCatalogue(Product[] catalogue) {
		this.catalogue = catalogue;
	}

	public Product[] getCatalogue() {
		return catalogue;
	}
}
