package com.titobarrios.view.admin.revenue;

import com.titobarrios.controller.RevenueCtrl;
import com.titobarrios.db.DB;
import com.titobarrios.model.Admin;
import com.titobarrios.model.Company;
import com.titobarrios.view.Console;

public class RevenuePerCompanyA {
    private Admin admin;

    public RevenuePerCompanyA(Admin admin) {
        this.admin = admin;
        menu();
    }

    private void menu() {
        Console.log("Ingresos por empresa\n");
        for (Company company : DB.getCompanies())
            Console.log(company.getId() + "\n" + RevenueCtrl.revenueInfo(company.getRevenue(), company.getLastCheck())
                    + "\n");
        Console.log("Digita cualquier tecla para continuar");
        Console.readData();
        new RevenueAMenu(admin);
    }
}
