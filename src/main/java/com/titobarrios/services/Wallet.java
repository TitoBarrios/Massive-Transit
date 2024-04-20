package com.titobarrios.services;

import com.titobarrios.error.NotAffordableException;
import com.titobarrios.model.User;

public class Wallet {

    public static void addFunds(User user, int funds) {
        user.setWallet(user.getWallet() + funds);
    }

    public static void charge(User user, int price) throws NotAffordableException {
        if (isAffordable(user, price)) {
            user.setWallet(user.getWallet() - price);
        } else {
            throw new NotAffordableException(NotAffordableException.defaultMessage(user, price));
        }
    }

    public static boolean isAffordable(User user, int price) {
        return user.getWallet() >= price;
    }

}
