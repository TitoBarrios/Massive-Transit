package com.titobarrios.view.company.route_seqs;

import java.time.DayOfWeek;
import java.time.LocalTime;

import com.titobarrios.constants.VType;
import com.titobarrios.controller.RouteSeqCtrl;
import com.titobarrios.model.Company;
import com.titobarrios.model.RouteSequence;
import com.titobarrios.view.Console;

public class CreateRouteSeq {
    private RouteSeqCtrl ctrl;
    private Company company;

    public CreateRouteSeq(Company company) {
        this.company = company;
        ctrl = new RouteSeqCtrl(company);
        menu();
    }

    private void menu() {
        Console.log("Menú de creación");
        VType type = ctrl.selectType();
        String name = ctrl.selectName();
        DayOfWeek[] laboralDays = ctrl.selectLaboralDays();
        LocalTime startingTime = ctrl.selectStartingTime();
        int stops = ctrl.selectStopsQuantity();
        int[] intervals = ctrl.selectIntervals(stops);
        new RouteSequence(company, type, name, startingTime, laboralDays, stops, intervals);
        new RSMainMenu(company);
    }
}
