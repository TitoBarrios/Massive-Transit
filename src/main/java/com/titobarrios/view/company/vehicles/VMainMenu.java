package com.titobarrios.view.company.vehicles;

import com.titobarrios.model.Company;
import com.titobarrios.view.Console;
import com.titobarrios.view.company.MainMenu;

public class VMainMenu {
    private Company company;

    public VMainMenu(Company company) {
        this.company = company;
        menu();
    }

    private void menu() {
        int option = 0;
        Console.log("1. Mis Vehículos    |    2. Crear vehículo    |    3. Editar vehículo    |    4. Eliminar vehículo.");
        option = Console.readNumber();

        switch (option) {
            case 1:
                new MyVehicles(company);
            case 2:
                new CreateVehicle(company);
            case 3:
                new EditVehicle(company);
            case 0:
                new MainMenu(company);
            default:
                menu();
        }
    }
}
