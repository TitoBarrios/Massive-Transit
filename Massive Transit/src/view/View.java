package view;

import java.util.Scanner;

public class View {
	private Scanner console;

	public View() {
		console = new Scanner(System.in);
	}

	public String readData() {
		return console.nextLine();
	}

	public int readNumber() {
		return Integer.parseInt(console.nextLine());
	}

	public void showCurrentLineMessage(String message) {
		System.out.print(message);
	}

	public void showMessage(String message) {
		System.out.println(message);
	}
}