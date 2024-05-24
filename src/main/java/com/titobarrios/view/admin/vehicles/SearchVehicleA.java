package com.titobarrios.view.admin.vehicles;

import com.titobarrios.controller.AdminCtrl;
import com.titobarrios.db.DB;
import com.titobarrios.model.Admin;
import com.titobarrios.model.Vehicle;
import com.titobarrios.view.Console;

public class SearchVehicleA {
    private AdminCtrl ctrl;
    private Admin admin;

    public SearchVehicleA(Admin admin) {
        this.admin = admin;
        this.ctrl = new AdminCtrl(admin);
        menu();
    }

    private void menu() {
        Console.log("Menú de Búsqueda");
        Vehicle vehicle = ctrl.selectVehicle(DB.getVehicles());
        Console.log("\nVehículo: " + ctrl.vehicleAdminInfo(vehicle) + "\n1. Eliminar   0. Volver");
        int option = Console.readNumber();
        if(option == 1) {
            Console.log("Está seguro que desea eliminar este vehículo?\n1. Sí   2. No");
            option = Console.readNumber();
            if(option == 1) {
                vehicle.delete();
                Console.log("Se ha eliminado el vehículo correctamente");
            }
        }
        new VehiclesAMenu(admin);
    }
}
