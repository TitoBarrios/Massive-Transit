package com.titobarrios.services;

import com.titobarrios.constants.VType;

public class Converter {
    public static VType fromInt(int number) {
        switch (number) {
            case 0:
                return VType.AIRPLANE;
            case 1:
                return VType.BUS;
            case 2:
                return VType.SHIP;
            case 3:
                return VType.TRAVEL_BUS;
        }
        return null;
    }
}
