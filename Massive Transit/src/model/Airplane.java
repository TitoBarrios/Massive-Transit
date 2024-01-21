package model;

public class Airplane extends Vehicle {
	private Product[] catalogue;
	private String description;

	public static final int MAX_PRODUCTS = 30;

	public Airplane(Company company, String plate, RouteSequence routeSeq, int price, int capacity) {
		super(VehicleType.AIRPLANE, company, plate, routeSeq, price, capacity);
		catalogue = new Product[MAX_PRODUCTS];
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
