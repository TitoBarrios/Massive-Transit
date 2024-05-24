package com.titobarrios.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.titobarrios.model.Coupon;
import com.titobarrios.model.RouteSequence;
import com.titobarrios.model.User;
import com.titobarrios.model.Account;
import com.titobarrios.model.Admin;
import com.titobarrios.model.Company;
import com.titobarrios.model.Vehicle;

public class DB {

    private static Admin admin;

    private static List<Account> accounts;

    private static List<RouteSequence> routeSeqs;

    private static List<Vehicle> vehicles;

    private static List<Coupon> coupons;

    public DB() {
    }

    public static void initialize() {
        accounts = new ArrayList<Account>();
        routeSeqs = new ArrayList<RouteSequence>();
        vehicles = new ArrayList<Vehicle>();
        coupons = new ArrayList<Coupon>();
        Archive.initialize();
    }

    public static void store(Account account) {
        accounts.add(account);
        Archive.store(account);
    }

    public static void store(RouteSequence routeSeq) {
        routeSeqs.add(routeSeq);
    }

    public static void store(Vehicle vehicle) {
        vehicles.add(vehicle);
        Archive.store(vehicle);
    }

    public static void store(Coupon coupon) {
        coupons.add(coupon);
        Archive.store(coupon);
    }

    public static void remove(Account account) {
        accounts.remove(account);
    }

    public static void remove(RouteSequence routeSeq) {
        routeSeqs.remove(routeSeq);
    }

    public static void remove(Vehicle vehicle) {
        vehicles.remove(vehicle);
    }

    public static void remove(Coupon coupon) {
        coupons.remove(coupon);
    }

    public static Admin getAdmin() {
        return admin;
    }

    public static Account[] getAccounts() {
        return accounts.toArray(Account[]::new);
    }

    public static Company[] getCompanies() {
        return Arrays.stream(getAccounts()).filter(account -> account instanceof Company)
                .map(account -> (Company) account).collect(Collectors.toList()).toArray(Company[]::new);
    }

    public static User[] getUsers() {
        return Arrays.stream(getAccounts()).filter(account -> account instanceof User).map(account -> (User) account)
                .collect(Collectors.toList()).toArray(User[]::new);
    }

    public static RouteSequence[] getRouteSeqs() {
        return routeSeqs.toArray(RouteSequence[]::new);
    }

    public static Vehicle[] getVehicles() {
        return vehicles.toArray(Vehicle[]::new);
    }

    public static Coupon[] getCoupons() {
        return coupons.toArray(Coupon[]::new);
    }

    public static void setAdmin(Admin newAdmin) {
        admin = newAdmin;
    }
}
