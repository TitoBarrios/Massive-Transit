package com.titobarrios.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.titobarrios.model.User;
import com.titobarrios.model.Vehicle;

public class DB {
    
    private static Map<String, User> users;
    
    private static List<Vehicle> vehicles;

    public DB() {}
    
    public static void initialize() {
        users = new HashMap<>();
        vehicles = new ArrayList<Vehicle>();
    }

    public static void store(Vehicle vehicle) {
        vehicles.add(vehicle);    
    }

    public static void store(User user) {
        users.put(user.getPassword(), user);
    }

    public static Vehicle[] getVehicles() {
        return vehicles.toArray(new Vehicle[0]);
    }

    public static User[] getUsers() {
        return users.values().toArray(new User[0]);
    }
}
