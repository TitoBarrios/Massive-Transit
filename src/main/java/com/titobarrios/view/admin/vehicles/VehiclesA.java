package com.titobarrios.view.admin.vehicles;

import com.titobarrios.controller.AdminCtrl;
import com.titobarrios.db.DB;
import com.titobarrios.model.Admin;
import com.titobarrios.model.Vehicle;
import com.titobarrios.view.Console;

public class VehiclesA {
    private AdminCtrl ctrl;
    private Admin admin;

    public VehiclesA(Admin admin) {
        this.admin = admin;
        ctrl = new AdminCtrl(admin);
        menu();
    }

    private void menu() {
        Console.log("Los vehículos inscritos son los siguientes:");
        for(Vehicle vehicle : DB.getVehicles())
            Console.log(ctrl.vehicleAdminInfo(vehicle));
        Console.log("Digita cualquier tecla para volver");
        Console.readData();
        new VehiclesAMenu(admin);
    }
}
