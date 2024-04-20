package com.titobarrios.services;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class LaboralDays {

    public static LocalDateTime adjustTime(DayOfWeek[] laboralDays, LocalDateTime time) {
		while (!isDayForWork(laboralDays, time))
			time = time.plusDays(1);
		return time;
    }

    public static DayOfWeek[] fromInt(int[] numbers) {
        DayOfWeek[] laboralDays = new DayOfWeek[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            laboralDays[i] = fromInt(numbers[i]);
            if (numbers[i] == 8) {
                laboralDays = new DayOfWeek[7];
                for(int j = 1; j <= 7; j++)
                    laboralDays[j] = fromInt(j);
                return laboralDays;
            }
        }
        return laboralDays;
    }

    public static DayOfWeek fromInt(int number) {
        switch (number) {
            case 1:
                return DayOfWeek.MONDAY;
            case 2:
                return DayOfWeek.TUESDAY;
            case 3:
                return DayOfWeek.WEDNESDAY;
            case 4:
                return DayOfWeek.THURSDAY;
            case 5:
                return DayOfWeek.FRIDAY;
            case 6:
                return DayOfWeek.SATURDAY;
            case 7:
                return DayOfWeek.SUNDAY;
            default:
                return null;
        }
    }

    public static boolean isDayForWork(DayOfWeek[] laboralDays, LocalDateTime time) {
		for (DayOfWeek laboralDay : laboralDays)
			if (time.getDayOfWeek().equals(laboralDay))
				return true;
		return false;
	}
}
