package com.titobarrios.view.user;

import com.titobarrios.constants.VType;
import com.titobarrios.controller.TicketCtrl;
import com.titobarrios.model.Coupon;
import com.titobarrios.model.Route;
import com.titobarrios.model.RouteSequence;
import com.titobarrios.model.Ticket;
import com.titobarrios.model.User;
import com.titobarrios.model.Vehicle;
import com.titobarrios.services.CouponServ;
import com.titobarrios.utils.ArraysUtil;
import com.titobarrios.utils.Wallet;
import com.titobarrios.view.Console;

public class TicketMenu {
    private TicketCtrl ctrl;
    private User user;
    private User owner;

    public TicketMenu(User user) {
        this.user = user;
        owner = null;
        ctrl = new TicketCtrl(user);
        menu();
    }

    public TicketMenu(User owner, User buyer) {
        this.user = buyer;
        this.owner = owner;
        ctrl = new TicketCtrl(user);
        menu();
    }

    private void menu() {
        VType type = ctrl.selectType();
        RouteSequence routeSeq = ctrl.selectRouteSeq(type);
        Route entry = ctrl.selectRoute(routeSeq.getRoutes(), "Seleccione la ruta por la cual entrará");
        Route exit = ctrl.selectRoute(routeSeq.getRoutes(), "Seleccione la ruta por la cual saldrá");
        Coupon[] applicable = ArraysUtil.combineArrays(routeSeq.getCoupons(), entry.getCoupons(), exit.getCoupons());
        Vehicle vehicle = ctrl.selectVehicle(routeSeq.getVehicles(), CouponServ.filterPublic(applicable));
        applicable = ArraysUtil.combineArrays(vehicle.getCoupons(), applicable);
        Coupon coupon = CouponServ.findBestCoupon(CouponServ.filterPublic(applicable), vehicle.getPrice());
        do {
            Console.log("1. Comprar     2. Agregar/Cambiar Cupón    3. Reiniciar    Cupón Actual: " + (coupon == null
                    ? "Ninguno" : coupon.getName()) + "\nPrecio actual: " + CouponServ.discountedPrice(coupon, vehicle.getPrice())
                            + "    Su saldo: " + user.getWallet() + "\n0. Cancelar y volver");
            int option = 0;
            do {
                option = Console.readNumber();
                if (option == 0)
                    new MainMenu(user);
            } while (option > 0 && option > 3);

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
                    coupon = ctrl.selectCoupon(applicable, vehicle.getPrice());
                    continue;
                case 3:
                    menu();
            }
        } while (true);
    }
}
