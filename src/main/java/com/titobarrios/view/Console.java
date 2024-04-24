package com.titobarrios.view;

import java.util.Scanner;

public class Console {
	private static Scanner console;

	public Console() {
	}

	public static void initialize() {
		console = new Scanner(System.in);
	}

	public static String readData() {
		return console.nextLine();
	}

	public static int readNumber() {
		try {
			return Integer.parseInt(console.nextLine());
		} catch (NumberFormatException e) {
			log("Por favor, digite un número entero");
		}
		return Integer.MIN_VALUE;
	}

	public static void log(String message) {
		System.out.println(message);
	}
}