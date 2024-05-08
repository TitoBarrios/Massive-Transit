package com.titobarrios.view.admin.revenue;

import com.titobarrios.controller.AdminCtrl;
import com.titobarrios.controller.RevenueCtrl;
import com.titobarrios.db.DB;
import com.titobarrios.model.Admin;
import com.titobarrios.model.Company;
import com.titobarrios.model.Coupon;
import com.titobarrios.model.RouteSequence;
import com.titobarrios.model.User;
import com.titobarrios.model.Vehicle;
import com.titobarrios.view.Console;

public class SearchRMenuA {
    private AdminCtrl ctrl;
    private Admin admin;

    public SearchRMenuA(Admin admin) {
        this.admin = admin;
        ctrl = new AdminCtrl(admin);
        menu();
    }

    private void menu() {
        Console.log(
                "Menú de Búsqueda   |   1. Buscar Usuario   |   2. Buscar Empresa   |   3. Buscar Secuencia de Rutas\n4. Buscar Vehículo   |   5. Buscar Cupón");
        int option = Console.readNumber();
        switch (option) {
            case 1:
                User user = ctrl.selectUser(DB.getUsers());
                Console.log("Los ingresos generados por el usuario son los siguientes: \n"
                        + RevenueCtrl.revenueInfo(user.getRevenue(), user.getLastCheck()));
                menu();
            case 2:
                Company company = ctrl.selectCompany(DB.getCompanies());
                Console.log("Los ingresos generados por la empresa son los siguientes: \n"
                        + RevenueCtrl.revenueInfo(company.getRevenue(), company.getLastCheck()));
                menu();
            case 3:
                RouteSequence routeSeq = ctrl.selectRouteSequence(DB.getRouteSeqs());
                Console.log("Los ingresos generados por la secuencia de rutas son los siguientes: \n"
                        + RevenueCtrl.revenueInfo(routeSeq.getRevenue(), routeSeq.getLastCheck()));
                menu();
            case 4:
                Vehicle vehicle = ctrl.selectVehicle(DB.getVehicles());
                Console.log("Los ingresos generados por el vehículo son los siguientes: \n"
                        + RevenueCtrl.revenueInfo(vehicle.getRevenue(), vehicle.getLastCheck()));
                menu();
            case 5:
                Coupon coupon = ctrl.selectCoupon(DB.getCoupons());
                Console.log("Los ingresos generados por el cupón son los siguientes: \n"
                        + RevenueCtrl.revenueInfo(coupon.getRevenue(), coupon.getLastCheck()));
                menu();
            case 0:
                new RevenueAMenu(admin);
            default:
                menu();
        }
        Console.log("Digita cualquier tecla para volver");
        Console.readData();
        menu();
    }

}
