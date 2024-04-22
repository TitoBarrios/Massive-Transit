package com.titobarrios.model;

import java.util.ArrayList;

import com.titobarrios.db.DB;

public class User extends Account {

	private ArrayList<User> relationships;
	private ArrayList<Subscription> subscriptions;
	private int wallet;

	public User(String id, String password) {
		super(id, password);
		relationships = new ArrayList<User>();
		subscriptions = new ArrayList<Subscription>();
		DB.store(this);
	}

	public void add(User relationship) {
		relationships.add(relationship);
	}

	public void add(Subscription subscription) {
		subscriptions.add(subscription);
	}

	public void deleteRelationship(int arrayNumber) {
		relationships.remove(arrayNumber);
	}

	public void deleteSubscription(int arrayNumber) {
		subscriptions.remove(arrayNumber);
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
}
