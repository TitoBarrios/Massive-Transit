package com.titobarrios.db;

import java.time.LocalDateTime;

public class CurrentDate {
    private static LocalDateTime currentDate;

    public static void initialize() {
        currentDate = LocalDateTime.now();
    }

    public CurrentDate(){}

    public static void refresh(){
        currentDate = LocalDateTime.now();
    }

    public static LocalDateTime get() {
        return currentDate;
    }

}
