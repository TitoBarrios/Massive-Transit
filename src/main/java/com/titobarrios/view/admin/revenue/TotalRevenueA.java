package com.titobarrios.view.admin.revenue;

import com.titobarrios.controller.RevenueCtrl;
import com.titobarrios.db.CurrentDate;
import com.titobarrios.model.Admin;
import com.titobarrios.utils.RevenueUtil;
import com.titobarrios.view.Console;

public class TotalRevenueA {
    private Admin admin;

    public TotalRevenueA(Admin admin) {
        this.admin = admin;
        menu();
    }

    private void menu() {
        Console.log("Ingresos totales en la aplicación:\n"
                + RevenueCtrl.revenueInfo(RevenueUtil.appRevenue(), CurrentDate.get()));
        Console.log("Digite cualquier tecla para volver");
        Console.readData();
        new RevenueAMenu(admin);
    }

}
