package com.titobarrios.services;

import com.titobarrios.db.DB;
import com.titobarrios.model.Account;
import com.titobarrios.model.Ticket;
import com.titobarrios.model.User;

public class AccountsServ {
        public static boolean isIdAvailable(String id) {
        for (Account account : DB.getAccounts())
            if (account.getId().equals(id))
                return false;
        return true;
    }

    public static Account searchAccount(String id) {
        for (Account account : DB.getAccounts())
            if (account.getId().equals(id))
                return account;
        return null;
    }

    public static void setTicketAvailability(Ticket ticket, boolean availability) {
        for (User user : DB.getUsers())
            for (Ticket currentTicket : user.getTickets())
                if (currentTicket.equals(ticket))
                    currentTicket.setAvailable(availability);
    }
}
