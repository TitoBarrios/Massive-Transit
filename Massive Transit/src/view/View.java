package view;

import java.util.Scanner;

public class View {
	private Scanner console;

	public View() {
		console = new Scanner(System.in);
	}

	public void showMessage(String message) {
		System.out.println(message);
	}

	public String readData() {
		String data;
		data = console.nextLine();
		return data;
	}

	public int readNumber() {
		int number;
		number = Integer.parseInt(console.nextLine());
		return number;
	}
}