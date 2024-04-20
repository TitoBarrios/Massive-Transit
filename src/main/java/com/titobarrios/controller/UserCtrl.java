package com.titobarrios.controller;

import com.titobarrios.db.DB;
import com.titobarrios.model.Ticket;
import com.titobarrios.model.User;

public class UserCtrl {
    public boolean isUserAvailable(String name) {
        for (User user : DB.getUsers())
            if (user.getName().equals(name))
                return false;
        return true;
    }

    public void setUsersTicketAvailability(Ticket ticket, boolean availability) {
        for (User user : DB.getUsers())
            for (Ticket currentTicket : user.getTicketHistory())
                if (currentTicket.equals(ticket))
                    currentTicket.setAvailable(availability);
    }
}
