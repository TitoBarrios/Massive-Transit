package com.titobarrios.view.company.coupons;

import com.titobarrios.model.Company;
import com.titobarrios.model.Coupon;
import com.titobarrios.view.Console;

public class DeleteCoupon {
    private Company company;

    public DeleteCoupon(Company company) {
        this.company = company;
        menu();
    }

    private void menu() {
        Coupon[] coupons = company.getCoupons();
        for (int i = 0; i < coupons.length; i++)
            Console.log((i + 1) + ". " + coupons[i].getName());
        Console.log("\nSeleccione un cupón para eliminar");
        int option = Console.readNumber();
        if (option == 0)
            new CMainMenu(company);
        if (option < 0 || option > coupons.length)
            menu();
        Coupon selected = coupons[option - 1];
        Console.log("Está seguro que desea eliminar el cupón " + selected.getName() + "?\n1. Sí   2. No");
        if (option == 1)
            selected.delete();
        if (option == 2)
            Console.log("Se ha cancelado la operación");
        if (option < 0 || option > 2) {
            Console.log("Opción inválida, por favor, inténtelo de nuevo");
            menu();
        }
        new CMainMenu(company);
    }
}
