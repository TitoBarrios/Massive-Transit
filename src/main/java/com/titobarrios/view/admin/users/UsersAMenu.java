package com.titobarrios.view.admin.users;

import com.titobarrios.model.Admin;
import com.titobarrios.services.SubscriptionServ;
import com.titobarrios.view.Console;
import com.titobarrios.view.admin.AdminMainMenu;

public class UsersAMenu {
    private Admin admin;

    public UsersAMenu(Admin admin) {
        this.admin = admin;
        menu();
    }

    private void menu() {
        Console.log("1. Ver todos los usuarios y su información   |   2. Buscar Usuario   |   3. Refrescar suscripciones");
        int option = Console.readNumber();
        switch (option) {
            case 1:
                new UsersA(admin);
            case 2:
                new SearchUserA(admin);
            case 3:
                SubscriptionServ.check();
                Console.log("Se han cobrado todas las suscripciones posibles");
                menu();
            case 0:
                new AdminMainMenu(admin);
            default:
                menu();
        }
    }
}
