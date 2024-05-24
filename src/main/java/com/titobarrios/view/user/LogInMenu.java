package com.titobarrios.view.user;

import com.titobarrios.exception.ElementNotFoundException;
import com.titobarrios.exception.InvalidCredentialsException;
import com.titobarrios.model.Account;
import com.titobarrios.model.Company;
import com.titobarrios.model.User;
import com.titobarrios.services.AccountsServ;
import com.titobarrios.utils.LogIn;
import com.titobarrios.view.Console;
import com.titobarrios.view.home.Home;

public class LogInMenu {
    public LogInMenu() {
        menu();
    }

    private void menu() {
        Console.log("Ingrese su usuario\n0. Volver");
        String id = Console.readData();
        if (id.equals("0"))
            new Home();
        Console.log("Ingrese su contraseña");
        String password = Console.readData();

        boolean logIn = false;
        try {
            logIn = LogIn.logIn(id, password);
        } catch (ElementNotFoundException e) {
            Console.log("El usuario no existe");
            menu();
        } catch (InvalidCredentialsException e) {
            Console.log("Contraseña incorrecta");
            menu();
        }

        if (logIn) {
            Account account = AccountsServ.searchAccount(id);
            if (account instanceof Company) {
                Console.log("Opción no disponible para empresas");
                new Home();
            }
            new MainMenu((User) account);
        }
        new Home();
    }
}
