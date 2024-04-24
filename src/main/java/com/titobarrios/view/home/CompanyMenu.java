package com.titobarrios.view.home;

import com.titobarrios.view.company.LogInMenu;
import com.titobarrios.view.company.Register;
import com.titobarrios.view.Console;

public class CompanyMenu {
    public CompanyMenu() {
        menu();
    }

    private void menu() {
        Console.log("1. Iniciar sesión\n2. Registrarse\n0. Salir");
        int option = Console.readNumber();
        if (option == 0)
            new Home();
        switch (option) {
            case 1:
                new LogInMenu();
            case 2:
                new Register();
            default:
                menu();
        }
    }
}
