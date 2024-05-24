package com.titobarrios.view.admin;

import com.titobarrios.model.Admin;
import com.titobarrios.view.Console;
import com.titobarrios.view.admin.archive.ArchiveAMenu;
import com.titobarrios.view.admin.companies.CompaniesAMenu;
import com.titobarrios.view.admin.coupons.CouponsAMenu;
import com.titobarrios.view.admin.revenue.RevenueAMenu;
import com.titobarrios.view.admin.route_seqs.RouteSeqAMenu;
import com.titobarrios.view.admin.users.UsersAMenu;
import com.titobarrios.view.admin.vehicles.VehiclesAMenu;
import com.titobarrios.view.home.Home;

public class AdminMainMenu {
    private Admin admin;

    public AdminMainMenu(Admin admin) {
        this.admin = admin;
        menu();
    }

    private void menu() {
        Console.log(
                "1. Ingresos   |   2. Usuarios   |   3. Empresas   |   4. Secuencias de Rutas   |   5. Vehículos   |   6. Cupones   |   7. Archivo\n0. Cerrar sesión");
        int option = Console.readNumber();
        switch (option) {
            case 1:
                new RevenueAMenu(admin);
            case 2:
                new UsersAMenu(admin);
            case 3:
                new CompaniesAMenu(admin);
            case 4:
                new RouteSeqAMenu(admin);
            case 5:
                new VehiclesAMenu(admin);
            case 6:
                new CouponsAMenu(admin);
            case 7:
                new ArchiveAMenu(admin);
            case 0:
                Console.log("Sesión cerrada correctamente");
                new Home();
            default:
                menu();
        }
    }
}
