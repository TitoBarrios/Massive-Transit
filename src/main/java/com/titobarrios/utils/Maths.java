package com.titobarrios.utils;

public class Maths {

    public int getGreatestBetweenThree(int a, int b, int c) {
		if (a >= b && a >= c) {
			return a;
		}
		if (b >= a && b >= c) {
			return b;
		}
		return c;
	}

    public int calculatePercentage(int percentage, int number){
        return number * (percentage / 100);
    }
}
