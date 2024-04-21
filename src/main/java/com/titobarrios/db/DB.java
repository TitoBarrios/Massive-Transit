package com.titobarrios.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.titobarrios.model.Coupon;
import com.titobarrios.model.RouteSequence;
import com.titobarrios.model.User;
import com.titobarrios.model.Account;
import com.titobarrios.model.Company;
import com.titobarrios.model.Vehicle;

public class DB {

    private static Map<String, Account> accounts;

    private static List<Vehicle> vehicles;

    private static List<Coupon> coupons;

    private static List<RouteSequence> routeSequences;

    public DB() {
    }

    public static void initialize() {
        accounts = new HashMap<>();
        vehicles = new ArrayList<Vehicle>();
        Archive.initialize();
    }

    public static void store(Account account) {
        accounts.put(account.getPassword(), account);
        Archive.store(account);
    }

    public static void store(Vehicle vehicle) {
        vehicles.add(vehicle);
        Archive.store(vehicle);
    }

    public static void store(Coupon coupon) {
        coupons.add(coupon);
        Archive.store(coupon);
    }

    public static void store(RouteSequence routeSequence) {
        routeSequences.add(routeSequence);
    }

    public static Vehicle[] getVehicles() {
        return vehicles.toArray(Vehicle[]::new);
    }

    public static Account[] getAccounts() {
        return accounts.values().toArray(Account[]::new);
    }

    public static Company[] getCompanies() {
        return Arrays.stream(getAccounts())
                .filter(account -> account instanceof Company)
                .map(account -> (Company) account)
                .collect(Collectors.toList()).toArray(Company[]::new);
    }

    public static User[] getUsers() {
        return Arrays.stream(getAccounts())
                .filter(account -> account instanceof User)
                .map(account -> (User) account)
                .collect(Collectors.toList()).toArray(User[]::new);
    }

    public static Coupon[] getCoupons() {
        return coupons.toArray(Coupon[]::new);
    }
}
