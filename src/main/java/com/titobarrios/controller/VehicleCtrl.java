package com.titobarrios.controller;

import com.titobarrios.constants.VType;
import com.titobarrios.model.Company;
import com.titobarrios.model.RouteSequence;
import com.titobarrios.model.Vehicle;
import com.titobarrios.utils.Converter;
import com.titobarrios.view.Console;
import com.titobarrios.view.company.vehicles.VMainMenu;

public class VehicleCtrl {
    public Company company;

    public VehicleCtrl(Company company) {
        this.company = company;
    }

    public Vehicle selectVehicle(Vehicle[] vehicles) {
        for (int i = 0; i < vehicles.length; i++)
            Console.log((i + 1) + ". " + vehicles[i].info());
        int option = 0;
        if (option == 0)
            new VMainMenu(company);
        if (option < 0 || option > vehicles.length)
            return selectVehicle(vehicles);
        return vehicles[option - 1];
    }

    public VType selectType() {
        Console.log("¿Qué tipo de vehículo desea?" + VType.menu());
        int option = Console.readNumber();
        if (option == 0)
            new VMainMenu(company);
        if (option < 0 || option > 4)
            return selectType();
        return Converter.fromInt(option - 1);
    }

    public String selectPlate() {
        String plate = Console.readData();
        if (plate.equals("0"))
            new VMainMenu(company);
        return plate;
    }

    public int selectPrice() {
        Console.log("Digite el precio del ticket");
        int price = 0;
        do {
            price = Console.readNumber();
            if (price == 0)
                new VMainMenu(company);
            if (price < 0)
                Console.log("El precio no puede ser negativo, inténtelo de nuevo");
        } while (price < 0);
        return price;
    }

    public int selectCapacity() {
        Console.log("Digite la máxima capacidad de transporte");
        int capacity = 0;
        do {
            capacity = Console.readNumber();
            if (capacity == 0)
                new VMainMenu(company);
            if (capacity < 0)
                Console.log("La capacidad no puede ser negativa, inténtelo de nuevo");
        } while (capacity < 0);
        return capacity;
    }

    public RouteSequence selectRouteSeq(RouteSequence[] routeSeqs) {
        RouteSequence selected = null;
        if(routeSeqs.length == 0) {
            Console.log("No hay secuencias de rutas creadas para este tipo de vehículo");
            new VMainMenu(company);
        }
        Console.log("Seleccione una secuencia de rutas");
        for (int i = 0; i < routeSeqs.length; i++)
            Console.log((i + 1) + ". " + routeSeqs[i].getId());
        int option = 0;
        do {
            option = Console.readNumber();
            if (option == 0)
                new VMainMenu(company);
        } while (option < 0 || option > routeSeqs.length);
        selected = routeSeqs[option - 1];
        Console.log("Secuencia: " + selected.getId());
        for (int i = 0; i < selected.getRoutes().length; i++)
            Console.log((i + 1) + ". " + selected.getRoutes()[i].info());
        Console.log("1. Seleccionar     0. Volver");
        do {
            option = Console.readNumber();
            if (option == 0)
                return selectRouteSeq(routeSeqs);
            if (option != 1)
                Console.log("Opción inválida");
        } while (option != 1);
        return selected;
    }
}
