package com.titobarrios.controller;

import com.titobarrios.db.CurrentDate;
import com.titobarrios.db.DB;
import com.titobarrios.model.Subscription;
import com.titobarrios.model.Ticket;
import com.titobarrios.model.User;

public class SubscriptionCtrl {
    private Subscription subscription;

    public SubscriptionCtrl(Subscription subscription) {
        this.subscription = subscription;
    }

}
