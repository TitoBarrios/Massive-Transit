package model;

import java.time.LocalDateTime;

public class User {
	private String name;
	private int wallet;
	
	public User(String name, int wallet) {
		this.name = name;
		this.wallet = wallet;
	}
	
	public String getName() {
		return name;
	}
	
	public int getWallet() {
		return wallet;
	}
}
