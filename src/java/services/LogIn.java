package services;

import error.InvalidCredentialsException;
import error.RepeatedUserException;
import error.UserNotFoundException;
import model.User;

public class LogIn {
    User[] users;

    public LogIn(User[] users) {
        this.users = users;
    }

    public void logIn(User user, String name, String password) throws UserNotFoundException, InvalidCredentialsException {
        
    }

    public void register(User.Type type, String name, String password) throws RepeatedUserException {

    }

}
