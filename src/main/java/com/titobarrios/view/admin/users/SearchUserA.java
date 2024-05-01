package com.titobarrios.view.admin.users;

import com.titobarrios.controller.AdminCtrl;
import com.titobarrios.db.DB;
import com.titobarrios.model.Admin;
import com.titobarrios.model.Subscription;
import com.titobarrios.model.Ticket;
import com.titobarrios.model.User;
import com.titobarrios.view.Console;

public class SearchUserA {
    private AdminCtrl ctrl;
    private Admin admin;

    public SearchUserA(Admin admin) {
        this.admin = admin;
        ctrl = new AdminCtrl(admin);
        menu();
    }

    private void menu() {
        Console.log("Menú de Búsqueda de Usuario");
        User user = ctrl.selectUser(DB.getUsers());
        userOpts(user);
        new UsersAMenu(admin);
    }

    private void userOpts(User user) {
        Console.log(ctrl.userAdminInfo(user) + "\nQué desea hacer con el usuario " + user.getId()
                + "?\n1. Ver tickets  |   2. Ver relaciones   |   3. Ver suscripciones   |   4. Eliminar");
        int option = Console.readNumber();
        switch (option) {
            case 1:
                showTickets(user);
                userOpts(user);
            case 2:
            showRelationships(user);
            userOpts(user);
            case 3:
            showSubscriptions(user);
            userOpts(user);
            case 4:
            delete(user);
                break;
            case 0:
                break;
            default:
                menu();
        }
    }

    private void showTickets(User user) {
        Console.log("Los tickets del usuario son los siguientes:");
        for (Ticket ticket : user.getTickets())
            Console.log(ticket.bill() + "\n");
    }

    private void showRelationships(User user) {
        Console.log("Las relaciones del usuario son las siguientes:");
        for(User relationship : user.getRelationships())
            Console.log(relationship.getId());
    }

    private void showSubscriptions(User user) {
        Console.log("Las suscripciones del usuario son las siguientes:");
        for(Subscription subscription : user.getSubscriptions())
            Console.log(subscription.info() + "\n");
    }
    
    private void delete(User user) {
        Console.log("Está seguro que desea eliminar este usuario?\n1. Sí   2. No");
        int option = Console.readNumber();
        if (option == 1) {
            user.delete();
            Console.log("Se ha eliminado el usuario correctamente");
        } else {
            userOpts(user);
        }
    }
}
