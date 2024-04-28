package com.titobarrios.view.company.route_seqs;

import com.titobarrios.model.Company;
import com.titobarrios.model.RouteSequence;
import com.titobarrios.view.Console;

public class DeleteRouteSeq {
    private Company company;

    public DeleteRouteSeq(Company company) {
        this.company = company;
        menu();
    }

    private void menu() {
        RouteSequence[] routeSeqs = company.getRouteSeqs();
        for(int i = 0; i < routeSeqs.length; i++)
            Console.log((i + 1) + ". " + routeSeqs[i].getId());
        Console.log("Seleccione una secuencia de rutas para eliminar");
        int option = Console.readNumber();
        if(option == 0) new RSMainMenu(company);
        if(option < 0 || option > routeSeqs.length) menu();
        RouteSequence selected = routeSeqs[option - 1];
        Console.log("Está seguro que desea eliminar la secuencia " + selected.getId() + "?\n1. Sí   2. No");
        option = Console.readNumber();
        if(option == 1) {
            selected.delete();
            Console.log("La secuencia de rutas se ha eliminado correctamente");
        }
        new RSMainMenu(company);
    }
}
