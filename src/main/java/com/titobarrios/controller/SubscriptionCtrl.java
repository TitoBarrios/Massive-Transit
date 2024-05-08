package com.titobarrios.controller;

import java.time.DayOfWeek;

import com.titobarrios.constants.VType;
import com.titobarrios.db.DB;
import com.titobarrios.model.Route;
import com.titobarrios.model.RouteSequence;
import com.titobarrios.model.Subscription;
import com.titobarrios.model.User;
import com.titobarrios.services.RouteSeqServ;
import com.titobarrios.utils.Converter;
import com.titobarrios.utils.LaboralDays;
import com.titobarrios.view.Console;
import com.titobarrios.view.user.MainMenu;
import com.titobarrios.view.user.subscription.SMainMenu;

public class SubscriptionCtrl {
    private User user;

    public SubscriptionCtrl(User user) {
        this.user = user;
    }

    public Subscription selectSubscription() {
        Subscription[] subscriptions = user.getSubscriptions();
        if (subscriptions.length == 0) {
            Console.log("No tiene suscripciones activas");
            new SMainMenu(user);
        }
        Console.log("Estas son tus suscripciones activas");
        for (int i = 0; i < subscriptions.length; i++) {
            Console.log((i + 1) + ". " + subscriptions[i].info());
        }
        Console.log("Digita el número de una suscripción\n0. Volver");
        int option = Console.readNumber();
        if (option == 0) {
            Console.log("Se ha cancelado la operación");
            new SMainMenu(user);
        }
        if (option < 0 || option > subscriptions.length)
            return selectSubscription();
        return subscriptions[option - 1];
    }

    public VType selectType() {
        int option = Console.readNumber();
        if (option == 0)
            new MainMenu(user);
        if (option < 0 || option > 4)
            return selectType();
        return Converter.fromInt(option - 1);
    }

    public DayOfWeek selectPaymentDay() {
        int option = Console.readNumber();
        if (option == 0)
            new SMainMenu(user);
        if (option < 0 || option > 7)
            return selectPaymentDay();
        return LaboralDays.fromInt(option);
    }

    public RouteSequence selectRouteSeq(VType type, DayOfWeek paymentDay) {
        RouteSequence[] applicableRouteSeqs = RouteSeqServ.filterByType(type,
                RouteSeqServ.filterByLaboralDay(paymentDay, DB.getRouteSeqs()));
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < applicableRouteSeqs.length; i++)
            builder.append("\n").append(i + 1).append(" Nombre: ").append(applicableRouteSeqs[i].getId());
        Console.log(builder.toString());
        int option = Console.readNumber();
        if (option == 0)
            new SMainMenu(user);
        if (option < 0 || option > applicableRouteSeqs.length)
            return selectRouteSeq(type, paymentDay);
        return applicableRouteSeqs[option - 1];
    }

    public Route selectRoute(RouteSequence routeSeq) {
        Console.log(routeSeq.getId());
        for (int i = 0; i < routeSeq.getRoutes().length; i++)
            Console.log((i + 1) + ". " + routeSeq.getRoutes()[i].info());
        int option = Console.readNumber();
        if (option == 0)
            new SMainMenu(user);
        if (option < 0 || option > routeSeq.getRoutes().length)
            return selectRoute(routeSeq);
        return routeSeq.getRoutes()[option - 1];
    }
}
