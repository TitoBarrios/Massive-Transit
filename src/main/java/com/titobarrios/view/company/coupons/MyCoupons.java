package com.titobarrios.view.company.coupons;

import com.titobarrios.model.Company;
import com.titobarrios.model.Coupon;
import com.titobarrios.view.Console;

public class MyCoupons {
    private Company company;

    public MyCoupons(Company company) {
        this.company = company;
        menu();
    }

    private void menu() {
        Coupon[] coupons = company.getCoupons();
        Console.log("Estos son sus cupones registrados");
        for (Coupon coupon : coupons)
            Console.log(coupon.info());
        Console.log("Digite cualquier tecla para volver");
        Console.readData();
        new CMainMenu(company);
    }
}
