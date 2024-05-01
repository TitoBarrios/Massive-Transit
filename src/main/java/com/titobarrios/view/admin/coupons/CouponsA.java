package com.titobarrios.view.admin.coupons;

import com.titobarrios.controller.AdminCtrl;
import com.titobarrios.db.DB;
import com.titobarrios.model.Admin;
import com.titobarrios.model.Coupon;
import com.titobarrios.view.Console;

public class CouponsA {
    private AdminCtrl ctrl;
    private Admin admin;

    public CouponsA(Admin admin) {
        this.admin = admin;
        ctrl = new AdminCtrl(admin);
        menu();
    }

    private void menu() {
        Console.log("Estos son los cupones inscritos: ");
        for (Coupon coupon : DB.getCoupons())
            Console.log(ctrl.couponAdminInfo(coupon) + "\n");
        Console.log("Digita cualquier tecla para volver");
        Console.readData();
        new CouponsAMenu(admin);
    }
}
