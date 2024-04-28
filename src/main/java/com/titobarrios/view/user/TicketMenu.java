package com.titobarrios.view.user;

import com.titobarrios.constants.VType;
import com.titobarrios.db.DB;
import com.titobarrios.model.Coupon;
import com.titobarrios.model.Route;
import com.titobarrios.model.RouteSequence;
import com.titobarrios.model.Ticket;
import com.titobarrios.model.User;
import com.titobarrios.model.Vehicle;
import com.titobarrios.services.CouponServ;
import com.titobarrios.services.RouteSeqServ;
import com.titobarrios.utils.ArraysUtil;
import com.titobarrios.utils.Converter;
import com.titobarrios.utils.Wallet;
import com.titobarrios.view.Console;

public class TicketMenu {
    private User user;
    private User owner;

    public TicketMenu(User user) {
        this.user = user;
        owner = null;
        menu();
    }

    public TicketMenu(User owner, User buyer) {
        this.user = buyer;
        this.owner = owner;
        menu();
    }

    private void menu() {
        VType type = selectType();
        RouteSequence routeSeq = selectRouteSeq(RouteSeqServ.filterByType(type, DB.getRouteSeqs()));
        Route entry = selectRoute(routeSeq.getRoutes(), "Seleccione la ruta por la cual entrará");
        Route exit = selectRoute(routeSeq.getRoutes(), "Seleccione la ruta por la cual saldrá");
        Coupon[] applicable = ArraysUtil.combineArrays(routeSeq.getCoupons(), entry.getCoupons(), exit.getCoupons());
        Vehicle vehicle = selectVehicle(routeSeq.getVehicles(), CouponServ.filterPublic(applicable));
        applicable = ArraysUtil.combineArrays(vehicle.getCoupons(), applicable);
        Coupon coupon = CouponServ.findBestCoupon(CouponServ.filterPublic(applicable), vehicle.getPrice());
        do {
            Console.log("1. Comprar     2. Agregar/Cambiar Cupón    3. Reiniciar    Cupón Actual: " + coupon != null
                    ? coupon.getName()
                    : "Ninguno" + "\nPrecio actual: " + CouponServ.discountedPrice(coupon, vehicle.getPrice())
                            + "    Su saldo: " + user.getWallet() + "\n0. Cancelar y volver");
            int option = 0;
            do {
                option = Console.readNumber();
                if (option == 0)
                    new MainMenu(user);
            } while (option > 0 && option <= 3);

            switch (option) {
                case 1:
                    if (!Wallet.isAffordable(user, vehicle.getPrice())) {
                        Console.log("Su saldo no es suficiente para realizar esta transacción");
                        continue;
                    }
                    Console.log("Su ticket será el siguiente:\n Secuencia: " + routeSeq + "\n Entrada: "
                            + entry.getStops()[Route.StopType.ENTRY.ordinal()] + "\n Salida: "
                            + exit.getStops()[Route.StopType.EXIT.ordinal()] + "\n Vehículo: " + vehicle.getPlate()
                            + "\n Precio Final: " + CouponServ.discountedPrice(coupon, vehicle.getPrice())
                            + (owner == null ? "" : ("\nSerá enviado a: " + owner.getId()))
                            + "\n\n1. Confirmar  0. Volver");
                    option = Console.readNumber();
                    if (option != 1) {
                        Console.log("Se ha cancelado la operación");
                        continue;
                    }
                    if (owner == null) {
                        Console.log("Se ha generado correctamente su ticket, gracias por su compra!");
                        new Ticket(user, user, coupon, vehicle, new Route[] { entry, exit }).bill();
                    } else {
                        Console.log("Se ha enviado el ticket a " + user.getId() + ", gracias por su compra!");
                        new Ticket(owner, user, coupon, vehicle, new Route[] { entry, exit }).bill();
                    }
                    new MainMenu(user);
                case 2:
                    coupon = selectCoupon(applicable, option);
                    continue;
                case 3:
                    menu();
            }
        } while (true);
    }

    private VType selectType() {
        Console.log("Seleccione el tipo de vehículo" + VType.menu() + "\n0. Volver");
        int option = Console.readNumber();
        if (option == 0)
            new MainMenu(user);
        if (option < 0 || option > 4)
            menu();
        return Converter.fromInt(option - 1);
    }

    private RouteSequence selectRouteSeq(RouteSequence[] routeSeqs) {
        for (int i = 0; i < routeSeqs.length; i++)
            if (routeSeqs[i].isAvailable())
                Console.log("\n" + (i + 1) + ". " + routeSeqs[i].getName() + "    " + routeSeqs[i].getOwner().getId());
        int option = 0;
        do {
            Console.log("Seleccione la secuencia de rutas que más le convenga");
            option = Console.readNumber();
            if (option == 0)
                new MainMenu(user);
        } while (option > 0 && option <= routeSeqs.length);
        return routeSeqs[option - 1];
    }

    private Route selectRoute(Route[] routes, String message) {
        for (int i = 0; i < routes.length; i++)
            if (routes[i].getIsAvailable())
                Console.log((i + 1) + ". " + routes[i].info());
        int option = 0;
        do {
            Console.log(message);
            option = Console.readNumber();
            if (option == 0)
                new MainMenu(user);
        } while (option > 0 && option <= routes.length);
        return routes[option - 1];
    }

    private Vehicle selectVehicle(Vehicle[] vehicles, Coupon[] applicable) {
        Coupon coupon = null;
        for (int i = 0; i < vehicles.length; i++) {
            coupon = CouponServ.findBestCoupon(applicable, vehicles[i].getPrice());
            StringBuilder builder = new StringBuilder();
            builder.append(i + 1).append(". ").append(vehicles[i].getPlate()).append("\n Ticket: ");
            if (coupon != null)
                builder.append(CouponServ.discountedPrice(coupon, vehicles[i].getPrice())).append("    Antes: ")
                        .append(vehicles[i].getPrice());
            builder.append("\n");
            Console.log(builder.toString());
        }
        int option = 0;
        do {
            Console.log("Seleccione un vehículo");
            option = Console.readNumber();
            if (option == 0)
                new MainMenu(user);
        } while (option > 0 && option <= vehicles.length);
        return vehicles[option - 1];
    }

    private Coupon selectCoupon(Coupon[] coupons, int price) {
        Coupon coupon = CouponServ.findBestCoupon(CouponServ.filterPublic(coupons), price);

        if (coupons.length == 0) {
            Console.log("No tenemos cupones disponibles en este momento");
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
                if (option != 0)
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
        } while (option > 0 && option <= coupons.length);
        if (option != 0)
            Console.log("Se ha agregado el cupón " + coupon.getName() + " correctamente");
        return coupon;
    }
}
