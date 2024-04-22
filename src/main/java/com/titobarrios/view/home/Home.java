package com.titobarrios.view.home;

import com.titobarrios.view.Console;
import com.titobarrios.view.user.LogIn;
import com.titobarrios.view.user.Register;

public class Home {

    public Home() {
        mainMenu();
    }

    public void mainMenu() {
        Console.log(
                "Bienvenido a mi sistema de transporte masivo\n\nSeleccione la opción que más le convenga: \n1. Iniciar sesión\n2. Ver rutas\n3. Registrarse\n4. Soy una empresa\n0. Salir");
        int option = Console.readOption(false, 5);
        if (option <= 0 || option > 5) {
            if (option == 0)
                new Exit();
            mainMenu();
        }

        switch (option) {
            case 1:
                new LogIn();
                break;
            case 2:
                new Routes();
                break;
            case 3:
                new Register();
                break;
            case 4:
                new Company();
                break;
            case 5:
                new Admin();
                break;
        }
    }
}
