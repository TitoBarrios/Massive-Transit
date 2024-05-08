package com.titobarrios.view.company.coupons;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import com.titobarrios.controller.CouponCtrl;
import com.titobarrios.model.Company;
import com.titobarrios.model.Coupon;
import com.titobarrios.model.Route;
import com.titobarrios.model.RouteSequence;
import com.titobarrios.model.Vehicle;
import com.titobarrios.view.Console;

public class CreateCoupon {
    private CouponCtrl ctrl;
    private Company company;

    public CreateCoupon(Company company) {
        this.ctrl = new CouponCtrl(company);
        this.company = company;
        menu();
    }

    private void menu() {
        Console.log("Menú de creación");
        Console.log("Escriba el nombre del cupón");
        String name = ctrl.selectString();
        Console.log("Escriba la descripción del cupón");
        String description = ctrl.selectString();
        Coupon.Type type = ctrl.selectType();
        String redeemWord = null;
        if (type.equals(Coupon.Type.RESERVED))
            redeemWord = ctrl.selectRedeemWord();
        Coupon.AppliesTo applicable = ctrl.selectApplicable();
        Vehicle[] vehicles = null;
        RouteSequence[] routeSeqs = null;
        Route[] routes = null;
        switch (applicable) {
            case VEHICLES:
                vehicles = ctrl.selectVehicles();
                break;
            case ROUTE_SEQS:
                routeSeqs = ctrl.selectRouteSeqs();
                break;
            case ROUTES:
                routes = ctrl.selectRoutes();
                break;
        }
        Coupon.DiscountType discountType = ctrl.selectDiscountType();
        int discount = ctrl.selectDiscount(discountType);
        Console.log("Digite el número máximo de canjes");
        int maxRedemptions = ctrl.selectNumber();
        Console.log("Digite el número máximo de canjes por usuario");
        int maxUserRedemptions = ctrl.selectNumber();
        LocalDateTime startingDate = null;
        LocalDateTime expirationDate = null;
        do {
            Console.log("Digite la fecha de inicio del cupón. Formato: yyyy-MM-dd'T'HH:mm:ss");
            startingDate = ctrl.selectDate();
            Console.log("Digite la fecha de expiración del cupón. Formato: yyyy-MM-dd'T'HH:mm:ss");
            expirationDate = ctrl.selectDate();
            if (expirationDate.isBefore(startingDate)) {
                Console.log("La fecha de expiración no puede ir antes de la de inicio");
                continue;
            }
        } while (false);
        DayOfWeek[] activeDays = ctrl.selectActiveDays();
        new Coupon(type, company, name, description, redeemWord, discountType, discount,
                new LocalDateTime[] { startingDate, expirationDate }, false, applicable, vehicles, routeSeqs, routes,
                activeDays, maxUserRedemptions, maxRedemptions);
        Console.log("El cupón se ha creado correctamente\n");
        new CMainMenu(company);
    }

}
