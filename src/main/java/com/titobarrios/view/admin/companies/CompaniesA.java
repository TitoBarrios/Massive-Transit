package com.titobarrios.view.admin.companies;

import com.titobarrios.controller.AdminCtrl;
import com.titobarrios.db.DB;
import com.titobarrios.model.Admin;
import com.titobarrios.model.Company;
import com.titobarrios.view.Console;

public class CompaniesA {
    private AdminCtrl ctrl;
    private Admin admin;

    public CompaniesA(Admin admin) {
        this.admin = admin;
        ctrl = new AdminCtrl(admin);
        menu();
    }

    private void menu() {
        Console.log("Las siguientes son las empresas inscritas");
        for(Company company : DB.getCompanies())
            Console.log(ctrl.companyAdminInfo(company));
        Console.log("Digite cualquier tecla para volver");
        new CompaniesAMenu(admin);
    }
}
