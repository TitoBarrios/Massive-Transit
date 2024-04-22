package com.titobarrios.view.user;

import com.titobarrios.model.User;
import com.titobarrios.view.Console;
import com.titobarrios.view.home.Home;

public class Register {
    public Register() {
        menu();
    }

    public void menu() {
        Console.log("Ingrese su nuevo usuario\n0. En cualquier momento para cancelar");
        String id = Console.readData();
        if(id.equals("0")) new Home();
        Console.log("Ingrese su contraseña");
        String password = Console.readData();
        if(password.equals("0")) new Home();
        Console.log("Repita su contraseña");
        String passwordConfirmation = Console.readData();
        if(!password.equals(passwordConfirmation)) {
            Console.log("Las contraseñas no coinciden");   
        } else {
            new User(id, password);
            Console.log("Su nueva cuenta se ha creado correctamente");
        }
        new Home();
    }
}
