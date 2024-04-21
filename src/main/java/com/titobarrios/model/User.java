package com.titobarrios.model;

import java.util.ArrayList;

import com.titobarrios.db.DB;

public class User extends Account {

	private ArrayList<Account> relationships;
	private ArrayList<Subscription> subscriptions;
	private int wallet;

	public User(String id, String password) {
		super(id, password);
		relationships = new ArrayList<Account>();
		subscriptions = new ArrayList<Subscription>();
		DB.store(this);
	}

	public void add(Account relationship) {
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

	public Account[] getRelationships() {
		return relationships.toArray(Account[]::new);
	}

	public Subscription[] getSubscriptions() {
		return subscriptions.toArray(Subscription[]::new);
	}

	public void setWallet(int wallet) {
		this.wallet = wallet;
	}

	public int getWallet() {
		return wallet;
	}
}
