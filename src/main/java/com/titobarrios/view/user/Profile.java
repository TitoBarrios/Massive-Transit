package com.titobarrios.view.user;

import org.springframework.security.crypto.bcrypt.BCrypt;

import com.titobarrios.model.User;
import com.titobarrios.services.AccountsServ;
import com.titobarrios.view.Console;

public class Profile {
    private User user;

    public Profile(User user) {
        this.user = user;
        menu();
    }

    private void menu() {
        if (user.getId().equals("admin")) {
            Console.log("El administrador no puede cambiar sus credenciales");
            new MainMenu(user);
        }
        Console.log("Id: " + user.getId() + "\n1. Editar ID\n2. Cambiar contraseña\n0. Volver");
        int option = Console.readNumber();
        if (option == 0)
            new MainMenu(user);

        switch (option) {
            case 1:
                newId();
            case 2:
                newPassword();
            default:
                menu();
        }
    }

    private void newId() {
        Console.log("Esta opción aún no está disponible");
        menu();

        Console.log("Escriba su nuevo id\n0. Volver");
        String id = Console.readData();
        if (id.equals("0"))
            new MainMenu(user);
        if (!AccountsServ.isIdAvailable(id)) {
            Console.log("El id escrito no está disponible, inténtelo de nuevo");
            newId();
        }
        user.setId(id);
        Console.log("Su nuevo id es: " + user.getId());
        menu();
    }

    private void newPassword() {
        Console.log("Digite su contraseña antigua\n0. Volver");
        String password = Console.readData();
        if (password.equals("0"))
            new MainMenu(user);

        if (!BCrypt.checkpw(password, user.getPassword())) {
            Console.log("Contraseña incorrecta, inténtelo de nuevo");
            newPassword();
        }
        Console.log("Escriba su nueva contraseña");
        String newPassword = Console.readData();
        if (newPassword.equals("0"))
            menu();
        Console.log("Escriba una vez más su nueva contraseña");
        String passwordConfirmation = Console.readData();
        if (!newPassword.equals(passwordConfirmation)) {
            Console.log("Las contraseñas no coinciden, inténtelo de nuevo");
            newPassword();
        }
        user.setPassword(newPassword);
        Console.log("La contraseña se ha cambiado correctamente");
        menu();
    }
}
