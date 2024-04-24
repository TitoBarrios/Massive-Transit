package com.titobarrios.view.user;

import com.titobarrios.model.User;
import com.titobarrios.services.Wallet;
import com.titobarrios.view.Console;

public class WalletMenu {
    private User user;

    public WalletMenu(User user) {
        this.user = user;
        menu();
    }

    public void menu() {
        Console.log("Su saldo actual es: " + user.getWallet()
                + " pesos\nDigite 1 para agregar fondos, 0 para volver");
        int option = Console.readNumber();
        if (option < 0 || option > 1)
            menu();
        if (option == 0)
            new MainMenu(user);
        addFunds();
    }

    public void addFunds() {
        Console.log("Cuánto dinero desea agregar a su billetera?\n0. Cancelar");
        int money = Console.readNumber();
        if (money < 0) {
            Console.log("El valor no puede ser negativo");
            menu();
        }
        Wallet.addFunds(user, money);
        if (money == 0)
            Console.log("Se ha cancelado la operación");
        Console.log("Su nuevo saldo es: " + user.getWallet());
        new MainMenu(user);
    }
}
