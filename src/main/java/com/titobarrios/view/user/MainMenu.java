package com.titobarrios.view.user;

import com.titobarrios.model.User;
import com.titobarrios.view.Console;
import com.titobarrios.view.home.Home;
import com.titobarrios.view.user.subscription.SMainMenu;

public class MainMenu {
    private User user;

    public MainMenu(User user) {
        this.user = user;
        menu();
    }

    private void menu() {
        Console.log(
                "¿Qué desea hacer?\n1. Comprar ticket\n2. Suscripciones\n3. Mi billetera\n4. Ver historial y estado de mis tickets\n5. Familia y Amigos\n6. Perfil\n0. Cerrar sesión");
        int option = Console.readNumber();
        switch (option) {
            case 1:
                new TicketMenu(user);
            case 2:
                new SMainMenu(user);
            case 3:
                new WalletMenu(user);
            case 4:
                new History(user);
            case 5:
                new Relationships(user);
            case 6:
                new Profile(user);
            case 0:
                new Home();
            default:
                menu();
        }
    }
}
