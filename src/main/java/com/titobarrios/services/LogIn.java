package com.titobarrios.services;

import com.titobarrios.error.InvalidCredentialsException;
import com.titobarrios.error.ElementNotFoundException;
import com.titobarrios.model.Account;

public class LogIn {
    Account[] users;

    public LogIn(Account[] users) {
        this.users = users;
    }

    public void logIn(Account user, String name, String password) throws ElementNotFoundException, InvalidCredentialsException {
        
    }

}
