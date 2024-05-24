package com.titobarrios.view.home;

import com.titobarrios.constants.VType;
import com.titobarrios.db.DB;
import com.titobarrios.model.RouteSequence;
import com.titobarrios.services.RouteSeqServ;
import com.titobarrios.utils.Converter;
import com.titobarrios.view.Console;

public class Routes {
    public Routes() {
        menu();
    }

    private void menu() {
        Console.log(
                "¿Para qué tipo de vehículo desea visualizar las rutas?" + VType.menu() + "\n0. Volver");
        int option = 0;
        do {
            option = Console.readNumber();
            if (option == 0)
                new Home();
            if (option < 0 || option > 4)
                Console.log("Opción inválida, inténtalo de nuevo");
        } while (option > 0 || option > 4);
        VType type = Converter.fromInt(option - 1);
        showRoutes(type);
    }

    private void showRoutes(VType type) {
        RouteSequence[] routeSeqs = RouteSeqServ.filterByType(type, DB.getRouteSeqs());
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < routeSeqs.length; i++) {
            routeSeqs[i].refresh();
            builder.append(i + 1).append(". ").append(routeSeqs[i].getId()).append("    ")
                    .append(routeSeqs[i].getOwner().getId())
                    .append("\nDisponibilidad: ").append(routeSeqs[i].isAvailable() ? "Disponible" : "No disponible")
                    .append("\n\n");
        }
        Console.log(builder.append("Seleccione una secuencia para ver más información\n0. Volver").toString());
        int option = 0;
        do {
            option = Console.readNumber();
            if (option == 0)
                new Home();
            if (option < 0 || option > routeSeqs.length)
                Console.log("Opción inválida, inténtalo de nuevo");
        } while (option > 0 && option <= routeSeqs.length);

        Console.log(routeSeqs[option - 1].info() + "\nDigite cualquier tecla para volver\n0. Salir");
        String data = Console.readData();
        if (data.equals("0"))
            new Home();
        showRoutes(type);
    }
}
