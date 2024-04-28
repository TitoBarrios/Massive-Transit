package com.titobarrios.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;

import com.titobarrios.exception.ElementNotFoundException;
import com.titobarrios.exception.InvalidCredentialsException;
import com.titobarrios.model.Account;
import com.titobarrios.services.AccountsServ;

public class LogIn {

    public LogIn() {
    }

    public static boolean logIn(String id, String password) throws ElementNotFoundException, InvalidCredentialsException {
        Account account = AccountsServ.searchAccount(id);
        if (account == null)
            throw new ElementNotFoundException("The account: " + id + " was not found in the database");
        if (BCrypt.checkpw(password, account.getPassword()))
            throw new InvalidCredentialsException("The password is wrong");
        return true;
    }

}
