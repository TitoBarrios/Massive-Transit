package com.titobarrios.view.user.subscription;

import com.titobarrios.model.User;
import com.titobarrios.view.Console;
import com.titobarrios.view.user.MainMenu;

public class SMainMenu {
    private User user;

    public SMainMenu(User user) {
        this.user = user;
        menu();
    }

    private void menu() {
        Console.log("1. Mis suscripciones   |   2. Crear nueva suscripción   |   3. Eliminar suscripción");
        int option = Console.readNumber();
        switch (option) {
            case 1:
                new MySubscriptions(user);
            case 2:
                new CreateSubscription(user);
            case 3:
                new DeleteSubscription(user);
            case 0:
                new MainMenu(user);
            default:
                menu();
        }
    }
}
