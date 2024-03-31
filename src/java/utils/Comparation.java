package utils;

public class Comparation {
    public int getGreatestBetweenThree(int a, int b, int c) {
		if (a >= b && a >= c) {
			return a;
		}
		if (b >= a && b >= c) {
			return b;
		}
		return c;
	}
}
