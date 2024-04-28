package com.titobarrios.view.company.financial_data;

import com.titobarrios.constants.VType;
import com.titobarrios.constants.Value;
import com.titobarrios.model.Company;
import com.titobarrios.model.Route;
import com.titobarrios.model.Ticket;
import com.titobarrios.model.Vehicle;
import com.titobarrios.services.FinancesServ;
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
                completeReport(vehicles, withTickets());
                break;
            case 2:
                Console.log(companyReport());
                break;
            case 3:
                vehiclesReport(VehicleServ.filterByType(VType.AIRPLANE, vehicles));
                break;
            case 4:
                vehiclesReport(VehicleServ.filterByType(VType.BUS, vehicles));
                break;
            case 5:
                vehiclesReport(VehicleServ.filterByType(VType.SHIP, vehicles));
                break;
            case 6:
                vehiclesReport(VehicleServ.filterByType(VType.TRAVEL_BUS, vehicles));
                break;
            case 0:
                new MainMenu(company);
            default:
                Console.log("Opción inválida, por favor, inténtelo de nuevo\n");
                menu();
        }
        Console.log("Digite cualquier tecla para volver");
        Console.readData();
        new MainMenu(company);
    }

    private void completeReport(Vehicle[] vehicles, boolean withTickets) {
        Console.log(companyReport() + "\n");
        Console.log("Aviones:");
        vehiclesReport(VehicleServ.filterByType(VType.AIRPLANE, vehicles));
        Console.log("Buses:");
        vehiclesReport(VehicleServ.filterByType(VType.BUS, vehicles));
        Console.log("Barcos:");
        vehiclesReport(VehicleServ.filterByType(VType.SHIP, vehicles));
        Console.log("Buses de Viaje:");
        vehiclesReport(VehicleServ.filterByType(VType.TRAVEL_BUS, vehicles));
    }

    private String companyReport() {
        StringBuilder builder = new StringBuilder();
        builder.append(company.getId()).append("\nIngresos: ").append(company.getRevenue()[Value.GENERAL.value()])
                .append("\n Anuales: ")
                .append(company.getRevenue()[Value.YEARLY.value()]).append("\n Mensuales: ")
                .append(company.getRevenue()[Value.MONTHLY.value()]).append("\n Diarios: ")
                .append(company.getRevenue()[Value.DAILY.value()]);
        return builder.toString();
    }

    private void vehiclesReport(Vehicle[] vehicles) {
        Console.log(vehiclesReport(vehicles, withTickets()));
    }

    private String vehiclesReport(Vehicle[] vehicles, boolean withTickets) {
        int[] revenue = FinancesServ.vehiclesRevenue(vehicles);
        StringBuilder builder = new StringBuilder();
        builder.append("Total: ").append(revenue[Value.GENERAL.value()]).append("    Total Anual: ")
                .append(revenue[Value.YEARLY.value()]).append("\nTotal Mensual: ")
                .append(revenue[Value.MONTHLY.value()]).append("    Total Diario: ")
                .append(revenue[Value.DAILY.value()]).append("\nVehículos: ").append(vehicles.length).append("\n");
        for (int i = 0; i < vehicles.length; i++)
            builder.append("\n").append(i + 1).append(". ")
                    .append(withTickets ? vehicleReportWithTickets(null) : vehicleReportWithoutTickets(vehicles[i]));
        return builder.toString();
    }

    private String vehicleReportWithoutTickets(Vehicle vehicle) {
        StringBuilder builder = new StringBuilder();
        builder.append(vehicle.getPlate()).append("\nGanancias Totales: ")
                .append(vehicle.getRevenue()[Value.GENERAL.value()]).append("\nGanancias Anuales: ")
                .append(vehicle.getRevenue()[Value.YEARLY.value()]).append("\nGanancias Mensuales: ")
                .append(vehicle.getRevenue()[Value.MONTHLY.value()]).append("\nGanancias Diarias: ")
                .append(vehicle.getRevenue()[Value.DAILY.value()]);
        return builder.toString();
    }

    private String vehicleReportWithTickets(Vehicle vehicle) {
        StringBuilder builder = new StringBuilder();
        builder.append(vehicleReportWithoutTickets(vehicle)).append("\n");
        for (Ticket ticket : vehicle.getTickets())
            builder.append("\n").append(ticketReport(ticket));
        return builder.toString();
    }

    private String ticketReport(Ticket ticket) {
        StringBuilder builder = new StringBuilder();
        builder.append("Id: ").append(ticket.getName()).append("   Cliente: ").append(ticket.getOwner().getId())
                .append("   Regalo: ").append(ticket.getOwner().equals(ticket.getBuyer()) ? " Si" : "NO")
                .append("\n Entrada: ")
                .append(ticket.getRoutes()[Route.StopType.ENTRY.ordinal()].getStops()[Route.StopType.ENTRY.ordinal()])
                .append("   Salida: ")
                .append(ticket.getRoutes()[Route.StopType.EXIT.ordinal()].getStops()[Route.StopType.EXIT.ordinal()])
                .append("\nPrecio pagado: ").append(ticket.getPrice());
        return builder.toString();
    }

    private boolean withTickets() {
        Console.log("¿Desea ver un informe con cada ticket vendido?\n1. Sí    2. No");
        int option = Console.readNumber();
        switch (option) {
            case 1:
                return true;
            case 2:
                return false;
            case 0:
                menu();
            default:
                Console.log("Opción inválida, por favor, inténtelo de nuevo\n");
                menu();
        }
        return false;
    }
}
