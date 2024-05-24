package com.titobarrios.view.admin.companies;

import com.titobarrios.model.Admin;
import com.titobarrios.view.Console;
import com.titobarrios.view.admin.AdminMainMenu;

public class CompaniesAMenu {
    private Admin admin;

    public CompaniesAMenu(Admin admin) {
        this.admin = admin;
        menu();
    }

    private void menu() {
        Console.log("1. Ver empresas y su información   |   2. Buscar empresa");
        int option = Console.readNumber();
        switch (option) {
            case 1:
                new CompaniesA(admin);
            case 2:
                new SearchCompanyA(admin);
            case 0:
                new AdminMainMenu(admin);
            default:
                menu();
        }
    }
}
