package com.titobarrios.view;

import java.util.Scanner;

public class Console {
	private static Scanner console;

	public Console() {}

	public static void initialize() {
		console = new Scanner(System.in);
	}

	public static String readData() {
		return console.nextLine();
	}

	public static int readNumber() {
		return Integer.parseInt(console.nextLine());
	}

	public static int readOption(boolean showErrorMessage, int limit) {
		int option;
		try {
			option = readNumber();
			if (showErrorMessage && (option == 0 || option > limit)) {
				log("Se ha cancelado la operación\n");
			}
			return option;
		} catch (NumberFormatException ex) {
			log("Digite un número entero e inténtelo de nuevo\n");
			return Integer.MIN_VALUE;
		}
	}

	public static void log(String message) {
		System.out.println(message);
	}
}