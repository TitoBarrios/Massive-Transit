package com.titobarrios.view.company.vehicles;

import com.titobarrios.constants.Value;
import com.titobarrios.controller.VehicleCtrl;
import com.titobarrios.model.Company;
import com.titobarrios.model.Vehicle;
import com.titobarrios.services.RouteSeqServ;
import com.titobarrios.view.Console;

public class EditVehicle {
    private VehicleCtrl ctrl;
    private Company company;

    public EditVehicle(Company company) {
        this.company = company;
        ctrl = new VehicleCtrl(company);
        menu();
    }

    private void menu() {
        Console.log("Seleccione un vehículo para editar");
        Vehicle[] vehicles = company.getVehicles();
        for (int i = 0; i < vehicles.length; i++)
            Console.log((i + 1) + ". " + vehicles[i].info());
        int option = 0;
        if (option == 0)
            new VMainMenu(company);
        if (option < 0 || option > vehicles.length)
            new EditVehicle(company);
        Vehicle selected = vehicles[option - 1];
        edit(selected);

    }

    private void edit(Vehicle vehicle) {
        Console.log(
                "Seleccione la característica a editar\n1. Edición Completa\n2. Placa    3. Precio\n4. Capacidad     5. Secuencia de Rutas");
        int option = Console.readNumber();
        switch (option) {
            case 1:
                Console.log("Edición Completa");
            case 2:
                Console.log("Editar placa, placa actual: " + vehicle.getPlate());
                vehicle.setPlate(ctrl.selectPlate());
                Console.log("Se ha editado la placa correctamente. Nueva placa: " + vehicle.getPlate());
                if (option != 1)
                    break;
            case 3:
                Console.log("Editar precio, precio actual: " + vehicle.getPrice());
                vehicle.setPrice(ctrl.selectPrice());
                Console.log("Se ha editado el precio correctamente. Nuevo precio: " + vehicle.getPrice());
                if (option != 1)
                    break;
            case 4:
                Console.log("Editar capacidad, capacidad actual: " + vehicle.getCapacity());
                vehicle.setCapacity(new int[] { vehicle.getCapacity()[Value.CURRENT.value()], ctrl.selectCapacity() });
                Console.log("Se ha editado la capacidad correctamente. Nueva capacidad: "
                        + vehicle.getCapacity()[Value.MAXIMUM.value()]);
                if (option != 1)
                    break;
            case 5:
                Console.log("Editar secuencia de rutas, actual: " + vehicle.getRouteSeq().getName());
                vehicle.setRouteSequence(
                        ctrl.selectRouteSeq(RouteSeqServ.filterByType(vehicle.getType(), company.getRouteSeqs())));
                Console.log("Se ha editado la secuencia de rutas exitosamente Nueva secuencia: " + vehicle.getRouteSeq().getName());
                if (option != 1)
                    Console.log("Vehículo editado correctamente.");
                break;
            case 0:
                menu();
                break;
            default:
                edit(vehicle);
        }
        new VMainMenu(company);
    }
}
