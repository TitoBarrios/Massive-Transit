package com.titobarrios.view.company.vehicles;

import com.titobarrios.constants.VType;
import com.titobarrios.controller.VehicleCtrl;
import com.titobarrios.model.Company;
import com.titobarrios.model.RouteSequence;
import com.titobarrios.services.RouteSeqServ;
import com.titobarrios.services.VehicleServ;
import com.titobarrios.view.Console;

public class CreateVehicle {
    private VehicleCtrl ctrl;
    private Company company;

    public CreateVehicle(Company company) {
        this.company = company;
        ctrl = new VehicleCtrl(company);
        menu();
    }

    private void menu() {
        VType type = ctrl.selectType();
        Console.log("Digite la placa del " + type.getName());
        String plate = ctrl.selectPlate();
        int price = ctrl.selectPrice();
        int capacity = ctrl.selectCapacity();
        RouteSequence routeSeq = ctrl.selectRouteSeq(RouteSeqServ.filterByType(type, company.getRouteSeqs()));
        VehicleServ.createVehicle(type, company, plate, routeSeq, price, capacity);
        Console.log("Se ha creado el " + type.getName() + " correctamente");
    }
}
