package com.titobarrios.view.admin.companies;

import com.titobarrios.controller.AdminCtrl;
import com.titobarrios.db.DB;
import com.titobarrios.model.Admin;
import com.titobarrios.model.Company;
import com.titobarrios.view.Console;

public class SearchCompanyA {
    private AdminCtrl ctrl;
    private Admin admin;

    public SearchCompanyA(Admin admin) {
        this.admin = admin;
        ctrl = new AdminCtrl(admin);
        menu();
    }

    private void menu() {
        Console.log("Menú de Búsqueda");
        Company company = ctrl.selectCompany(DB.getCompanies());
        companyOpts(company);
    }

    private void companyOpts(Company company) {
        Console.log("Qué desea hacer con la empresa " + company.getId()
                + "?\n1. Ver información detallada   |   2. Eliminar");
        int option = Console.readNumber();
        switch (option) {
            case 1:
            Console.log(company.info());;
            case 2:
            delete(company);
            case 0:
                new CompaniesAMenu(admin);
            default:
                companyOpts(company);
        }
    }

    private void delete(Company company) {
        Console.log("Está seguro que desea eliminar esta empresa?\n1. Sí   2. No");
        int option = Console.readNumber();
        if (option == 1) {
            company.delete();
            Console.log("La empresa se ha eliminado correctamente");
            new CompaniesAMenu(admin);
        } else {
            Console.log("Se ha cancelado la operación");
            companyOpts(company);
        }
    }
}
