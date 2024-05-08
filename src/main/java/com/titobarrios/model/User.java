package com.titobarrios.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.titobarrios.db.CurrentDate;
import com.titobarrios.db.DB;
import com.titobarrios.utils.RevenueUtil;

public class User extends Account {

	private ArrayList<Ticket> tickets;
	private ArrayList<User> relationships;
	private ArrayList<Subscription> subscriptions;
	private int wallet;

	private int[] revenue;
	private LocalDateTime lastCheck;

	public User(String id, String password) {
		super(id, password);
		tickets = new ArrayList<Ticket>();
		relationships = new ArrayList<User>();
		subscriptions = new ArrayList<Subscription>();
		revenue =  new int[4];
		lastCheck = CurrentDate.get();
		DB.store(this);
	}

	public void add(Ticket ticket) {
		RevenueUtil.refreshRevenue(revenue, lastCheck);
		for (int i = 0; i < revenue.length; i++)
			revenue[i] += ticket.getPrice()[Ticket.PriceType.PAID.ordinal()];
		tickets.add(ticket);
	}

	public void add(User relationship) {
		relationships.add(relationship);
	}

	public void add(Subscription subscription) {
		subscriptions.add(subscription);
	}

	public void remove(User relationship) {
		relationships.remove(relationship);
	}

	public void remove(Subscription subscription) {
		subscriptions.remove(subscription);
	}

	public Ticket[] getTickets() {
		return tickets.toArray(Ticket[]::new);
	}

	public User[] getRelationships() {
		return relationships.toArray(User[]::new);
	}

	public Subscription[] getSubscriptions() {
		return subscriptions.toArray(Subscription[]::new);
	}

	public int getWallet() {
		return wallet;
	}

	public void setWallet(int wallet) {
		this.wallet = wallet;
	}

	public int[] getRevenue() {
		return revenue;
	}

	public LocalDateTime getLastCheck() {
		return lastCheck;
	}

	public void delete() {
		DB.remove(this);
	}
}
