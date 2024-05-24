package com.titobarrios.services;

import com.titobarrios.db.CurrentDate;
import com.titobarrios.db.DB;
import com.titobarrios.model.Subscription;
import com.titobarrios.model.Ticket;
import com.titobarrios.model.User;

public class SubscriptionServ {
    public static void check() {
        for (User user : DB.getUsers())
            checkUser(user);
    }

    public static void checkUser(User user) {
        CurrentDate.refresh();
        for (Subscription subscription : user.getSubscriptions())
            if (subscription.getDayOfWeek().equals(CurrentDate.get().getDayOfWeek()))
                new Ticket(user, user, null, VehicleServ.findBestVehicle(subscription.getRouteSeq().getVehicles()),
                        subscription.getRoutes(), subscription);
    }
}
