package com.titobarrios.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.titobarrios.model.Coupon;
import com.titobarrios.model.RouteSequence;
import com.titobarrios.model.User;
import com.titobarrios.model.Account;
import com.titobarrios.model.Company;
import com.titobarrios.model.Vehicle;

public class DB {

    private static List<Account> accounts;

    private static List<Vehicle> vehicles;

    private static List<Coupon> coupons;

    private static List<RouteSequence> routeSeqs;

    public DB() {
    }

    public static void initialize() {
        accounts = new ArrayList<Account>();
        vehicles = new ArrayList<Vehicle>();
        coupons = new ArrayList<Coupon>();
        routeSeqs = new ArrayList<RouteSequence>(); 
        Archive.initialize();
    }

    public static void store(Account account) {
        accounts.add(account);
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

    public static void store(RouteSequence routeSeq) {
        routeSeqs.add(routeSeq);
    }

    public static void remove(Vehicle vehicle) {
        vehicles.remove(vehicle);
    }

    public static void remove(RouteSequence routeSeq) {
        routeSeqs.remove(routeSeq);
    }

    public static Vehicle[] getVehicles() {
        return vehicles.toArray(Vehicle[]::new);
    }

    public static Account[] getAccounts() {
        return accounts.toArray(Account[]::new);
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
    
    public static RouteSequence[] getRouteSeqs() {
        return routeSeqs.toArray(RouteSequence[]::new);
    }
}
