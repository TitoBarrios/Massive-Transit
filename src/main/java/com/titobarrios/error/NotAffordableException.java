package com.titobarrios.error;

import com.titobarrios.model.User;

public class NotAffordableException extends Exception {

    public NotAffordableException(String message) {
        super(message);
    }

    public static String defaultMessage(User user, int price) {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("El usuario ").append(user.getName()).append(" no tiene suficientes fondos para pagar ")
                .append(price).append("\nUser Wallet: ").append(user.getWallet()).append(".   Price: ").append(price);
        return strBuilder.toString();
    }
}
