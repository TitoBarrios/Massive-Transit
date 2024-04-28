package com.titobarrios.view.company.route_seqs;

import com.titobarrios.controller.RouteSeqCtrl;
import com.titobarrios.model.Company;
import com.titobarrios.model.RouteSequence;
import com.titobarrios.view.Console;

public class EditRouteSeq {
    private RouteSeqCtrl ctrl;
    private Company company;

    public EditRouteSeq(Company company) {
        this.company = company;
        ctrl = new RouteSeqCtrl(company);
        menu();
    }

    private void menu() {
        RouteSequence selected = ctrl.selectRouteSeq(company.getRouteSeqs());
        edit(selected);
    }

    private void edit(RouteSequence routeSeq) {
        Console.log(
                "¿Qué desea característica desea editar?\n1. Edición completa   2. Tipo de vehículo objetivo   3. Nombre\n4. Días Laborales   5. Hora de inicio   6. Número de paradas e intervalos\nSecuencia seleccionada: "
                        + routeSeq.getId());
        int option = Console.readNumber();
        switch (option) {
            case 1:
                Console.log("Edición completa");
            case 2:
                routeSeq.setType(ctrl.selectType());
                Console.log("Se ha editado exitosamente el tipo, nuevo tipo: " + routeSeq.getType().getUpperCaseName());
                if (option != 1)
                    break;
            case 3:
                routeSeq.setId(ctrl.selectId());
                Console.log("se ha editado exitosamente el nombre, nuevo nombre: " + routeSeq.getId());
                if (option != 1)
                    break;
            case 4:
                routeSeq.setLaboralDays(ctrl.selectLaboralDays());
                Console.log("Se han editado exitosamente los días laborales");
                if (option != 1)
                    break;
            case 5:
                Console.log("Edición de esta característica en desarrollo");
                if (option != 1)
                    break;
            case 6:
                Console.log("Edición de esta característica en desarrollo");
                if (option == 1)
                    Console.log("Se ha editado completamente la ruta " + routeSeq.getId());
                break;
            case 0:
                new RSMainMenu(company);
            default:
                menu();
        }
        new RSMainMenu(company);
    }
}
