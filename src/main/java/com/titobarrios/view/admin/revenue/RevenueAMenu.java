package com.titobarrios.view.admin.revenue;

import com.titobarrios.model.Admin;
import com.titobarrios.view.Console;
import com.titobarrios.view.admin.AdminMainMenu;

public class RevenueAMenu {
    private Admin admin;

    public RevenueAMenu(Admin admin) {
        this.admin = admin;
        menu();
    }

    private void menu() {
        Console.log(
                "1. Ingresos Totales   |   2. Ingresos por Empresa   |   3. Ingresos por Usuario   |   4. Menú de Búsqueda");
        int option = Console.readNumber();
        switch (option) {
            case 1:
                new TotalRevenueA(admin);
            case 2:
                new RevenuePerCompanyA(admin);
            case 3:
                new RevenuePerUserA(admin);
            case 4:
                new SearchRMenuA(admin);
            case 0:
                new AdminMainMenu(admin);
            default:
                menu();
        }
    }

}
