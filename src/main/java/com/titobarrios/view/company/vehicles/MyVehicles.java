package com.titobarrios.view.company.vehicles;

import com.titobarrios.constants.VType;
import com.titobarrios.model.Company;
import com.titobarrios.model.Vehicle;
import com.titobarrios.services.VehicleServ;
import com.titobarrios.view.Console;

public class MyVehicles {
    private Company company;

    public MyVehicles(Company company) {
        this.company = company;
        menu();
    }

    private void menu() {
        Vehicle[] vehicles = company.getVehicles();
        Console.log("Tiene inscritos " + vehicles.length + " vehículos");
        Console.log("\nAviones:");
        showVehicles(VehicleServ.filterByType(VType.AIRPLANE, vehicles));
        Console.log("\nBuses:");
        showVehicles(VehicleServ.filterByType(VType.BUS, vehicles));
        Console.log("\nBarcos");
        showVehicles(VehicleServ.filterByType(VType.SHIP, vehicles));
        Console.log("\nBuses de Viaje:");
        showVehicles(VehicleServ.filterByType(VType.TRAVEL_BUS, vehicles));
        Console.log("\nDigita cualquier tecla para volver");
        Console.readData();
        new VMainMenu(company);
    }

    private void showVehicles(Vehicle[] vehicles) {
        for (Vehicle vehicle : vehicles)
            Console.log(vehicle.info());
        if (vehicles.length == 0)
            Console.log("No tienes ningún vehículo de este tipo");
    }
}
