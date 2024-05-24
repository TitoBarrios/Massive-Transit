package com.titobarrios.view.company.route_seqs;

import com.titobarrios.model.Company;
import com.titobarrios.model.RouteSequence;
import com.titobarrios.view.Console;

public class MyRouteSeqs {
    private Company company;

    public MyRouteSeqs(Company company) {
        this.company = company;
        menu();
    }

    public void menu() {
        RouteSequence[] routeSeqs = company.getRouteSeqs();
        Console.log("Tiene " + routeSeqs.length + " secuencias de rutas registradas");
        for(RouteSequence routeSeq : routeSeqs){
            routeSeq.refresh();
            Console.log("\n\n" + routeSeq.getType() + " " + routeSeq.info());
        }
        Console.log("Escriba cualquier tecla para volver");
        Console.readData();
        new RSMainMenu(company);
    }
}
