package com.titobarrios.exception;

import com.titobarrios.model.User;

public class NotAffordableException extends Exception {

    public NotAffordableException(String message) {
        super(message);
    }

    public static String defaultMessage(User customer, int price) {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("El usuario ").append(customer.getId()).append(" no tiene suficientes fondos para pagar ")
                .append(price).append("\nUser Wallet: ").append(customer.getWallet()).append(".   Price: ").append(price);
        return strBuilder.toString();
    }
}
