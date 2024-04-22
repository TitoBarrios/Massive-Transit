package com.titobarrios.view.user;

import com.titobarrios.model.User;
import com.titobarrios.model.Vehicle;
import com.titobarrios.view.Console;

public class Subscriptions {
    private User user;

    public Subscriptions(User user) {
        this.user = user;
        menu();
    }

    public void menu() {
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

    public void manage() {
        if (user.getSubscriptions().length == 0) {
            Console.log("No tiene suscripciones activas");
            menu();
        }
        Console.log("Estas son tus suscripciones activas");
        for (int i = 0; i < user.getSubscriptions().length; i++) {
            Console.log((i + 1) + user.getSubscriptions()[i].info());
        }
        Console.log("Digita el número de una suscripción para cancelarla\n0. Volver");
        int option = Console.readOption(false, user.getSubscriptions().length);
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

    public void create() {
        Console.log(
                "¿Para qué tipo de vehículo desea crear tu suscripción?\nSuscríbete y así compramos automáticamente tus tickets\n1. "
                        + Vehicle.Type.AIRPLANE.getUpperCaseName() + "\n2. " + Vehicle.Type.BUS.getUpperCaseName()
                        + "\n3. " + Vehicle.Type.SHIP.getUpperCaseName() + "\n4. "
                        + Vehicle.Type.TRAVEL_BUS.getUpperCaseName() + "\n0. Volver");
        int option = Console.readNumber();
        if (option == 0)
            menu();
        if (option < 0 || option > 4)
            create();
        Console.log(
                "¿En qué día de la semana deseas agregar a tu horario?\n1. Lunes    2. Martes    3. Miércoles   4. Jueves\n5. Viernes    6. Sábado   7. Domingo\n0. Salir");
        
        new MainMenu(user);
    }
}
