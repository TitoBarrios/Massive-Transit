package com.titobarrios;

import com.titobarrios.db.DB;
import com.titobarrios.model.User;
import com.titobarrios.view.Console;
import com.titobarrios.view.home.Home;

public class App {
    public void defaults() {
        DB.store(new User("admin", "123"));
    }
    public static void main(String[] args) {
        DB.initialize();
        Console.initialize();

        App app = new App();
        app.defaults();

        new Home();
    }
}
