package com.titobarrios.view.user;

import com.titobarrios.model.User;
import com.titobarrios.view.Console;

public class History {
    private User user;

    public History(User user) {
        this.user = user;
        menu();
    }

    public void menu() {
        Console.log("Su historial de compras es el siguiente:");
        for (int i = 0; i < user.getTickets().length; i++) {
            user.getTickets()[i].getVehicle().getRouteSeq().refresh();
            Console.log(user.getTickets()[i].bill());
        }
        Console.log("Digite cualquier tecla para volver");
        Console.readData();
        new MainMenu(user);
    }
}