package com.titobarrios.controller;

import com.titobarrios.constants.VType;
import com.titobarrios.db.DB;
import com.titobarrios.model.Coupon;
import com.titobarrios.model.Route;
import com.titobarrios.model.RouteSequence;
import com.titobarrios.model.User;
import com.titobarrios.model.Vehicle;
import com.titobarrios.services.CouponServ;
import com.titobarrios.services.RouteSeqServ;
import com.titobarrios.utils.Converter;
import com.titobarrios.view.Console;
import com.titobarrios.view.user.MainMenu;

public class TicketCtrl {
    private User user;

    public TicketCtrl(User user) {
        this.user = user;
    }

    public VType selectType() {
        Console.log("Seleccione el tipo de vehículo" + VType.menu() + "\n0. Volver");
        int option = Console.readNumber();
        if (option == 0)
            new MainMenu(user);
        if (option < 0 || option > 4)
            return selectType();
        return Converter.fromInt(option - 1);
    }

    public RouteSequence selectRouteSeq(VType type) {
        RouteSequence[] routeSeqs = RouteSeqServ.filterByType(type,
                RouteSeqServ.filterByAvailability(true, DB.getRouteSeqs()));
        if (routeSeqs.length == 0) {
            Console.log(
                    "No tenemos secuencias de rutas disponibles en este momento, por favor, inténtelo de nuevo más tarde");
            new MainMenu(user);
        }
        for (int i = 0; i < routeSeqs.length; i++)
            Console.log("\n" + (i + 1) + ". " + routeSeqs[i].getId() + "    " + routeSeqs[i].getOwner().getId());
        int option = 0;
        do {
            Console.log("Seleccione la secuencia de rutas que más le convenga");
            option = Console.readNumber();
            if (option == 0)
                new MainMenu(user);
        } while (option > 0 && option > routeSeqs.length);
        return routeSeqs[option - 1];
    }

    public Route selectRoute(Route[] routes, String message) {
        if (routes.length == 0) {
            Console.log("No tenemos rutas disponibles en este momento, por favor, inténtelo de nuevo más tarde");
            new MainMenu(user);
        }
        for (int i = 0; i < routes.length; i++)
            if (routes[i].getIsAvailable())
                Console.log((i + 1) + ". " + routes[i].info());
        int option = 0;
        do {
            Console.log(message);
            option = Console.readNumber();
            if (option == 0)
                new MainMenu(user);
        } while (option > 0 && option > routes.length);
        Route selected = routes[option - 1];
        if (selected.getIsAvailable() == false) {
            Console.log("La ruta seleccionada no está disponible, por favor inténtelo de nuevo");
            return selectRoute(routes, message);
        }
        return selected;
    }

    public Vehicle selectVehicle(Vehicle[] vehicles, Coupon[] applicable) {
        Coupon coupon = null;
        if (vehicles.length == 0) {
            Console.log(
                    "No tenemos vehículos disponibles en este momento, por favor, inténtelo de nuevo más tarde");
            new MainMenu(user);
        }
        for (int i = 0; i < vehicles.length; i++) {
            coupon = CouponServ.findBestCoupon(applicable, vehicles[i].getPrice());
            StringBuilder builder = new StringBuilder();
            builder.append(i + 1).append(". ").append(vehicles[i].getPlate()).append("\n Ticket: ")
                    .append(coupon == null ? vehicles[i].getPrice()
                            : new StringBuilder().append(CouponServ.discountedPrice(coupon, vehicles[i].getPrice()))
                                    .append("    Antes: ").append(vehicles[i].getPrice()).toString());
            builder.append("\n");
            Console.log(builder.toString());
        }
        int option = 0;
        do {
            Console.log("Seleccione un vehículo");
            option = Console.readNumber();
            if (option == 0)
                new MainMenu(user);
        } while (option > 0 && option > vehicles.length);
        return vehicles[option - 1];
    }

    public Coupon selectCoupon(Coupon[] coupons, int price) {
        Coupon coupon = CouponServ.findBestCoupon(CouponServ.filterPublic(coupons), price);

        if (coupons.length == 0) {
            Console.log("No tenemos cupones disponibles en este momento\n");
            return coupon;
        }
        Coupon[] pCoupons = CouponServ.filterPublic(CouponServ.filterAvailable(coupons));
        for (int i = 0; i < pCoupons.length; i++)
            Console.log((i + 1) + ". " + coupons[i].getName() + "\n " + coupon.getDescription() + "\n Descuento: "
                    + coupon.getDiscount()
                    + (coupon.getDiscountType().equals(Coupon.DiscountType.PERCENTAGE) ? "%" : "")
                    + "    Precio Final: " + CouponServ.discountedPrice(coupons[i], price));

        String redeemCode = "0";
        int option = 0;
        do {
            Console.log("Escriba un número para seleccionar un cupón o el código de uno para redimirlo");
            redeemCode = Console.readData();
            try {
                option = Integer.parseInt(redeemCode);
                if (option == 0)
                    new MainMenu(user);
                if (option > 0 && option <= coupons.length)
                    coupon = pCoupons[option - 1];
            } catch (NumberFormatException e) {
                Coupon temp = CouponServ.searchCouponByWord(coupons, redeemCode);
                if (temp == null) {
                    Console.log("No se ha encontrado el cupón");
                    continue;
                }
                if (!temp.isAvailable()) {
                    Console.log("El cupón ha expirado o ya no está disponible");
                    continue;
                }
                coupon = temp;
            }
        } while (option > 0 && option > coupons.length);
        if (option != 0)
            Console.log("Se ha agregado el cupón " + coupon.getName() + " correctamente");
        return coupon;
    }
}
