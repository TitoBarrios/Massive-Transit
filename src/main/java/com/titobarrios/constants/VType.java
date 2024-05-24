package com.titobarrios.constants;

public enum VType {
    AIRPLANE("avión", "aviones", "Avión"), BUS("bus", "buses", "Bus"), SHIP("barco", "barcos", "Barco"),
    TRAVEL_BUS("bus de viaje", "buses de viaje", "Bus de Viaje");

    private VType(String name, String pluralName, String upperCaseName) {
        this.name = name;
        this.pluralName = pluralName;
        this.upperCaseName = upperCaseName;
    }

    private String name;
    private String pluralName;
    private String upperCaseName;

    public String getName() {
        return name;
    }

    public String getPluralName() {
        return pluralName;
    }

    public String getUpperCaseName() {
        return upperCaseName;
    }

    public static String menu() {
        StringBuilder builder = new StringBuilder();
        builder.append("\n1. ").append(AIRPLANE.getUpperCaseName()).append("\n2. ").append(BUS.getUpperCaseName())
                .append("\n3. ").append(SHIP.getUpperCaseName()).append("\n4. ").append(TRAVEL_BUS.getUpperCaseName());
        return builder.toString();
    }
}
