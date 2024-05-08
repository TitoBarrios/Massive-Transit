package com.titobarrios.view.admin.revenue;

import com.titobarrios.controller.RevenueCtrl;
import com.titobarrios.db.DB;
import com.titobarrios.model.Admin;
import com.titobarrios.model.User;
import com.titobarrios.view.Console;

public class RevenuePerUserA {
    private Admin admin;

    public RevenuePerUserA(Admin admin) {
        this.admin = admin;
        menu();
    }

    private void menu() {
        Console.log("Ingresos por usuario");
        for (User user : DB.getUsers())
            Console.log(user.getId()  + "\n" + RevenueCtrl.revenueInfo(user.getRevenue(), user.getLastCheck()) + "\n");
        Console.log("Digite cualquier tecla para continuar");
        Console.readData();
        new RevenueAMenu(admin);
    }
}
