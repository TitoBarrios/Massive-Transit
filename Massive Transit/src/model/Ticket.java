package model;

import java.time.LocalDateTime;

public class Ticket {
	private User owner;
	private int chairNumber;
	private LocalDateTime expirationDate;
	private int price;
	
	public Ticket(User owner, int chairNumber, LocalDateTime expirationDate, int price) {
		this.expirationDate = expirationDate;
		this.chairNumber = chairNumber;
		this.owner = owner;
		this.price = price;
	}
	
	public User getOwner() {
		return owner;
	}
	
	public LocalDateTime getExpirationDate() {
		return expirationDate;
	}
	
	public int getChairNumber() {
		return chairNumber;
	}
	
	public int getPrice() {
		return price;
	}
}
