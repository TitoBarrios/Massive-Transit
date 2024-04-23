package com.titobarrios.services;

import com.titobarrios.error.InvalidCredentialsException;

import org.springframework.security.crypto.bcrypt.BCrypt;

import com.titobarrios.controller.AccountsCtrl;
import com.titobarrios.error.ElementNotFoundException;
import com.titobarrios.model.Account;

public class LogIn {

    public LogIn() {
    }

    public static boolean logIn(String id, String password) throws ElementNotFoundException, InvalidCredentialsException {
        Account account = AccountsCtrl.searchAccount(id);
        if (account == null)
            throw new ElementNotFoundException("The account: " + id + " was not found in the database");
        if (BCrypt.checkpw(password, account.getPassword()))
            throw new InvalidCredentialsException("The password is wrong");
        return true;
    }

}
