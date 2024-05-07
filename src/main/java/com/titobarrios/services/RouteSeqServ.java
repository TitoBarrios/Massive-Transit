package com.titobarrios.services;

import java.time.DayOfWeek;
import java.util.ArrayList;

import com.titobarrios.constants.VType;
import com.titobarrios.model.RouteSequence;

public class RouteSeqServ {
    public static RouteSequence[] filterByLaboralDay(DayOfWeek laboralDay, RouteSequence[] routeSeqs) {
        ArrayList<RouteSequence> filtered = new ArrayList<RouteSequence>();
        for (RouteSequence routeSeq : routeSeqs)
            for (DayOfWeek routeDay : routeSeq.getLaboralDays())
                if (laboralDay.equals(routeDay)) {
                    filtered.add(routeSeq);
                    break;
                }
        return filtered.toArray(RouteSequence[]::new);
    }

    public static RouteSequence[] filterByType(VType filter, RouteSequence[] routeSeqs) {
        ArrayList<RouteSequence> filtered = new ArrayList<RouteSequence>();
        for (RouteSequence routeSeq : routeSeqs)
            if (filter.equals(routeSeq.getType())) {
                filtered.add(routeSeq);
                break;
            }
        return filtered.toArray(RouteSequence[]::new);
    }

    public static RouteSequence[] filterByAvailability(boolean isAvailableFilter, RouteSequence[] routeSeqs) {
        ArrayList<RouteSequence> filtered = new ArrayList<RouteSequence>();
        for (RouteSequence routeSeq : routeSeqs)
            if (isAvailableFilter == routeSeq.isAvailable()) {
                filtered.add(routeSeq);
                break;
            }
        return filtered.toArray(RouteSequence[]::new);
    }
}
