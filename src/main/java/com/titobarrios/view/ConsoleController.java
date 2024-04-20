package com.titobarrios.view;

import java.util.Scanner;

public class ConsoleController {
	private Scanner console;

	public ConsoleController() {
		console = new Scanner(System.in);
	}

	public String readData() {
		return console.nextLine();
	}

	public int readNumber() {
		return Integer.parseInt(console.nextLine());
	}

	public void log(String message) {
		System.out.println(message);
	}
}