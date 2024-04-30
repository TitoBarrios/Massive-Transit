package com.titobarrios.view.user.subscription;

import com.titobarrios.controller.SubscriptionCtrl;
import com.titobarrios.model.Subscription;
import com.titobarrios.model.Ticket;
import com.titobarrios.model.User;
import com.titobarrios.view.Console;

public class MySubscriptions {
    private SubscriptionCtrl ctrl;

    public MySubscriptions(User user) {
        ctrl = new SubscriptionCtrl(user);
        menu();
    }

    private void menu() {
        Console.log("Estas son tus suscripciones activas:");
        Subscription subscription = ctrl.selectSubscription();
        seeHistory(subscription);
        Console.log("\nDigita cualquier tecla para volver");
        Console.readData();
    }

    private void seeHistory(Subscription subscription) {
        Console.log("El historial de compras de esta suscripción es el siguiente:\n");
        for(Ticket ticket : subscription.getTickets()) {
            Console.log(ticket.bill() + "\n");
        }
    }
}
