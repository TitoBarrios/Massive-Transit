package com.titobarrios.controller;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;

import com.titobarrios.model.Company;
import com.titobarrios.model.Coupon;
import com.titobarrios.model.Route;
import com.titobarrios.model.RouteSequence;
import com.titobarrios.model.Vehicle;
import com.titobarrios.utils.LaboralDays;
import com.titobarrios.view.Console;
import com.titobarrios.view.company.coupons.CMainMenu;
import com.titobarrios.view.company.route_seqs.RSMainMenu;

public class CouponCtrl {
    private Company company;

    public CouponCtrl(Company company) {
        this.company = company;
    }

    public Coupon selectCoupon() {
        Coupon[] coupons = company.getCoupons();
        for (int i = 0; i < coupons.length; i++)
            Console.log((i + 1) + ". " + coupons[i].getName() + "\n " + coupons[i].getDescription() + "\n Descuento: "
                    + coupons[i].getDiscount()
                    + (coupons[i].getDiscountType().equals(Coupon.DiscountType.PERCENTAGE) ? "%" : ""));
        Console.log("Seleccione un cupón");
        int option = Console.readNumber();
        if (option == 0)
            new CMainMenu(company);
        if (option < 0 || option > coupons.length)
            return selectCoupon();
        return coupons[option - 1];
    }

    public String selectName() {
        String name = Console.readData();
        return name;
    }

    public String selectString() {
        String string = Console.readData();
        if (string.equals("0"))
            new CMainMenu(company);
        return string;
    }

    public Coupon.Type selectType() {
        Coupon.Type type = null;
        Console.log("Cuál será la privacidad del cupón?\n1. Público   2. Reservado");
        int option = Console.readNumber();
        switch (option) {
            case 1:
                type = Coupon.Type.PUBLIC;
                break;
            case 2:
                type = Coupon.Type.RESERVED;
                break;
            case 0:
                new CMainMenu(company);
            default:
                return selectType();
        }
        return type;
    }

    public String selectRedeemWord() {
        String redeemWord = null;
        Console.log("Digite la palabra con la que el usuario canjeará el cupón (debe tener al menos una letra)");
        redeemWord = selectString();
        try {
            Integer.parseInt(redeemWord);
            Console.log("La palabra debe tener al menos una letra, inténtelo nuevamente");
            return selectRedeemWord();
        } catch (NumberFormatException e) {
        }
        return redeemWord;
    }

    public Coupon.AppliesTo selectApplicable() {
        Coupon.AppliesTo applicable = null;
        Console.log("A qué tipo de objetos aplicará el cupón?\n1. Vehículos   2. Secuencias de rutas   3. Rutas");
        int option = Console.readNumber();
        switch (option) {
            case 1:
                applicable = Coupon.AppliesTo.VEHICLES;
                break;
            case 2:
                applicable = Coupon.AppliesTo.ROUTE_SEQS;
                break;
            case 3:
                applicable = Coupon.AppliesTo.ROUTES;
                break;
            case 0:
                new CMainMenu(company);
            default:
                return selectApplicable();
        }
        return applicable;
    }

    public Vehicle[] selectVehicles() {
        ArrayList<Vehicle> selected = new ArrayList<Vehicle>();
        Vehicle[] vehicles = company.getVehicles();
        for (int i = 0; i < vehicles.length; i++)
            Console.log((i + 1) + ". " + vehicles[i].getId());
        Console.log("Seleccione uno por uno, cada vehículo que desea agregar.\n0. Terminar de leer");
        addUntilStop(selected, vehicles);
        return selected.toArray(Vehicle[]::new);
    }

    public RouteSequence[] selectRouteSeqs() {
        ArrayList<RouteSequence> selected = new ArrayList<RouteSequence>();
        RouteSequence[] routeSeqs = company.getRouteSeqs();
        for (int i = 0; i < routeSeqs.length; i++)
            Console.log((i + 1) + ". " + routeSeqs[i].getId());
        Console.log("Seleccione una por una, cada secuencia de rutas que desea agregar.\n0. Terminar de leer");
        addUntilStop(selected, routeSeqs);
        return selected.toArray(RouteSequence[]::new);
    }

    public Route[] selectRoutes() {
        ArrayList<Route> selected = new ArrayList<Route>();
        RouteSequence routeSeq = selectRouteSeq();
        for (int i = 0; i < routeSeq.getRoutes().length; i++)
            Console.log((i + 1) + ". " + routeSeq.getRoutes()[i].info());
        Console.log("Seleccione una por una, cada ruta que desea agregar.\n0. Terminar de leer");
        addUntilStop(selected, routeSeq.getRoutes());
        return selected.toArray(Route[]::new);
    }

