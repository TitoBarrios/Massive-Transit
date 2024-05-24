package com.titobarrios.constants;

public enum Value {
	CURRENT(0), MAXIMUM(1),
	STARTING(0), EXPIRATION(1);
	
	Value (int value) {
		this.value = value;
	}
	
	private int value;
	
	public int value() {
		return value;
	}
}
