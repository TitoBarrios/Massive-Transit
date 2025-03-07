package com.titobarrios.services.JsonServices;

import java.util.ArrayList;

import com.titobarrios.model.Coupon;
import com.titobarrios.model.RouteSequence;
import com.titobarrios.db.Archive;
import com.titobarrios.model.Account;
import com.titobarrios.model.Admin;
import com.titobarrios.model.Vehicle;

public class JsonArchiveContainer {

    private ArrayList<Admin> admins;
    private ArrayList<Account> accounts;
    private ArrayList<RouteSequence> routeSeqs;
    private ArrayList<Vehicle> vehicles;
    private ArrayList<Coupon> coupons;

    
    public void SynchronizeThis() {
        admins = Archive.getAdminsArrayList();
        accounts = Archive.getAccountsArrayList();
        routeSeqs = Archive.getRouteSeqsArrayList();
        vehicles = Archive.getVehiclesArrayList();
        coupons = Archive.getCouponsArrayList();
    }

    public void SynchronizeArchive() {
        Archive.setAdmins(admins);
        Archive.setAccounts(accounts);
        Archive.setRouteSeqs(routeSeqs);
        Archive.setVehicles(vehicles);
        Archive.setCoupons(coupons);
    }

    public ArrayList<Admin> getAdmins() {
        return admins;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public ArrayList<RouteSequence> getRouteSeqs() {
        return routeSeqs;
    }

    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }

    public ArrayList<Coupon> getCoupons() {
        return coupons;
    }

}
