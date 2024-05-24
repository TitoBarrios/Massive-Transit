package com.titobarrios.model;

import com.titobarrios.db.DB;

public class Admin extends Account {
    public Admin(String id, String password) {
        super(id, password);
        DB.remove(this);
        DB.setAdmin(this);
    }
}
