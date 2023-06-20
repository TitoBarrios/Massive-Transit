package presenter;

import model.Calculator;
import view.View;

public class Presenter {
	private View view;
	private Calculator calculator;

	public Presenter() {
		view = new View();
		calculator = new Calculator();
	}

	public void run() {
		int option = 0;
		String dateDayMonth;

		do {
			view.showMessage(
					"Bienvenido a mi sistema de transporte masivo\n\nSeleccione la opción que más le convenga: \n1. Iniciar sesión\n2. Ver rutas\n3. Registrarse\n0. Salir");
			option = view.readNumber();
			switch (option) {
			case 1:
				view.showMessage("Ingrese su usuario");
				view.readData();
				view.showMessage("Ingrese su contraseña");
				view.readData();
				view.showMessage(
						"¿Qué desea hacer?\n1. Comprar ticket\n2. Crear mi horario\n3. Agregar fondos a mi billetera\n0. Salir");
				option = view.readNumber();
				switch (option) {
				case 1:
					view.showMessage("Seleccione desde dónde tomará el transporte ------");
					option = view.readNumber();
					view.showMessage("Seleccione hasta dónde tomará el recorrido");
					option = view.readNumber();
					view.showMessage("Para que fecha desea el servicio? \nDigite día/mes");
					dateDayMonth = view.readData();
					view.showMessage("Seleccione el autobús que más le convenga según su hora" + "" + "\n0. Salir");
					option = view.readNumber();
					//agregar booleano de fondos suficientes
					view.showMessage("Gracias por usar nuestros servicios!\nRuta seleccionada: " + ""
							+ "\n Número de ticket: 2023" + "" + "\nSilla asignada: " + "" + "\n");
					break;
				case 2: view.showMessage("Se le acreditará automáticamente el monto por servicio");
				view.showMessage("Seleccione desde dónde tomará el transporte ------");
				option = view.readNumber();
				view.showMessage("Seleccione hasta dónde tomará el recorrido");
				option = view.readNumber();
				view.showMessage("Para que fecha desea el servicio? \nDigite día/mes");
				dateDayMonth = view.readData();
				view.showMessage("Seleccione el autobús que más le convenga según su hora" + "" + "\n0. Salir");
				option = view.readNumber();
				view.showMessage("El servicio ha sido agregado a su horario correctamente!");
				view.showMessage("Digite 1 para agregar otro servicio, 0 salir");
				option = view.readNumber();
				}
			}
		} while (option != 0);
	}

	public static void main(String[] args) {
		Presenter presenter = new Presenter();

		presenter.run();
	}
}