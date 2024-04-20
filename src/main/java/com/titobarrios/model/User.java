package com.titobarrios.model;

import java.util.Arrays;

import com.titobarrios.db.DB;

public class User {

	public static enum Type {
		USER, COMPANY;
	}

	public static final int MAX_TICKETS = 50;
	public static final int MAX_SUBSCRIPTIONS = 30;
	public static final int MAX_RELATIONSHIPS = 50;

	private Type type;
	private User[] relationships;
	private Subscription[] subscriptions;
	private Ticket[] ticketHistory;
	private String name;
	private String password;
	private int wallet;

	public User(String name, String password) {
		this.name = name;
		this.password = password;
		ticketHistory = new Ticket[MAX_TICKETS];
		subscriptions = new Subscription[MAX_SUBSCRIPTIONS];
		relationships = new User[MAX_RELATIONSHIPS];
		DB.store(this);
	}

	public void add(User relationship) {
		for (int i = 0; i < relationships.length; i++)
			if (relationships[i] == null) {
				relationships[i] = relationship;
				break;
			}
	}

	public void add(Subscription subscription) {
		for (int i = 0; i < this.subscriptions.length; i++) {
			if (this.subscriptions[i] == null) {
				this.subscriptions[i] = subscription;
				break;
			}
		}
	}

	public void add(Ticket ticket) {
		for (int i = 0; i < ticketHistory.length; i++)
			if (ticketHistory[i] == null) {
				ticketHistory[i] = ticket;
				break;
			}
	}

	public void deleteRelationship(int relationshipArrayNumber) {
		relationships[relationshipArrayNumber] = null;
	}

	public void deleteSubscription(int subscriptionArrayNumber) {
		subscriptions[subscriptionArrayNumber] = null;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public User[] getRelationships() {
		return relationships;
	}

	public void setRelationships(User[] relationships) {
		this.relationships = relationships;
	}

	public Subscription[] getSubscriptions() {
		return subscriptions;
	}

	public void setSubscriptions(Subscription[] subscriptions) {
		this.subscriptions = subscriptions;
	}

	public Ticket[] getTicketHistory() {
		return ticketHistory;
	}

	public void setTicketHistory(Ticket[] ticketHistory) {
		this.ticketHistory = ticketHistory;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setWallet(int wallet) {
		this.wallet = wallet;
	}

	public int getWallet() {
		return wallet;
	}

	@Override
	public String toString() {
		return "User [type=" + type + ", relationships=" + Arrays.toString(relationships) + ", subscriptions="
				+ Arrays.toString(subscriptions) + ", ticketHistory=" + Arrays.toString(ticketHistory) + ", name="
				+ name + ", password=" + password + ", wallet=" + wallet + "]";
	}
}
