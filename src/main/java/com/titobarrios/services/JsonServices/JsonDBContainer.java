package com.titobarrios.services.JsonServices;

import java.util.List;

import com.titobarrios.model.Coupon;
import com.titobarrios.model.RouteSequence;
import com.titobarrios.db.DB;
import com.titobarrios.model.Account;
import com.titobarrios.model.Admin;
import com.titobarrios.model.Vehicle;

public class JsonDBContainer {

    private Admin admin;

    private List<Account> accounts;

    private List<RouteSequence> routeSeqs;

    private List<Vehicle> vehicles;

    private List<Coupon> coupons;

    public void SynchronizeThis() {
        admin = DB.getAdmin();
        accounts = DB.getAccountsList();
        routeSeqs = DB.getRouteSeqsList();
        vehicles = DB.getVehiclesList();
        coupons = DB.getCouponsList();
    }

    public void SynchronizeDB() {
        DB.setAdmin(admin);
        DB.setAccounts(accounts);
        DB.setRouteSeqs(routeSeqs);
        DB.setVehicles(vehicles);
        DB.setCoupons(coupons);
    }

    public Admin getAdmin() {
        return admin;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public List<RouteSequence> getRouteSeqs() {
        return routeSeqs;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public List<Coupon> getCoupons() {
        return coupons;
    }
}