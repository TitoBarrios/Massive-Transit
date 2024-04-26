package com.titobarrios.view.user;

import java.time.DayOfWeek;

import com.titobarrios.constants.VType;
import com.titobarrios.controller.RouteSeqCtrl;
import com.titobarrios.db.DB;
import com.titobarrios.model.Route;
import com.titobarrios.model.RouteSequence;
import com.titobarrios.model.Subscription;
import com.titobarrios.model.User;
import com.titobarrios.model.Route.StopType;
import com.titobarrios.services.LaboralDays;
import com.titobarrios.utils.Converter;
import com.titobarrios.view.Console;

public class Subscriptions {
    private User user;

    public Subscriptions(User user) {
        this.user = user;
        menu();
    }

    private void menu() {
        Console.log("1. Gestionar mis suscripciones     2. Crear nueva suscripción\n0. Volver");
        int option = Console.readNumber();
        switch (option) {
            case 1:
                manage();
            case 2:
                create();
            case 0:
                new MainMenu(user);
            default:
                menu();
        }
    }

    private void manage() {
        if (user.getSubscriptions().length == 0) {
            Console.log("No tiene suscripciones activas");
            menu();
        }
        Console.log("Estas son tus suscripciones activas");
        for (int i = 0; i < user.getSubscriptions().length; i++) {
            Console.log((i + 1) + user.getSubscriptions()[i].info());
        }
        Console.log("Digita el número de una suscripción para cancelarla\n0. Volver");
        int option = Console.readNumber();
        if (option == 0) {
            Console.log("Se ha cancelado la operación");
            menu();
        }
        if (option < 0 || option > user.getSubscriptions().length)
            manage();

        Console.log("Está seguro que desea cancelar la suscripción Nro. " + option + "?");
        if (Console.readNumber() != 1) {
            Console.log("Se ha cancelado la operación");
            menu();
        }
        user.deleteSubscription(option - 1);
        Console.log("Se ha cancelado la suscripción Nro. " + option);
        menu();
    }

    private void create() {
        Console.log(
                "¿Para qué tipo de vehículo desea crear tu suscripción?\nSuscríbete y así compramos automáticamente tus tickets\n1. "
                        + VType.AIRPLANE.getUpperCaseName() + "\n2. " + VType.BUS.getUpperCaseName()
                        + "\n3. " + VType.SHIP.getUpperCaseName() + "\n4. "
                        + VType.TRAVEL_BUS.getUpperCaseName() + "\n0. Volver");
        int option = Console.readNumber();
        if (option == 0)
            menu();
        if (option < 0 || option > 4)
            create();
        VType type = Converter.fromInt(option);
        Console.log(
                "¿En qué día de la semana deseas agregar a tu horario?\n1. Lunes    2. Martes    3. Miércoles   4. Jueves\n5. Viernes    6. Sábado   7. Domingo\n0. Salir");
        option = Console.readNumber();
        if (option == 0)
            menu();
        if (option < 0 || option > 7)
            create();
        DayOfWeek paymentDay = LaboralDays.fromInt(option);
        routeSelection(type, paymentDay);
    }

    private void routeSelection(VType type, DayOfWeek paymentDay) {
        Console.log("Selecciona una de las siguientes secuencias para ver más opciones");
        RouteSequence[] applicableRouteSeqs = RouteSeqCtrl.filterByType(type,
                RouteSeqCtrl.filterByLaboralDay(paymentDay, DB.getRouteSeqs()));
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < applicableRouteSeqs.length; i++)
            builder.append("\n").append(i + 1).append(" Nombre: ").append(applicableRouteSeqs[i].getName());
        Console.log(builder.toString());
        int option = Console.readNumber();
        if (option == 0)
            menu();
        if (option < 0 || option > applicableRouteSeqs.length)
            create();
        RouteSequence routeSeq = applicableRouteSeqs[option - 1];
        builder.delete(0, builder.length() - 1);
        Console.log(routeSeq.getName());
        for (int i = 0; i < routeSeq.getRoutes().length; i++)
            builder.append(i + 1).append(". ");
        Console.log("\nDigite la ruta por la cual va a entrar. -1. Reelegir secuencia.\n0. Volver");
        if (option == -1)
            routeSelection(type, paymentDay);
        if (option == 0)
            menu();
        if (option < -1 || option > routeSeq.getRoutes().length)
            routeSelection(type, paymentDay);
        Route entry = routeSeq.getRoutes()[option - 1];

        Console.log("Digite la ruta por la cual va a salir");
        if (option == -1)
            routeSelection(type, paymentDay);
        if (option == 0)
            menu();
        if (option < -1 || option > routeSeq.getRoutes().length)
            routeSelection(type, paymentDay);
        Route exit = routeSeq.getRoutes()[option - 1];

        Console.log("Su suscripción es la siguiente: " + "\nSe cobrará los: " + paymentDay + "\nSecuencia: "
                + routeSeq.getName() + ".\nEntrada: " + entry.getStops()[StopType.ENTRY.ordinal()].toLocalTime()
                + ".    Salida: " + exit.getStops()[StopType.EXIT.ordinal()].toLocalTime()
                + ".\n\n1. Confirmar.  0. Cancelar.");
        option = Console.readNumber();
        if (option != 1) {
            Console.log("Se ha cancelado la operación");
            new MainMenu(user);
        }
        new Subscription(user, paymentDay, routeSeq, new Route[] { entry, exit });
        Console.log("Se ha creado su suscripción correctamente");
        new MainMenu(user);
    }
}
