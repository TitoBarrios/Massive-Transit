package com.titobarrios.view.user;

import com.titobarrios.controller.AccountsCtrl;
import com.titobarrios.error.ElementNotFoundException;
import com.titobarrios.model.Account;
import com.titobarrios.model.Company;
import com.titobarrios.model.User;
import com.titobarrios.utils.ArraysUtil;
import com.titobarrios.view.Console;

public class Relationships {
    private User user;

    public Relationships(User user) {
        this.user = user;
        menu();
    }

    public void menu() {
        Console.log("Familiares y Amigos\n1. Ver usuarios\n2. Agregar nuevo usuario\n0. Salir");
        int option = Console.readNumber();
        switch (option) {
            case 1:
                showUsers();
            case 2:
                add();
            case 0:
                new MainMenu(user);
            default:
                menu();
        }
    }

    public void showUsers() {
        Console.log("Seleccione un usuario para ver más opciones");
        for (int i = 0; i < user.getRelationships().length; i++) {
            Console.log((i + 1) + ". Nombre: " + user.getRelationships()[i].getId());
        }
        Console.log("0. Salir");

        int option = Console.readNumber();
        if (option <= 0 || option > user.getRelationships().length)
            menu();
        User relationship = user.getRelationships()[option];

        Console.log(relationship.getId() + "\n1. Comprar ticket    2. Eliminar de mi lista\n0. Reelegir");
        switch (option) {
            case 1:
                buyTicket(relationship);
            case 2:
                delete(relationship);
            case 0:
                showUsers();
            default:
                menu();
        }
    }

    public void buyTicket(User relationship) {
        menu();
    }

    public void delete(User relationship) {
        Console.log("Está seguro que desea eliminar el usuario " + relationship.getId()
                + " de su lista de familiares y amigos?\n1. Sí   2. No");
        int option = Console.readNumber();
        if(option != 1) menu();

        try{
            user.deleteRelationship(ArraysUtil.searchElementSlot(user.getRelationships(), relationship));
            Console.log("Se ha eliminado el usuario de su lista correctamente");
        } catch(ElementNotFoundException e) {
            Console.log(e.getMessage());
        }
        menu();
    }   

    public void add() {
        Console.log("Escriba el id del usuario a agregar");
        String id = Console.readData();
        Account relationship = AccountsCtrl.searchAccount(id);
        if (relationship == null) {
            Console.log("Usuario no encontrado");
            menu();
        }
        if (relationship instanceof Company) {
            Console.log("No se puede agregar una empresa");
            menu();
        }
        Console.log(
                "Está seguro que desea agregar el usuario " + id + " a su lista de amigos y familiares?\n1. Sí  2.No");
        int option = Console.readNumber();
        if (option != 2) {
            Console.log("Se canceló la operación");
            menu();
        }
        user.add((User) relationship);
        Console.log("Se ha agregado al usuario " + id + " correctamente");
        menu();
    }
}
