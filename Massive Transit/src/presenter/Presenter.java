package presenter;

import model.Calculator;
import view.View;

public class Presenter {
	private View view;
	private Calculator calculate;

	public Presenter() {
		view = new View();
		calculate = new Calculator();
	}

	public void adminRegistration() {
		calculate.UserCreator("administrador", "0000");
	}

	public void run() {
		int option = 0;
		String dateDayMonth;
		String userName;
		String userPassword;
		int userNumber;
		do {
			view.showMessage(
					"Bienvenido a mi sistema de transporte masivo\n\nSeleccione la opción que más le convenga: \n1. Iniciar sesión\n2. Ver rutas\n3. Registrarse\n4. Ingresar como administrador\n0. Salir");
			option = view.readNumber();
			switch (option) {
			case 1:
				view.showMessage("Ingrese su usuario");
				userName = view.readData();
				view.showMessage("Ingrese su contraseña");
				userPassword = view.readData();
				userNumber = calculate.searchUserArrayNumber(userName, userPassword);
				if (calculate.LogIn(userName, userPassword)) {
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
						// agregar booleano de fondos suficientes
						view.showMessage("Gracias por usar nuestros servicios!\nRuta seleccionada: " + ""
								+ "\n Número de ticket: 2023" + "" + "\nSilla asignada: " + "" + "\n");
						break;
					case 2:
						view.showMessage("Se le acreditará automáticamente el monto por servicio");
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
			case 2:
				break;
			case 3:
				view.showMessage("Para registrarse, ingrese su nuevo usuario");
				userName = view.readData();
				view.showMessage("Ingrese la contraseña");
				userPassword = view.readData();
				calculate.UserCreator(userName, userPassword);
				if (calculate.LogIn(userName, userPassword)) {
					view.showMessage("Se ha registrado correctamente");
				} else {
					view.showMessage("El nombre de usuario digitado ya está en uso");
				}
				break;
			case 4:
				view.showMessage("Ingrese Usuario de administrador");
				userName = view.readData();
				view.showMessage("Ingrese contraseña de administrador");
				userPassword = view.readData();
				if (calculate.AdminLogIn(userName, userPassword)) {
					view.showMessage(
							"Digite la opción que más le convenga\n1. Crear bus\n2. Crear ruta\n0. Salir");
					switch (option) {
					case 0:
						option = 0;
						break;
					case 1:
						view.showMessage("Digite la placa del bus");
						String busPlate = view.readData();
						view.showMessage("Digite el precio por ticket del bus");
						int busPrice = view.readNumber();
						int routeArrayNumber;
						do {
							view.showMessage("Elija la ruta que desea asignarle al nuevo bus");
							for (int i = 0; i < calculate.getRoutes().length; i++) {
								if (calculate.getRoutes()[i] != null) {
									view.showMessage("Ruta " + i + 1);
								}
							}
							view.showMessage("Si desea ver los detalles, de una ruta digite el número");
							routeArrayNumber = view.readNumber() - 1;
							for (int i = 0; i < calculate.getRoutes()[option].length; i++) {
								view.showMessage(calculate.getRoutes()[option][i].getName() + "\nInicio: "
										+ calculate.getRoutes()[option][i].getStops()[0] + "\nFin: "
										+ calculate.getRoutes()[option][i].getStops()[1] + "\n");
							}
							view.showMessage("Digite 1 para seleccionar esta ruta, 0 para regresar");
						} while (option != 1);
						calculate.busCreator(busPlate, busPrice, routeArrayNumber);
					case 2:
						view.showMessage(
								"Digite los días que estará activo el bus (1. Lunes, 2. Martes... etc).\n 0. Para terminar\n 8. Para que el bus esté disponible todos los días");
						int[] daysNumber = new int[7];
						for (int i = 0; option != 0 && i < daysNumber.length; i++) {
							option = view.readNumber();
							option = Math.min(option, 8);
							if (option != 0) {
								if (option == 8) {
									calculate.LaboralDaysCreator(new int[] { 8 });
									view.showMessage("La ruta funcionará todos los días");
									break;
								}
								daysNumber[i] = option;
							} else {
								break;
							}
						}
						view.showMessage("Desde qué hora incia la ruta (hh:mm:ss)");
						String initialTime = view.readData();
						view.showMessage("¿Cuántas paradas creará?");
						int stopsNumber = view.readNumber();
						view.showMessage("Digite la duración en minutos");
						int[] durationTime = new int[stopsNumber];
						for (int i = 0; i < durationTime.length; i++) {
							view.showMessage("Duración desde la parada " + i + 1 + " hasta la parada" + i + 2);
							durationTime[i] = view.readNumber();
						}
						calculate.RoutesCreator(stopsNumber, initialTime, durationTime, daysNumber);
					}
				} else {
					view.showMessage("Usuario o contraseña inválidos");
				}
			}
		} while (option != 0);
	}

	public static void main(String[] args) {
		Presenter presenter = new Presenter();

		presenter.adminRegistration();
		presenter.run();
	}
}