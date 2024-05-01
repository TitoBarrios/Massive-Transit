package com.titobarrios.view.admin.coupons;

import com.titobarrios.controller.AdminCtrl;
import com.titobarrios.db.DB;
import com.titobarrios.model.Admin;
import com.titobarrios.model.Coupon;
import com.titobarrios.view.Console;

public class SearchCouponA {
    private AdminCtrl ctrl;
    private Admin admin;

    public SearchCouponA(Admin admin) {
        this.admin = admin;
        ctrl = new AdminCtrl(admin);
        menu();
    }

    private void menu() {
        Console.log("Menú de búsqueda de cupón");
        Coupon coupon = ctrl.selectCoupon(DB.getCoupons());
        couponOpts(coupon);
        new CouponsAMenu(admin);
    }

    private void couponOpts(Coupon coupon) {
        Console.log("Qué desea hacer con el cupón?\n1. Ver información detallada   |   2. Eliminar");
        int option = Console.readNumber();
        switch (option) {
            case 1:
                Console.log(coupon.info());
                couponOpts(coupon);
            case 2:
                delete(coupon);
            case 0:
                new CouponsAMenu(admin);
            default:
                couponOpts(coupon);
        }
    }

    private void delete(Coupon coupon) {
        Console.log("Está seguro que desea eliminar el cupón " + coupon.getId() + "\n1. Sí   2. No");
        int option = Console.readNumber();
        if (option == 1) {
            coupon.delete();
            Console.log("El cupón se ha eliminado correctamente");
            new CouponsAMenu(admin);
        } else {
            Console.log("Se ha cancelado la operación");
            couponOpts(coupon);
        }
    }
}
