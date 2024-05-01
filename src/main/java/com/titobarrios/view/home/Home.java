package com.titobarrios.view.home;

import com.titobarrios.view.Console;
import com.titobarrios.view.user.LogInMenu;
import com.titobarrios.view.user.Register;

public class Home {

    public Home() {
        mainMenu();
    }

    private void mainMenu() {
        Console.log(
                "Bienvenido a mi sistema de transporte masivo\n\nSeleccione la opción que más le convenga: \n1. Iniciar sesión\n2. Ver rutas\n3. Registrarse\n4. Soy una empresa\n0. Salir");
        int option = Console.readNumber();

        switch (option) {
            case 1:
                new LogInMenu();
            case 2:
                new Routes();
            case 3:
                new Register();
            case 4:
                new CompanyMenu();
            case 5:
                new AdminLogIn();
            case 0:
                new Exit();
            default:
                mainMenu();
        }
    }
}
