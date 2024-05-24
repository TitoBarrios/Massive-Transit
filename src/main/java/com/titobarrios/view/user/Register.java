package com.titobarrios.view.user;

import com.titobarrios.model.User;
import com.titobarrios.services.AccountsServ;
import com.titobarrios.view.Console;
import com.titobarrios.view.home.Home;

public class Register {
    public Register() {
        menu();
    }

    private void menu() {
        Console.log("Ingrese su nuevo usuario\n0. En cualquier momento para cancelar");
        String id = Console.readData();
        if (id.equals("0"))
            new Home();
        if (!AccountsServ.isIdAvailable(id)) {
            Console.log("El id escrito ya está en uso, por favor, elija otro distinto");
            menu();
        }
        String password = null;
        boolean sContinue = true;
        do {
            Console.log("Ingrese su contraseña");
            password = Console.readData();
            if (password.equals("0"))
                new Home();
            Console.log("Repita su contraseña");
            String passwordConfirmation = Console.readData();
            if (!password.equals(passwordConfirmation)) {
                Console.log("Las contraseñas no coinciden, por favor, inténtelo de nuevo");
                sContinue = false;
            } else {
                sContinue = true;
            }
        } while (!sContinue);
        new User(id, password);
        Console.log("Su nueva cuenta se ha creado correctamente");

        new Home();
    }
}
