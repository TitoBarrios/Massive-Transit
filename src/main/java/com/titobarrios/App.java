package com.titobarrios;

import com.myproperties.PropCtrl;
import com.titobarrios.db.CurrentDate;
import com.titobarrios.db.DB;
import com.titobarrios.model.Admin;
import com.titobarrios.model.Company;
import com.titobarrios.model.User;
import com.titobarrios.services.JsonServices.JsonService;
import com.titobarrios.view.Console;
import com.titobarrios.view.home.Home;

public class App {

    public void defaults() {
        PropCtrl propCtrl = new PropCtrl("src/main/resources/default.properties");
        new User(propCtrl.getValue("userId"), propCtrl.getValue("userPassword"));
        new Company(propCtrl.getValue("companyId"), propCtrl.getValue("companyPassword"));
    }

    public void admin() {
        new Admin(DB.getPropCtrl().getValue("adminId"), DB.getPropCtrl().getValue("adminPassword"));
    }
    
    public static void main(String[] args) {
        DB.initialize();
        CurrentDate.initialize();
        Console.initialize();
        JsonService.initialize();

        App app = new App();
        app.admin();
        app.defaults();

        new Home();
    }
}
