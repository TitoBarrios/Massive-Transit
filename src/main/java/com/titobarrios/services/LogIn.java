package com.titobarrios.services;

import com.titobarrios.error.InvalidCredentialsException;
import com.titobarrios.error.RepeatedUserException;
import com.titobarrios.error.ElementNotFoundException;
import com.titobarrios.model.User;

public class LogIn {
    User[] users;

    public LogIn(User[] users) {
        this.users = users;
    }

    public void logIn(User user, String name, String password) throws ElementNotFoundException, InvalidCredentialsException {
        
    }

    public void register(User.Type type, String name, String password) throws RepeatedUserException {

    }

}
