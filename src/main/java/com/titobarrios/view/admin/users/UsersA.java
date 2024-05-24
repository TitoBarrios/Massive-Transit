package com.titobarrios.view.admin.users;

import com.titobarrios.controller.AdminCtrl;
import com.titobarrios.db.DB;
import com.titobarrios.model.Admin;
import com.titobarrios.model.User;
import com.titobarrios.view.Console;

public class UsersA {
    private AdminCtrl ctrl;
    private Admin admin;

    public UsersA (Admin admin) {
        this.admin = admin;
        ctrl = new AdminCtrl(admin);
        menu();
    }

    private void menu() {
        Console.log("Los usuarios inscritos son los siguientes:");
        for(User user : DB.getUsers())
            Console.log(ctrl.userAdminInfo(user));
        Console.log("Digita cualquier tecla para volver");
        Console.readData();
        new UsersAMenu(admin);
    }
}
