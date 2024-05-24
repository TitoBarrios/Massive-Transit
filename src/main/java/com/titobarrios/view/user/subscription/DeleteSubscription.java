package com.titobarrios.view.user.subscription;

import com.titobarrios.controller.SubscriptionCtrl;
import com.titobarrios.model.Subscription;
import com.titobarrios.model.User;
import com.titobarrios.view.Console;

public class DeleteSubscription {
        private SubscriptionCtrl ctrl;
    private User user;

    public DeleteSubscription(User user) {
        this.user = user;
        ctrl = new SubscriptionCtrl(user);
        menu();
    }

    private void menu() {
        Subscription subscription = ctrl.selectSubscription();
        delete(subscription);
        new SMainMenu(user);
    }

    private void delete(Subscription subscription) {
        Console.log("Estás seguro que deseas eliminar la suscripción\n" + subscription.info() + "\n\n1. Eliminar   0. Cancelar");
        int option = Console.readNumber();
        if(option == 0)
            Console.log("Se ha cancelado la operación");
        if(option < 0 || option > 1)
            menu();
        if(option == 1){
            subscription.delete();
            Console.log("Se ha eliminado la suscripción correctamente");
        }
    }
}
