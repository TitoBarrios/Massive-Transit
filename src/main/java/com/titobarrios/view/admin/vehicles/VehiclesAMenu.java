package com.titobarrios.view.admin.vehicles;

import com.titobarrios.model.Admin;
import com.titobarrios.view.Console;
import com.titobarrios.view.admin.AdminMainMenu;

public class VehiclesAMenu {
    private Admin admin;

    public VehiclesAMenu(Admin admin) {
        this.admin = admin;
        menu();
    }

    private void menu() {
        Console.log("1. Ver vehículos   |   2. Buscar vehículo");
        int option = Console.readNumber();
        switch (option) {
            case 1:
                new VehiclesA(admin);
            case 2:
                new SearchVehicleA(admin);
            case 0:
                new AdminMainMenu(admin);
            default:
                menu();
        }
    }
}
