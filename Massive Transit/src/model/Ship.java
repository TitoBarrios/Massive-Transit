package model;

public class Ship extends Vehicle {
	private Product[] catalogue;

	public Ship(String company, String plate, Route[] routes, int price, int capacity) {
		super(VehicleType.SHIP, company, plate, routes, price, capacity);
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
