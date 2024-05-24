package com.titobarrios.view.admin.route_seqs;

import com.titobarrios.controller.AdminCtrl;
import com.titobarrios.db.DB;
import com.titobarrios.model.Admin;
import com.titobarrios.model.Coupon;
import com.titobarrios.model.RouteSequence;
import com.titobarrios.model.Vehicle;
import com.titobarrios.view.Console;

public class SearchRouteSeqA {
    private AdminCtrl ctrl;
    private Admin admin;

    public SearchRouteSeqA(Admin admin) {
        this.admin = admin;
        ctrl = new AdminCtrl(admin);
        menu();
    }

    private void menu() {
        Console.log("Menú de búsqueda de secuencia de rutas");
        RouteSequence routeSeq = ctrl.selectRouteSequence(DB.getRouteSeqs());
        routeSeqOpt(routeSeq);
        new RouteSeqAMenu(admin);
    }

    private void routeSeqOpt(RouteSequence routeSeq) {
        routeSeq.refresh();
        Console.log(
                "Qué desea hacer con esta secuencia de rutas?\n1. Ver más información   |   2. Ver vehículos   |   3. Ver cupones");
        int option = Console.readNumber();
        switch (option) {
            case 1:
                routeSeq.info();
                Console.log("Digita cualquier tecla para continuar");
                routeSeqOpt(routeSeq);
            case 2:
                showVehicles(routeSeq);
                routeSeqOpt(routeSeq);
            case 3:
                showCoupons(routeSeq);
                routeSeqOpt(routeSeq);
            case 0:
                break;
            default:
                routeSeqOpt(routeSeq);
        }
    }

    private void showVehicles(RouteSequence routeSeq) {
        Console.log("Los vehículos asignados a esta secuencias de rutas son los siguientes:");
        for (Vehicle vehicle : routeSeq.getVehicles())
            Console.log(vehicle.info() + "\n");
    }

    private void showCoupons(RouteSequence routeSeq) {
        Console.log("Los cupones asignados a esta secuencia de rutas son los siguientes:");
        for (Coupon coupon : routeSeq.getCoupons())
            Console.log(coupon.info() + "\n");
    }
}
