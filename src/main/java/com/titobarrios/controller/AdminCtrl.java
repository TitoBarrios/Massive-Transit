package com.titobarrios.controller;

import com.titobarrios.constants.Revenue;
import com.titobarrios.constants.Value;
import com.titobarrios.model.Admin;
import com.titobarrios.model.Company;
import com.titobarrios.model.Coupon;
import com.titobarrios.model.Coupon.DiscountType;
import com.titobarrios.model.Coupon.RedeemType;
import com.titobarrios.model.RouteSequence;
import com.titobarrios.model.User;
import com.titobarrios.model.Vehicle;
import com.titobarrios.view.Console;
import com.titobarrios.view.admin.companies.CompaniesAMenu;
import com.titobarrios.view.admin.coupons.CouponsAMenu;
import com.titobarrios.view.admin.route_seqs.RouteSeqAMenu;
import com.titobarrios.view.admin.users.UsersAMenu;
import com.titobarrios.view.admin.vehicles.VehiclesAMenu;

public class AdminCtrl {
    private Admin admin;

    public AdminCtrl(Admin admin) {
        this.admin = admin;
    }

    public User selectUser(User[] users) {
        User selected = null;
        for (int i = 0; i < users.length; i++)
            Console.log(new StringBuilder().append(i + 1).append(". ").append(users[i].getId()).append("\n Tickets: ")
                    .append(users[i].getTickets().length).append(".   Relaciones: ")
                    .append(users[i].getRelationships().length).append(".    Suscripciones: ")
                    .append(users[i].getSubscriptions().length).toString());
        Console.log("Seleccione un usuario");
        int option = 0;
        do {
            option = Console.readNumber();
            if (option == 0)
                new UsersAMenu(admin);
            if (option < 0 || option > users.length)
                Console.log("Opción inválida, por favor, inténtelo de nuevo");
            selected = users[option - 1];
        } while (option < 0 || option > users.length);
        return selected;
    }

    public String userAdminInfo(User user) {
        StringBuilder builder = new StringBuilder();
        builder.append(user.getId());
        return builder.toString();
    }

    public Company selectCompany(Company[] companies) {
        Company selected = null;
        for (int i = 0; i < companies.length; i++)
            Console.log(new StringBuilder().append(i + 1).append(". ").toString());
        Console.log("Seleccione una empresa");
        int option = 0;
        do {
            option = Console.readNumber();
            if (option == 0)
                new CompaniesAMenu(admin);
            if (option < 0 || option > companies.length)
                Console.log("Opción inválida, por favor, inténtelo de nuevo");
            selected = companies[option - 1];
        } while (option < 0 || option > companies.length);
        return selected;
    }

    public String companyAdminInfo(Company company) {
        StringBuilder builder = new StringBuilder();
        builder.append(company.getId());
        return builder.toString();
    }

    public RouteSequence selectRouteSequence(RouteSequence[] routeSeqs) {
        RouteSequence selected = null;
        for (int i = 0; i < routeSeqs.length; i++)
            Console.log(new StringBuilder().append(i + 1).append(". ").append(routeSeqs[i].getId()).append("    ")
                    .append(routeSeqs[i].getOwner().getId()).toString());
        Console.log("Seleccione una secuencia de rutas");
        int option = 0;
        do {
            option = Console.readNumber();
            if (option == 0)
                new RouteSeqAMenu(admin);
            if (option < 0 || option > routeSeqs.length)
                Console.log("Opción inválida, por favor, inténtelo de nuevo");
            selected = routeSeqs[option - 1];
        } while (option < 0 || option > routeSeqs.length);
        return selected;
    }

    public String routeSeqAdminInfo(RouteSequence routeSeq) {
        StringBuilder builder = new StringBuilder();
        builder.append(routeSeq.getId()).append("     ").append(routeSeq.getOwner().getId())
                .append("     Disponibilidad: ").append(routeSeq.isAvailable() ? "Disponible" : "No Disponible");
        return builder.toString();
    }

    public Vehicle selectVehicle(Vehicle[] vehicles) {
        Vehicle selected = null;
        for (int i = 0; i < vehicles.length; i++)
            Console.log(new StringBuilder().append(i + 1).append(". ").append(vehicles[i].getPlate()).append("    ")
                    .append(vehicles[i].getCompany().getId()).append("    ").append(vehicles[i].getPrice()).append("\n")
                    .toString());
        Console.log("\nSeleccione un vehículo");
        int option = 0;
        do {
            option = Console.readNumber();
            if (option == 0)
                new VehiclesAMenu(admin);
            if (option < 0 || option > vehicles.length)
                Console.log("Opción inválida, por favor, inténtelo de nuevo");
            selected = vehicles[option - 1];
        } while (option < 0 || option > vehicles.length);
        return selected;
    }

    public String vehicleAdminInfo(Vehicle vehicle) {
        StringBuilder builder = new StringBuilder();
        builder.append(vehicle.getType().getUpperCaseName()).append(": ").append(vehicle.getPlate()).append("   ")
                .append(vehicle.getCompany().getId()).append("\nRuta: ").append(vehicle.getRouteSeq().getId())
                .append(".    Ingresos de hoy: ")
                .append(vehicle.getRevenue()[Revenue.DAILY.ordinal()]).append("\nCapacidad: ")
                .append(vehicle.getCapacity()[Value.CURRENT.value()]).append("/")
                .append(vehicle.getCapacity()[Value.MAXIMUM.value()]).append("Precio: ").append(vehicle.getPrice())
                .append(". Cupones Aplicables: ").append(vehicle.getCoupons().length).append("\n")
                .append(vehicle.isAvailable() ? "Disponible" : "No Disponible").append("\n");
        return builder.toString();
    }

    public Coupon selectCoupon(Coupon[] coupons) {
        Coupon selected = null;
        for (int i = 0; i < coupons.length; i++)
            Console.log(new StringBuilder().append(i + 1).append(". ").append(coupons[i].getId()).append("    ")
                    .append(coupons[i].getOwner().getId()).append("\n Descuento: ").append(coupons[i].getDiscount())
                    .append(Coupon.DiscountType.PERCENTAGE.equals(coupons[i].getDiscountType()) ? "%" : "").toString());
        Console.log("Seleccione un cupón");
        int option = 0;
        do {
            option = Console.readNumber();
            if (option == 0)
                new CouponsAMenu(admin);
            if (option < 0 || option > coupons.length)
                Console.log("Opción inválida, por favor, inténtelo de nuevo");
            selected = coupons[option - 1];
        } while (option < 0 || option > coupons.length);
        return selected;
    }

    public String couponAdminInfo(Coupon coupon) {
        StringBuilder builder = new StringBuilder();
        builder.append("[").append(coupon.getId()).append("] ").append(coupon.getName()).append("   ").append(coupon.getOwner().getId()).append("\n ")
                .append(coupon.getDescription()).append("\n")
                .append(coupon.getType().getName()).append(" ")
                .append(coupon.getType() == Coupon.Type.RESERVED ? coupon.getRedeemWord() + "   " : "")
                .append(coupon.getDiscountType()).append("   ").append(coupon.getDiscount())
                .append(coupon.getDiscountType() == DiscountType.PERCENTAGE ? "%" : "").append("\nRedenciones: ")
                .append(coupon.getRedeems()[RedeemType.CURRENT.ordinal()]).append("/")
                .append(coupon.getRedeems()[RedeemType.MAXIMUM.ordinal()]).append("\nExpira: ")
                .append(coupon.getDates()[Value.EXPIRATION.value()]).append("\n");
        return builder.toString();
    }
}
