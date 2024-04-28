package com.titobarrios.view.company.financial_data;

import com.titobarrios.constants.VType;
import com.titobarrios.model.Company;
import com.titobarrios.model.Vehicle;
import com.titobarrios.services.VehicleServ;
import com.titobarrios.view.Console;
import com.titobarrios.view.company.MainMenu;

public class Finances {
    private Company company;

    public Finances(Company company) {
        this.company = company;
        menu();
    }

    private void menu() {
        Console.log("1. Informe completo   |   2. Mis ingresos   |   3. "
                + VType.AIRPLANE.getUpperCaseName() + "\n4. " + VType.BUS.getUpperCaseName() + "   |   5. "
                + VType.SHIP.getUpperCaseName() + "6. " + VType.TRAVEL_BUS.getUpperCaseName());
        int option = Console.readNumber();
        Vehicle[] vehicles = company.getVehicles();
        switch (option) {
            case 1:
                completeReport();
            case 2:
                companyReport();
            case 3:
                vehiclesReport(VehicleServ.filterByType(VType.AIRPLANE, vehicles));
            case 4:
                vehiclesReport(VehicleServ.filterByType(VType.BUS, vehicles));
            case 5:
                vehiclesReport(VehicleServ.filterByType(VType.SHIP, vehicles));
            case 6:
                vehiclesReport(VehicleServ.filterByType(VType.TRAVEL_BUS, vehicles));
            case 0:
                new MainMenu(company);
            default:
                Console.log("Opción inválida, por favor, inténtelo de nuevo\n");
                menu();
        }
    }

    private void completeReport() {
        Console.log("¿Desea ver un informe con cada ticket vendido?\n1. Sí    2. No");
        int option = 0;
        if (option == 1)
            completeReportWithTickets();
        if (option == 2)
            completeReportWithoutTickets();
        if (option == 0)
            menu();
        Console.log("Opción inválida, por favor, inténtelo de nuevo\n");
        completeReport();
    }

    private void completeReportWithTickets() {

    }

    private void completeReportWithoutTickets() {

    }

    private void companyReport() {

    }

    private void vehiclesReport(Vehicle[] vehicles) {

    }

    private void vehicleReport(Vehicle vehicle) {

    }
}
