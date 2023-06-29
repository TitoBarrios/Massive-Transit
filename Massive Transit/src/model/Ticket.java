package model;

import java.time.LocalDateTime;

public class Ticket {
	private User owner;
	private LocalDateTime expirationDate;
	private int price;
	
	public Ticket(User owner, LocalDateTime expirationDate) {
		this.expirationDate = expirationDate;
		this.owner = owner;
		this.price = price;
	}

	public User getOwner() {
		return owner;
	}
	
	public LocalDateTime getExpirationDate() {
		return expirationDate;
	}
	
	public int getPrice() {
		return price;
	}
}
