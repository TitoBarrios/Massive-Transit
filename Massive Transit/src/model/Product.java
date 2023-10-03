package model;

public class Product {
	private String name;
	private int price;
	private boolean isConcurrent;
	private boolean availability;

	public Product(String name, int price, boolean isConcurrent) {
		this.name = name;
		this.price = price;
		this.isConcurrent = isConcurrent;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public void setConcurrent(boolean isConcurrent) {
		this.isConcurrent = isConcurrent;
	}

	public void setAvailability(boolean availability) {
		this.availability = availability;
	}

	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}

	public boolean getIsConcurrent() {
		return isConcurrent;
	}

	public boolean getAvailability() {
		return availability;
	}
}
