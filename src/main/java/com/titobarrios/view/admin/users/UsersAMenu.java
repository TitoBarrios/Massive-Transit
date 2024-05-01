package com.titobarrios.view.admin.users;

import com.titobarrios.model.Admin;
import com.titobarrios.view.Console;
import com.titobarrios.view.admin.AdminMainMenu;

public class UsersAMenu {
    private Admin admin;

    public UsersAMenu(Admin admin) {
        this.admin = admin;
        menu();
    }

    private void menu() {
        Console.log("1. Ver todos los usuarios y su información   |   2. Buscar Usuario");
        int option = Console.readNumber();
        switch (option) {
            case 1:
                new UsersA(admin);
            case 2:
                new SearchUserA(admin);
            case 0:
                new AdminMainMenu(admin);
            default:
                menu();
        }
    }
}
