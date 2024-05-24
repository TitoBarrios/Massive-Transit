package com.titobarrios.view.admin.coupons;

import com.titobarrios.model.Admin;
import com.titobarrios.view.Console;
import com.titobarrios.view.admin.AdminMainMenu;

public class CouponsAMenu {
    private Admin admin;

    public CouponsAMenu(Admin admin) {
        this.admin = admin;
        menu();
    }

    private void menu() {
        Console.log("1. Ver cupones   |   2. Buscar cupón");
        int option = Console.readNumber();
        switch (option) {
            case 1:
                new CouponsA(admin);
            case 2:
                new SearchCouponA(admin);
            case 0:
                new AdminMainMenu(admin);
            default:
                menu();
        }
    }
}
