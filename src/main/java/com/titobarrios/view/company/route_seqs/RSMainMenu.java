package com.titobarrios.view.company.route_seqs;

import com.titobarrios.model.Company;
import com.titobarrios.view.Console;
import com.titobarrios.view.company.MainMenu;

public class RSMainMenu {
    private Company company;

    public RSMainMenu(Company company) {
        this.company = company;
        menu();
    }

    private void menu() {
        Console.log(
                "1. Mis secuencias de rutas   |   2. Crear nueva secuencia de rutas  |  3. Editar secuencia de rutas   |   4. Eliminar");
        int option = Console.readNumber();

        switch (option) {
            case 1:
                new MyRouteSeqs(company);
            case 2:
                new CreateRouteSeq(company);
            case 3:
                new EditRouteSeq(company);
            case 4:
                new DeleteRouteSeq(company);
            case 0:
                new MainMenu(company);
            default:
                Console.log("Opción inválida, por favor, inténtelo de nuevo\n");
        }
    }
}
