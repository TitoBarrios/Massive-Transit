package com.titobarrios.services;

import com.titobarrios.db.CurrentDate;
import com.titobarrios.db.DB;
import com.titobarrios.model.Subscription;
import com.titobarrios.model.Ticket;
import com.titobarrios.model.User;

public class SubscriptionServ {
        public void check() {
        for (User user : DB.getUsers())
            checkUser(user);
    }

    public void checkUser(User user) {
        for (Subscription subscription : user.getSubscriptions())
            if (subscription.getDayOfWeek().equals(CurrentDate.get().getDayOfWeek()))
                new Ticket(user, user, null, VehicleServ.findBestVehicle(subscription.getRouteSeq().getVehicles()),
                        subscription.getRoutes(), subscription);
    }
}
