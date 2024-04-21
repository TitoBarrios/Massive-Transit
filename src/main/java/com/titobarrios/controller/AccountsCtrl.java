package com.titobarrios.controller;

import com.titobarrios.db.DB;
import com.titobarrios.model.Ticket;
import com.titobarrios.model.Account;

public class AccountsCtrl {
    public static boolean isIdAvailable(String id) {
        for (Account account : DB.getAccounts())
            if (account.getId().equals(id))
                return false;
        return true;
    }

    public static void setTicketAvailability(Ticket ticket, boolean availability) {
        for (Account account : DB.getAccounts())
            for (Ticket currentTicket : account.getTickets())
                if (currentTicket.equals(ticket))
                    currentTicket.setAvailable(availability);
    }
}
