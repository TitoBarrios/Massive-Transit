package com.titobarrios.view.admin.archive;

import com.titobarrios.db.Archive;
import com.titobarrios.model.Account;
import com.titobarrios.model.Admin;
import com.titobarrios.model.Coupon;
import com.titobarrios.model.RouteSequence;
import com.titobarrios.model.Vehicle;
import com.titobarrios.view.Console;
import com.titobarrios.view.admin.AdminMainMenu;

public class ArchiveAMenu {
    private Admin admin;

    public ArchiveAMenu(Admin admin) {
        this.admin = admin;
        menu();
    }

    private void menu() {
        Console.log(
                "Archivo:\n1. Ver administradores   |   2. Ver Todas las Cuentas   |   3. Ver Secuencias de Rutas   |   4. Ver Vehículos   |   5. Ver Cupones");
        int option = Console.readNumber();
        switch (option) {
            case 1:
                showAdmins();
                menu();
            case 2:
                showAccounts();
                menu();
            case 3:
                showRouteSeqs();
                menu();
            case 4:
                showVehicles();
                menu();
            case 5:
                showCoupons();
                menu();
            case 0:
                new AdminMainMenu(admin);
            default:
                menu();
        }
    }

    private void showAdmins() {
        for (Admin admin : Archive.getAdmins())
            Console.log("Id: " + admin.getId() + "   Password: " + admin.getPassword());
    }

    private void showAccounts() {
        for (Account account : Archive.getAccounts())
            Console.log("Id: " + account.getId() + "   Password: " + account.getPassword());
    }

    private void showRouteSeqs() {
        for (RouteSequence routeSeq : Archive.getRouteSeqs())
            Console.log(routeSeq.info() + "\n");
    }

    private void showVehicles() {
        for (Vehicle vehicle : Archive.getVehicles())
            Console.log(vehicle.info() + "\n");
    }

    private void showCoupons() {
        for (Coupon coupon : Archive.getCoupons())
            Console.log(coupon.info() + "\n");
    }
}
