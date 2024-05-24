package com.titobarrios.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.titobarrios.db.DB;
import com.titobarrios.model.interfaces.Id;

public abstract class Account implements Id {

	private String id;
	private String password;

	public Account(String id, String password) {
		this.id = id;
		this.password = new BCryptPasswordEncoder().encode(password);
		DB.store(this);
	}

	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = new BCryptPasswordEncoder().encode(password);
	}

	public void delete() {
		DB.remove(this);
	}
}
