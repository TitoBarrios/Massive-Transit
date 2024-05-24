package com.titobarrios.utils;

public class Maths {

    public static int getGreatestBetweenThree(int a, int b, int c) {
		if (a >= b && a >= c) {
			return a;
		}
		if (b >= a && b >= c) {
			return b;
		}
		return c;
	}

    public static int calculatePercentage(int percentage, int number){
        return (int) (number * (float) percentage / 100);
    }
}
