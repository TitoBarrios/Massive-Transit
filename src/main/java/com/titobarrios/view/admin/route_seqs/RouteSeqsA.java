package com.titobarrios.view.admin.route_seqs;

import com.titobarrios.controller.AdminCtrl;
import com.titobarrios.db.DB;
import com.titobarrios.model.Admin;
import com.titobarrios.model.RouteSequence;
import com.titobarrios.view.Console;

public class RouteSeqsA {
    private Admin admin;
    private AdminCtrl ctrl;

    public RouteSeqsA(Admin admin) {
        this.admin = admin;
        ctrl = new AdminCtrl(admin);
        menu();
    }

    private void menu() {
        Console.log("Ls secuencias de rutas inscritas son las siguientes");
        for (RouteSequence routeSeq : DB.getRouteSeqs())
            Console.log(ctrl.routeSeqAdminInfo(routeSeq));
        Console.log("Digite cualquier tecla para continuar");
        Console.readData();
        new RouteSeqAMenu(admin);
    }
}
