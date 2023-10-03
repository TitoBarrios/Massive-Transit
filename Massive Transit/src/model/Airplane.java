package model;

public class Airplane extends Vehicle {
	private Product[] catalogue;

	private String description;

	public final int MAX_PRODUCTS = 30;

	public Airplane(String plate, String company, int price, int capacity, Route[] routes) {
		super(plate, VehicleType.AIRPLANE, company, price, capacity, routes);
		catalogue = new Product[30];
	}

	public void addProduct(Product product) {
		for (int i = 0; i < catalogue.length; i++) {
			if (catalogue[i] == null) {
				catalogue[i] = product;
				break;
			}
		}
	}

	public void setCatologue(Product[] catalogue) {
		this.catalogue = catalogue;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Product[] getCatalogue() {
		return catalogue;
	}

	public String getDescription() {
		return description;
	}
}
