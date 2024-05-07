package com.titobarrios;

import com.myproperties.PropCtrl;
import com.titobarrios.db.CurrentDate;
import com.titobarrios.db.DB;
import com.titobarrios.model.Admin;
import com.titobarrios.model.Company;
import com.titobarrios.model.User;
import com.titobarrios.view.Console;
import com.titobarrios.view.home.Home;

public class App {
    public void defaults() {
        PropCtrl propCtrl = new PropCtrl("src/main/resources/default.properties");
        new User(propCtrl.getValue("userId"), propCtrl.getValue("userPassword"));
        new Company(propCtrl.getValue("companyId"), propCtrl.getValue("companyPassword"));

    }

    public void admin() {
        PropCtrl propCtrl = new PropCtrl("src/main/resources/config.properties");
        new Admin(propCtrl.getValue("adminId"), propCtrl.getValue("adminPassword"));
    }
    
    public static void main(String[] args) {
        DB.initialize();
        CurrentDate.initialize();
        Console.initialize();

        App app = new App();
        app.admin();
        app.defaults();

        new Home();
    }
}
