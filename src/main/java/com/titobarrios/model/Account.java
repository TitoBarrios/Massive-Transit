package com.titobarrios.model;

import java.util.ArrayList;

import com.titobarrios.db.DB;

public abstract class Account {

	private String id;
	private String password;
	private ArrayList<Ticket> tickets;

	public Account(String id, String password) {
		this.id = id;
		this.password = password;
		tickets = new ArrayList<Ticket>();
		DB.store(this);
	}

	public void add(Ticket ticket) {
		tickets.add(ticket);
	}

	public void setTickets(ArrayList<Ticket> tickets) {
		this.tickets = tickets;
	}

	public Ticket[] getTickets() {
		return tickets.toArray(Ticket[]::new);
	}

	public String getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
