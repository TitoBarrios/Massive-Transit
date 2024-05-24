package com.titobarrios.controller;

import java.time.LocalDateTime;

import com.titobarrios.constants.Revenue;

public class RevenueCtrl {
    public static String revenueInfo(int[] revenue, LocalDateTime lastCheck) {
        StringBuilder builder = new StringBuilder();
        builder.append("Informe del ").append(lastCheck).append("\n Ingresos de ").append(lastCheck.getDayOfMonth())
                .append(" de ").append(lastCheck.getMonth()).append(": ").append(revenue[Revenue.DAILY.ordinal()])
                .append("\n Ingresos del mes ").append(lastCheck.getMonthValue()).append(": ")
                .append(revenue[Revenue.MONTHLY.ordinal()]).append("\n Ingresos del ").append(lastCheck.getYear())
                .append(": ").append(revenue[Revenue.YEARLY.ordinal()]).append("\n Ingresos Totales: ")
                .append(revenue[Revenue.GENERAL.ordinal()]);
        return builder.toString();
    }
}
