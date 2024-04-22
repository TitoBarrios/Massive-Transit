package com.titobarrios.view.user;

import com.titobarrios.view.Console;
import com.titobarrios.view.home.Home;

public class LogIn {
    public LogIn() {
        menu();
    }

    public void menu() {
        Console.log("Ingrese su usuario\n0. Volver");
        String id = Console.readData();
        if(id.equals(0)) new Home();
        Console.log("Ingrese su contraseña");
        String password = Console.readData();
        // Comprobación de contraseña
        if(true) {
            new MainMenu(user);
        }
    }
}
