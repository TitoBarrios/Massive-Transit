package com.titobarrios.view.company.coupons;

import java.time.LocalDateTime;

import com.titobarrios.constants.Value;
import com.titobarrios.controller.CouponCtrl;
import com.titobarrios.db.CurrentDate;
import com.titobarrios.model.Company;
import com.titobarrios.model.Coupon;
import com.titobarrios.model.Route;
import com.titobarrios.model.RouteSequence;
import com.titobarrios.model.Vehicle;
import com.titobarrios.view.Console;

public class EditCoupon {
    private CouponCtrl ctrl;
    private Company company;

    public EditCoupon(Company company) {
        ctrl = new CouponCtrl(company);
        this.company = company;
        menu();
    }

    private void menu() {
        Console.log("Menú de Edición");
        Coupon coupon = ctrl.selectCoupon();
        Console.log(
                "\nSeleccione una característica a editar\n1. Edición completa   |   2. Nombre   |   3. Descripción\n4. Tipo   |   5. Código de canje   |   6. Objetos Aplicables\n7. Descuento   |   8. Máximos canjes   |   9. Máximos canjes por usuario\n10. Fecha de inicio   |   11. Fecha de expiración   |   12. Días activo\nCupón a editar: ["
                        + coupon.getId() + "] " + coupon.getName());
        int option = Console.readNumber();
        boolean complete = false;
        switch (option) {
            case 1:
            case 2:
                Console.log("Escriba el nombre del cupón. Nombre actual: " + coupon.getName());
                coupon.setName(ctrl.selectString());
                Console.log("El nombre se ha editado correctamente");
                if (!complete)
                    new CMainMenu(company);
            case 3:
                Console.log("Digite la nueva descripción del cupón\nDescripción actual: ");
                coupon.setDescription(ctrl.selectString());
                Console.log("La descripción se ha editado correctamente");
                if (!complete)
                    new CMainMenu(company);
            case 4:
                Console.log("Tipo actual: " + coupon.getType());
                coupon.setType(ctrl.selectType());
                Console.log("El tipo se ha editado correctamente");
                if (!complete)
                    new CMainMenu(company);
            case 5:
                Console.log("Seleccione el código de canje. Código de canje actual: " + coupon.getRedeemWord());
                coupon.setRedeemWord(ctrl.selectString());
                Console.log("El código de canje se ha editado correctamente");
                if (!complete)
                    new CMainMenu(company);
            case 6:
                Console.log("Tipo de vehículos aplicables actual: " + coupon.getApplicable().name()
                        + "\nObjetos aplicables actuales:");
                switch (coupon.getApplicable()) {
                    case VEHICLES:
                        for (Vehicle vehicle : coupon.getVehicles())
                            Console.log(vehicle.getPlate());
                        break;
                    case ROUTE_SEQS:
                        for (RouteSequence routeSeq : coupon.getRouteSeqs())
                            Console.log(routeSeq.getId());
                        break;
                    case ROUTES:
                        for (Route route : coupon.getRoutes())
                            Console.log(route.getName());
                        break;
                }
                Coupon.AppliesTo applicable = ctrl.selectApplicable();
                switch (applicable) {
                    case VEHICLES:
                        Vehicle[] vehicles = ctrl.selectVehicles();
                        coupon.setApplicable(applicable);
                        coupon.setVehicles(vehicles);
                        break;
                    case ROUTE_SEQS:
                        RouteSequence[] routeSeqs = ctrl.selectRouteSeqs();
                        coupon.setApplicable(applicable);
                        coupon.setRouteSeqs(routeSeqs);
                        break;
                    case ROUTES:
                        Route[] routes = ctrl.selectRoutes();
                        coupon.setApplicable(applicable);
                        coupon.setRoutes(routes);
                        break;
                }
                Console.log("Los objetos aplicables se han editado correctamente");
                if (!complete)
                    new CMainMenu(company);
            case 7:
                Console.log(
                        "Tipo de descuento actual: " + coupon.getDiscountType() + ". Descuento: " + coupon.getDiscount()
                                + (coupon.getDiscountType().equals(Coupon.DiscountType.PERCENTAGE) ? "%" : ""));
                Coupon.DiscountType discountType = ctrl.selectDiscountType();
                int discount = ctrl.selectDiscount(discountType);
                coupon.setDiscountType(discountType);
                coupon.setDiscount(discount);
                Console.log("El descuento se ha editado correctamente");
                if (!complete)
                    new CMainMenu(company);
            case 8:
                Console.log("Digite el número máximo de canjes. Actual: "
                        + coupon.getRedeems()[Coupon.RedeemType.MAXIMUM.ordinal()]);
                coupon.setRedeems(new int[] { coupon.getRedeems()[Coupon.RedeemType.USER.ordinal()],
                        coupon.getRedeems()[Coupon.RedeemType.CURRENT.ordinal()],
                        coupon.getRedeems()[Coupon.RedeemType.USER_MAXIMUM.ordinal()], ctrl.selectNumber() });
                Console.log("La cantidad máxima de canjes se ha editado correctamente");
                if (!complete)
                    new CMainMenu(company);
            case 9:
                Console.log("Digite el número máximo de canjes por usuario. Actual: "
                        + coupon.getRedeems()[Coupon.RedeemType.USER_MAXIMUM.ordinal()]);
                coupon.setRedeems(
                        new int[] { coupon.getRedeems()[Coupon.RedeemType.USER.ordinal()],
                                coupon.getRedeems()[Coupon.RedeemType.CURRENT.ordinal()], ctrl.selectNumber(),
                                coupon.getRedeems()[Coupon.RedeemType.MAXIMUM.ordinal()] });
                Console.log("La cantidad máxima de canjes por usuario se ha editado correctamente");
                if (!complete)
                    new CMainMenu(company);
            case 10:
                if (CurrentDate.get().isAfter(coupon.getDates()[Value.STARTING.value()])) {
                    Console.log("No es posible editar, la fecha inicial ya ha pasado");
                    break;
                }
                Console.log("Digite la fecha de inicio del cupón. Formato: yyyy-MM-dd'T'HH:mm:ss.\nActual: "
                        + coupon.getDates()[Value.STARTING.value()]);
                coupon.setDates(new LocalDateTime[] { ctrl.selectDate(), coupon.getDates()[Value.EXPIRATION.value()] });
                Console.log("La fecha de inicio se ha editado correctamente");
                if (!complete)
                    new CMainMenu(company);
            case 11:
                Console.log("Digite la fecha de expiración del cupón. Formato: yyyy-MM-dd'T'HH:mm:ss.\nActual: "
                        + coupon.getDates()[Value.EXPIRATION.value()]);
                LocalDateTime expiration = ctrl.selectDate();
                if (expiration.isBefore(coupon.getDates()[Value.STARTING.value()])) {
                    Console.log("La fecha de expiración no puede ir antes que la de inicio");
                    break;
                }
                coupon.setDates(new LocalDateTime[] { coupon.getDates()[Value.STARTING.value()], expiration });
                Console.log("La fecha de expiración se ha editado correctamente");
                if (!complete)
                    new CMainMenu(company);
            case 12:
                Console.log("Días activos actuales: " + coupon.getRedeemingDays());
                coupon.setRedeemingDays(ctrl.selectActiveDays());
                Console.log("Los días activos se han editado correctamente");
                if (complete)
                    Console.log("El cupón se ha editado correctamente");
                new CMainMenu(company);
            case 0:
                new CMainMenu(company);
            default:
                menu();
        }
        new CMainMenu(company);
    }
}
