package model;

public enum VehicleType {
	AIRPLANE("avión", "aviones", "Avión", 0), 
	BUS("bus", "buses", "Bus", 1), SHIP("barco", "barcos", "Barco", 2),
	TRAVEL_BUS("bus de viaje", "buses de viaje", "Bus de Viaje", 3);

	private VehicleType(String name, String pluralName, String upperCaseName, int value) {
		this.name = name;
		this.pluralName = pluralName;
		this.upperCaseName = upperCaseName;
		this.value = value;
	}

	private String name;
	private String pluralName;
	private String upperCaseName;
	private int value;

	public static VehicleType createByInteger(int value) {
		switch (value) {
		case 0:
			return AIRPLANE;
		case 1:
			return BUS;
		case 2:
			return SHIP;
		case 3:
			return TRAVEL_BUS;
		default:
			return BUS;
		}
	}

	public String getName() {
		return name;
	}

	public String getPluralName() {
		return pluralName;
	}

	public String getUpperCaseName() {
		return upperCaseName;
	}

	public int getValue() {
		return value;
	}
}
