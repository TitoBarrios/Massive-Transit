package com.titobarrios.view.company;

import com.titobarrios.model.Company;
import com.titobarrios.view.Console;
import com.titobarrios.view.company.coupons.CMainMenu;
import com.titobarrios.view.company.financial_data.FMainMenu;
import com.titobarrios.view.company.route_seqs.RSMainMenu;
import com.titobarrios.view.company.vehicles.VMainMenu;
import com.titobarrios.view.home.Home;

public class MainMenu {
    public Company company;

    public MainMenu(Company company) {
        this.company = company;
        menu();
    }

    private void menu() {
        Console.log(
                "¿Qué desea hacer?\n1. Datos financieros       2. Cupones\n3. Vehículos      4. Crear Ruta\n0. Cerrar sesión");
        int option = Console.readNumber();
        switch (option) {
            case 1:
                new FMainMenu(company);
            case 2:
                new CMainMenu(company);
            case 3:
                new VMainMenu(company);
            case 4:
                new RSMainMenu(company);
            case 0:
                new Home();
            default:
                Console.log("Opción inválida");
                menu();
        }
    }
}
