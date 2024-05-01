package com.titobarrios.view.admin.route_seqs;

import com.titobarrios.model.Admin;
import com.titobarrios.view.Console;
import com.titobarrios.view.admin.AdminMainMenu;

public class RouteSeqAMenu {
    private Admin admin;

    public RouteSeqAMenu(Admin admin) {
        this.admin = admin;
        menu();
    }

    private void menu() {
        Console.log("1. Ver secuencias de rutas  |   2. Buscar secuencia de rutas");
        int option = Console.readNumber();
        switch (option) {
            case 1:
                new RouteSeqsA(admin);
            case 2:
                new SearchRouteSeqA(admin);
            case 0:
                menu();
            default:
                new AdminMainMenu(admin);
        }
    }
}
