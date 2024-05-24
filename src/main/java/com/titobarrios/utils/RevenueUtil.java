package com.titobarrios.utils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

import com.titobarrios.constants.Revenue;
import com.titobarrios.db.Archive;
import com.titobarrios.db.CurrentDate;
import com.titobarrios.model.Company;

public class RevenueUtil {
    public static void refreshRevenue(int[] revenue, LocalDateTime lastCheck) {
        if (CurrentDate.get().getYear() != lastCheck.getYear())
            revenue[Revenue.YEARLY.ordinal()] = 0;
        if (CurrentDate.get().getMonth() != lastCheck.getMonth())
            revenue[Revenue.MONTHLY.ordinal()] = 0;
        if (CurrentDate.get().getDayOfMonth() != lastCheck.getDayOfMonth())
            revenue[Revenue.DAILY.ordinal()] = 0;
        lastCheck = CurrentDate.get();
    }

    public static int[] appRevenue() {
        int[] revenue = new int[4];
        for (Company company : Arrays.stream(Archive.getAccounts()).filter(account -> account instanceof Company)
                .map(account -> (Company) account).collect(Collectors.toList()).toArray(Company[]::new))
            for(int i = 0; i < revenue.length; i++)
                revenue[i] += company.getRevenue()[i];
        return revenue;
    }
}
