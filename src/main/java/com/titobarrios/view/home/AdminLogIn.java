package com.titobarrios.view.home;

import com.titobarrios.exception.InvalidCredentialsException;
import com.titobarrios.model.Admin;
import com.titobarrios.utils.LogIn;
import com.titobarrios.view.Console;
import com.titobarrios.view.admin.AdminMainMenu;

public class AdminLogIn {
    public AdminLogIn() {
        menu();
    }

    private void menu() {
        Console.log("Menú de Administrador\nDigite el usuario:");
        String id = Console.readData();
        Console.log("Digite la contraseña");
        String password = Console.readData();
        try{
            Admin admin = LogIn.admin(id, password);
            new AdminMainMenu(admin);
        } catch(InvalidCredentialsException e){
            Console.log("Credenciales inválidas");
            new Home();
        }
    }
}