    private RouteSequence selectRouteSeq() {
        RouteSequence[] routeSeqs = company.getRouteSeqs();
        RouteSequence selected = null;
        Console.log("Seleccione una secuencia de rutas");
        for (int i = 0; i < routeSeqs.length; i++)
            Console.log((i + 1) + ". " + routeSeqs[i].getId());
        int option = 0;
        do {
            option = Console.readNumber();
            if (option == 0)
                new CMainMenu(company);
            if (option < 0 || option > routeSeqs.length)
                Console.log("Opción inválida, por favor, inténtelo de nuevo");
        } while (option < 0 || option > routeSeqs.length);
        selected = routeSeqs[option - 1];
        Console.log("Secuencia: " + selected.getId());
        return selected;
    }

    private <T> void addUntilStop(ArrayList<T> arrayList, T[] list) {
        int option = 0;
        ArrayList<Integer> selected = new ArrayList<>();
        do {
            option = Console.readNumber();
            if (option == 0 && arrayList.size() == 0) {
                Console.log("Se ha cancelado la operación");
                new CMainMenu(company);
            }
            if (option < 0 || option > list.length) {
                Console.log("Número inválido, por favor, inténtelo de nuevo");
                continue;
            }
            if(isRepeated(selected, option)) {
                Console.log("El elemento ya ha sido seleccionado, inténtelo de nuevo");
                continue;
            }
            if (option == 0) {
                Console.log("Se ha terminado de leer");
                continue;
            }
            arrayList.add(list[option - 1]);
            selected.add(option);
            Console.log("Se ha agregado el objeto");
        } while (option != 0);
    }

    private boolean isRepeated(ArrayList<Integer> selected, int selection) {
        for(Integer number : selected)
            if(number == selection)
                return true;
        return false;
    }

    public Coupon.DiscountType selectDiscountType() {
        Coupon.DiscountType discountType = null;
        Console.log("Seleccione el tipo de descuento del cupón\n1. Cantidad   2. Porcentaje");
        int option = Console.readNumber();
        switch (option) {
            case 1:
                discountType = Coupon.DiscountType.QUANTITY;
                break;
            case 2:
                discountType = Coupon.DiscountType.PERCENTAGE;
                break;
            case 0:
                new CMainMenu(company);
            default:
                selectDiscountType();
        }
        return discountType;
    }

    public int selectDiscount(Coupon.DiscountType discountType) {
        Console.log("Digite el " + (discountType == Coupon.DiscountType.PERCENTAGE ? "porcentaje de " : "")
                + "descuento del cupón");
        int discount = Console.readNumber();
        if (discount == 0)
            new CMainMenu(company);
        if (discount < 0)
            selectDiscount(discountType);
        if (discount > 100 && discountType == Coupon.DiscountType.PERCENTAGE) {
            Console.log("El porcentaje de descuento se ha ajustado automáticamente al 100%");
            discount = 100;
        }
        return discount;
    }

    public int selectNumber() {
        int option = Console.readNumber();
        if (option == 0)
            new CMainMenu(company);
        if (option < 0)
            selectNumber();
        return option;
    }

    public LocalDateTime selectDate() {
        String dateFormat = Console.readData();
        if (dateFormat.equals("0"))
            new CMainMenu(company);
        LocalDateTime date = null;
        try {
            date = LocalDateTime.parse(dateFormat, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        } catch (DateTimeParseException e) {
            Console.log("Fecha escrita en el formato incorrecto, por favor, inténtelo de nuevo");
            selectDate();
        }
        return date;
    }

    public DayOfWeek[] selectActiveDays() {
        Console.log(
                "Seleccione los días que estará activo el cupón\n1. Lunes  2. Martes  3. Miércoles  4. Jueves  5. Viernes  6. Sábado 7. Domingo\n8. Todos los días  -1. Terminar de leer");
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        int option = 0;
        do {
            option = Console.readNumber();
            if (option == -1) {
                Console.log("Se terminó de leer");
                break;
            }
            if (option == 0)
                new RSMainMenu(company);
            if (option < -1 || option > 8)
                continue;
            numbers.add(option);
            if (option == 8) {
                Console.log("Ha seleccionado todos los días");
                break;
            }
        } while (option != -1);
        return LaboralDays
                .fromInt(Arrays.stream(numbers.toArray(Integer[]::new)).mapToInt(Integer::intValue).toArray());
    }
}
