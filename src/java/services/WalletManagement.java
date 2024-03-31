package services;

import error.NotAffordableException;
import model.User;

public class WalletManagement {

    public void addFunds(User user, int funds) {
        user.setWallet(user.getWallet() + funds);
    }

    public void charge(User user, int price) throws NotAffordableException {
        if (isAffordable(user, price)) {
            user.setWallet(user.getWallet() - price);
        } else {
            throw new NotAffordableException(NotAffordableException.defaultMessage(user, price));
        }
    }

    public boolean isAffordable(User user, int price) {
        return user.getWallet() >= price;
    }

}
