package com.titobarrios.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.titobarrios.model.Coupon;
import com.titobarrios.model.RouteSequence;
import com.titobarrios.model.User;
import com.myproperties.PropCtrl;
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
    private static PropCtrl propCtrl;

    public static void initialize() {
        accounts = new ArrayList<Account>();
        routeSeqs = new ArrayList<RouteSequence>();
        vehicles = new ArrayList<Vehicle>();
        coupons = new ArrayList<Coupon>();
        propCtrl = new PropCtrl("src/main/resources/config.properties");
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

    public static PropCtrl getPropCtrl() {
        return propCtrl;
    }

    public static void changeAdmin(Admin newAdmin) {
        admin = newAdmin;
        Archive.store(admin);
    }

    public static List<Account> getAccountsList() {
        return accounts;
    }

    public static List<RouteSequence> getRouteSeqsList() {
        return routeSeqs;
    }

    public static List<Vehicle> getVehiclesList() {
        return vehicles;
    }

    public static List<Coupon> getCouponsList() {
        return coupons;
    }

    public static void setAdmin(Admin admin) {
        DB.admin = admin;
    }

    public static void setAccounts(List<Account> accounts) {
        DB.accounts = accounts;
    }

    public static void setRouteSeqs(List<RouteSequence> routeSeqs) {
        DB.routeSeqs = routeSeqs;
    }

    public static void setVehicles(List<Vehicle> vehicles) {
        DB.vehicles = vehicles;
    }

    public static void setCoupons(List<Coupon> coupons) {
        DB.coupons = coupons;
    }

}
