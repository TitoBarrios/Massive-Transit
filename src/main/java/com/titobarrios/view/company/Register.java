package com.titobarrios.view.company;

import com.titobarrios.controller.AccountsCtrl;
import com.titobarrios.model.Company;
import com.titobarrios.view.Console;
import com.titobarrios.view.home.CompanyMenu;

public class Register {

    public Register() {
        menu();
    }

    private void menu() {
        Console.log("Ingrese un nuevo id de empresa\n0. En cualquier momento para cancelar");
        String id = Console.readData();
        if (id.equals("0"))
            new CompanyMenu();
        if (!AccountsCtrl.isIdAvailable(id)) {
            Console.log("El id escrito ya está en uso, por favor, elija otro distinto");
            menu();
        }
        String password = null;
        boolean sContinue = true;
        do {
            Console.log("Ingrese su contraseña");
            password = Console.readData();
            if (password.equals("0"))
                new CompanyMenu();
            Console.log("Repita su contraseña");
            String passwordConfirmation = Console.readData();
            if (!password.equals(passwordConfirmation)) {
                Console.log("Las contraseñas no coinciden, por favor, inténtelo de nuevo");
                sContinue = false;
            }
        } while (!sContinue);
        new Company(id, password);
        Console.log("Su nueva cuenta se ha creado correctamente");
        new CompanyMenu();
    }
}
