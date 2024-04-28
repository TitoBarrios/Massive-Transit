package com.titobarrios.view.company.vehicles;

import com.titobarrios.controller.VehicleCtrl;
import com.titobarrios.model.Company;
import com.titobarrios.view.Console;

public class DeleteVehicle {
    private VehicleCtrl ctrl;
    private Company company;

    public DeleteVehicle(Company company) {
        this.company = company;
        ctrl = new VehicleCtrl(company);
        menu();
    }

    private void menu() {
        Console.log("Seleccione un vehículo para eliminar");
        ctrl.selectVehicle(company.getVehicles()).delete();
        Console.log("Se ha eliminado el vehículo correctamente");
        new VMainMenu(company);
    }
}
