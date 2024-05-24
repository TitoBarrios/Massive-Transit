package com.titobarrios.services;

import com.titobarrios.model.Company;
import com.titobarrios.model.Vehicle;

public class FinancesServ {

    public static int[] companiesRevenue(Company[] companies) {
        int[] revenue = new int[4];
        for (Company company : companies) {
            for (int i = 0; i < revenue.length; i++)
                revenue[i] += company.getRevenue()[i];
        }
        return revenue;
    }

    public static int[] vehiclesRevenue(Vehicle[] vehicles) {
        int[] revenue = new int[4];
        for (Vehicle vehicle : vehicles) {
            for (int i = 0; i < revenue.length; i++)
                revenue[i] += vehicle.getRevenue()[i];
        }
        return revenue;
    }
}
