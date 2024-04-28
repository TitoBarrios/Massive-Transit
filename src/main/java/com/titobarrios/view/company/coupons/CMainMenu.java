package com.titobarrios.view.company.coupons;

import com.titobarrios.model.Company;
import com.titobarrios.view.Console;
import com.titobarrios.view.company.MainMenu;

public class CMainMenu {
    private Company company;

    public CMainMenu(Company company) {
        this.company = company;
        menu();
    }

    private void menu() {
        Console.log("1. Mis cupones   |   2. Crear nuevo cupón   |   3. Editar cupón existente   |   4. Eliminar cupón");
        int option = Console.readNumber();
        switch(option) {
            case 1:
            new MyCoupons(company);
            case 2:
            new CreateCoupon(company);
            case 3:
            new EditCoupon(company);
            case 4:
            new DeleteCoupon(company);
            case 0:
            new MainMenu(company);
            default:
            Console.log("Opción inválida, por favor, inténtelo de nuevo");
            menu();
        }
    }
}
