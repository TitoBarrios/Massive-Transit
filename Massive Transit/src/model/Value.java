package model;

public enum Value {
	FRIEND(0), FAMILIAR(1),
	ENTRY(0), EXIT(1),
	CURRENT(0), MAXIMUM(1);
	
	Value (int value) {
		this.value = value;
	}
	
	private int value;
	
	public int getValue() {
		return value;
	}
}
