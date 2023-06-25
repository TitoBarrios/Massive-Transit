package model;

public class User {
	private String name;
	private String password;
	private int wallet;
	
	public User(String name, String password) {
		this.name = name;
		this.password = password;
	}
	
	public void setWallet(int wallet) {
		this.wallet = wallet;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public int getWallet() {
		return wallet;
	}
}
