package com.titobarrios.view.user.subscription;

import java.time.DayOfWeek;

import com.titobarrios.constants.VType;
import com.titobarrios.controller.SubscriptionCtrl;
import com.titobarrios.model.Route;
import com.titobarrios.model.Route.StopType;
import com.titobarrios.model.RouteSequence;
import com.titobarrios.model.Subscription;
import com.titobarrios.model.User;
import com.titobarrios.view.Console;

public class CreateSubscription {
    private SubscriptionCtrl ctrl;
    private User user;

    public CreateSubscription(User user) {
        this.user = user;
        ctrl = new SubscriptionCtrl(user);
        menu();
    }

    private void menu() {
        Console.log("Menú de Creación\nSeleccione el tipo de vehículo" + VType.menu() + "\n0. Volver");
        VType type = ctrl.selectType();
        Console.log(
                "¿En qué día de la semana deseas agregar a tu suscripción?\n1. Lunes    2. Martes    3. Miércoles   4. Jueves\n5. Viernes    6. Sábado   7. Domingo");
        DayOfWeek paymentDay = ctrl.selectPaymentDay();
        Console.log("Selecciona una de las siguientes secuencias");
        RouteSequence routeSeq = ctrl.selectRouteSeq(type, paymentDay);
        Console.log("Seleccione la ruta por la cual va a entrar");
        Route entry = ctrl.selectRoute(routeSeq);
        Console.log("Seleccione la ruta por la cual va a salir");
        Route exit = ctrl.selectRoute(routeSeq);
        if (entry.getStops()[Route.StopType.ENTRY.ordinal()].isAfter(exit.getStops()[Route.StopType.EXIT.ordinal()])) {
            Console.log("La ruta de salida no puede ir antes de la entrada, por favor, inténtalo de nuevo");
            menu();
        }
        Console.log("Su suscripción es la siguiente: " + "\nSe cobrará los: " + paymentDay + "\nSecuencia: "
                + routeSeq.getId() + ".\nEntrada: " + entry.getStops()[StopType.ENTRY.ordinal()].toLocalTime()
                + ".    Salida: " + exit.getStops()[StopType.EXIT.ordinal()].toLocalTime()
                + ".\n\n1. Confirmar.  0. Cancelar.");
        int option = Console.readNumber();
        if (option != 1) {
            Console.log("Se ha cancelado la operación");
            new SMainMenu(user);
        }
        new Subscription(user, paymentDay, routeSeq, new Route[] { entry, exit });
        Console.log("Se ha creado su suscripción correctamente");
        new SMainMenu(user);
    }

}
