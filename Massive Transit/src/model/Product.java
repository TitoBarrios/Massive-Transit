package model;

public class Product {
	private String name;
	private int price;
	private boolean availability;
	private boolean isConcurrent;

	public Product(String name, int price, boolean isConcurrent) {
		this.name = name;
		this.price = price;
		this.isConcurrent = isConcurrent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public boolean getAvailability() {
		return availability;
	}

	public void setAvailability(boolean availability) {
		this.availability = availability;
	}

	public boolean getIsConcurrent() {
		return isConcurrent;
	}

	public void setConcurrent(boolean isConcurrent) {
		this.isConcurrent = isConcurrent;
	}
}
