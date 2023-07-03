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
		String dateDay;
		String dateMonth;
		String userName;
		String userPassword;
		int userNumber;
		int busNumber;
		do {
			view.showMessage(
					"Bienvenido a mi sistema de transporte masivo\n\nSeleccione la opción que más le convenga: \n1. Iniciar sesión\n2. Ver rutas\n3. Registrarse\n4. Ingresar como administrador\n0. Salir");
			option = view.readNumber();
			switch (option) {
			case 1:
				view.showMessage("Ingrese su usuario\n0. Cancelar");
				userName = view.readData();
				if(userName.equals("0")) {
					view.showMessage("Se ha cancelado la operación");
					break;
				}
				view.showMessage("Ingrese su contraseña");
				userPassword = view.readData();
				if (calculate.LogIn(userName, userPassword)) {
					userNumber = calculate.searchUserArrayNumber(userName, userPassword);
					view.showMessage("Su número de usuario es: " + userNumber);
					do {
						view.showMessage(
								"¿Qué desea hacer?\n1. Comprar ticket\n2. Crear mi horario(No Disponible)\n3. Agregar fondos a mi billetera\n4. Ver historial y estado de mis tickets\n0. Cerrar sesión");
						option = view.readNumber();
						switch (option) {
						case 1:
							do {
								view.showMessage("\nDigite el número del bus para ver más información");
								view.showMessage("Los buses disponibles son los siguientes: \n0. Cancelar");
								calculate.busDisponibilityChecker();
								for (int i = 0; i < calculate.getBuses().length; i++) {
									if (calculate.getBuses()[i] != null) {
										if (calculate.getBuses()[i].getDisponiblility()) {
											view.showMessage("Bus " + (i + 1) + "\n Ticket: "
													+ calculate.getBuses()[i].getPrice());
										}
									}
								}
								busNumber = 0;
								int routeNumberEntry = 0;
								int routeNumberExit = 0;
								busNumber = view.readNumber() - 1;
								if (busNumber == -1) {
									option = 0;
									break;
								}
								if (calculate.getBuses()[busNumber] == null) {
									view.showMessage("El bus seleccionado es inexistente");
									option = 1;
								} else if (!calculate.getBuses()[busNumber].getDisponiblility()) {
									view.showMessage("El bus seleccionado no está disponible");
									option = 1;
								} else {
									for (int i = 0; i < calculate.getBuses()[busNumber].getRoutes().length; i++) {
										if (calculate.getBuses()[busNumber].getRoutes()[i] != null) {
											if (calculate.getBuses()[busNumber].getRoutes()[i].getDisponibility()) {
												view.showMessage("\nRuta " + (i + 1) + "\n"
														+ calculate.getBuses()[busNumber].getRoutes()[i].getName()
														+ "\nEntrada: "
														+ calculate.getBuses()[busNumber].getRoutes()[i].getStops()[0]
														+ "\nSalida: "
														+ calculate.getBuses()[busNumber].getRoutes()[i].getStops()[1]);
											}
										}
									}
									view.showMessage(
											"Seleccione el número de ruta en el cual va a ingresar, 0 para cancelar");
									routeNumberEntry = view.readNumber() - 1;
									if (routeNumberEntry == -1) {
										option = 0;
									}
									if (calculate.getBuses()[busNumber].getRoutes()[routeNumberEntry] == null) {
										view.showMessage("La ruta seleccionada no existe");
										option = 1;
									} else if (!calculate.getBuses()[busNumber].getRoutes()[routeNumberEntry]
											.getDisponibility()) {
										view.showMessage("La ruta seleccionada no está disponible");
										option = 1;
									} else {
										view.showMessage("Seleccione el número de ruta en el cual saldrá");
										routeNumberExit = view.readNumber() - 1;
										if (routeNumberExit < routeNumberEntry) {
											view.showMessage("La entrada no puede ir después de la salida");
											option = 1;
										} else if (calculate.getBuses()[busNumber]
												.getRoutes()[routeNumberExit] == null) {
											view.showMessage("La ruta seleccionada es inexistente");
											option = 1;
										} else if (!calculate.getBuses()[busNumber].getRoutes()[routeNumberExit]
												.getDisponibility()) {
											view.showMessage("La ruta seleccionada no está disponible");
											option = 1;
										} else {
											if (calculate.enoughMoney(userNumber, busNumber)) {
												calculate.ticketCreator(userNumber, busNumber, routeNumberEntry,
														routeNumberExit);
												view.showMessage("Gracias por usar nuestros servicios!\nBus: "
														+ (busNumber + 1) + "\nEntrada: " + "Ruta " + routeNumberEntry
														+ " "
														+ calculate.getBuses()[busNumber].getRoutes()[routeNumberEntry]
																.getStops()[0]
														+ "\nSalida: " + "Ruta " + routeNumberExit + " "
														+ calculate.getBuses()[busNumber].getRoutes()[routeNumberExit]
																.getStops()[1]
														+ "\nNúmero de ticket: "
														+ calculate.getUsers()[userNumber].getTicketHistory()[calculate
																.getLastUserTicketNumber(userNumber)].getName()
														+ "\nValor Pagado: "
														+ calculate.getBuses()[busNumber].getPrice() + "\n");
												option = 0;
											} else {
												view.showMessage(
														"Su saldo no es suficiente para realizar la transacción\n");
												option = 0;
											}
										}
									}
								}
							} while (option != 0);
							option = 1;
							break;
						case 2:
							view.showMessage("Esta sección aún no está disponible\n");
							break;
						case 3:
							view.showMessage("Su saldo actual es: " + calculate.getUsers()[userNumber].getWallet()
									+ " pesos\nDigite 1 para agregar fondos, 2 para volver");
							option = view.readNumber();
							if (option == 1) {
								view.showMessage("Cuánto dinero desea agregar? Agregue 0 para cancelar la operación");
								int userAddMoney = view.readNumber();
								if (userAddMoney != 0) {
									calculate.addWallet(userNumber, userAddMoney);
									view.showMessage("Se ha agrega correctamente la cantidad de " + userAddMoney
											+ " pesos\nNuevo saldo disponible: "
											+ calculate.getUsers()[userNumber].getWallet());
								} else {
									view.showMessage("Se ha cancelado la operación");
								}
							}
							break;
						case 4:
							view.showMessage("Su historial de compras es el siguiente: ");
							for (int i = 0; i < calculate.getUsers()[userNumber].getTicketHistory().length; i++) {
								if (calculate.getUsers()[userNumber].getTicketHistory()[i] != null) {
									view.showCurrentLineMessage("Número de Ticket: "
											+ calculate.getUsers()[userNumber].getTicketHistory()[i].getName()
											+ "\nBus: "
											+ calculate.getUsers()[userNumber].getTicketHistory()[i].getBus().getPlate()
											+ "\nPrecio pagado: "
											+ calculate.getUsers()[userNumber].getTicketHistory()[i].getBus().getPrice()
											+ "\nEntrada: " + "Ruta "
											+ calculate.getUsers()[userNumber].getTicketHistory()[i]
													.getRoutesNumber()[0]
											+ " " + calculate.getUsers()[userNumber].getTicketHistory()[i].getDates()[0]
											+ "\nSalida: " + "Ruta "
											+ calculate.getUsers()[userNumber].getTicketHistory()[i]
													.getRoutesNumber()[1]
											+ " " + calculate.getUsers()[userNumber].getTicketHistory()[i].getDates()[1]
											+ "\nEstado Actual: ");
									if (calculate.getUsers()[userNumber].getTicketHistory()[i].getDisponibility()) {
										view.showMessage("Activo");
									} else if (calculate.getCurrentDate().isBefore(
											calculate.getUsers()[userNumber].getTicketHistory()[i].getDates()[0])
											|| calculate.getCurrentDate()
													.isEqual(calculate.getUsers()[userNumber].getTicketHistory()[i]
															.getDates()[0])) {
										view.showMessage("Confirmado");
									} else {
										view.showMessage("Inactivo");
									}
									view.showMessage("");
								}
							}
							break;
						}
					} while (option != 0);
					option = 1;
				} else {
					view.showMessage("Ha ocurrido un error al intentar iniciar sesión");
				}
				break;
			case 2:
				do {
					view.showMessage("Seleccione un bus\n0. Salir");
					for (int i = 0; i < calculate.getBuses().length; i++) {
						if (calculate.getBuses()[i] != null) {
							view.showMessage("Bus " + (i + 1) + "\n Ticket: " + calculate.getBuses()[i].getPrice());
						}
					}
					busNumber = view.readNumber() - 1;
					if (busNumber == -1) {
						option = 0;
						break;
					}
					if (calculate.getBuses()[busNumber] != null) {
						for (int i = 0; i < calculate.getBuses()[busNumber].getRoutes().length; i++) {
							if (calculate.getBuses()[busNumber].getRoutes()[i] != null) {
								view.showMessage("\nRuta " + (i + 1) + "\n"
										+ calculate.getBuses()[busNumber].getRoutes()[i].getName() + "\nEntrada: "
										+ calculate.getBuses()[busNumber].getRoutes()[i].getStops()[0] + "\nSalida: "
										+ calculate.getBuses()[busNumber].getRoutes()[i].getStops()[1]);
							}
						}
					} else {
						view.showMessage("El bus seleccionado es inexistente");
					}
					view.showMessage("\n1 Regresar, 0 Salir");
					option = view.readNumber();
				} while (option != 0);
				option = 1;
				break;
			case 3:
				view.showMessage("Para registrarse, ingrese su nuevo usuario\n0. Volver");
				userName = view.readData();
				if(userName.equals("0")) {
					break;
				}
				view.showMessage("Ingrese la contraseña");
				userPassword = view.readData();
				if (calculate.UserAvailability(userName)) {
					calculate.UserCreator(userName, userPassword);
					view.showMessage("Se ha registrado correctamente");
				} else {
					view.showMessage("El nombre de usuario digitado ya está en uso");
				}
				break;
			case 4:
				view.showMessage("Ingrese Usuario de administrador\n0. Volver");
				userName = view.readData();
				if(userName.equals("0")) {
					break;
				}
				view.showMessage("Ingrese contraseña de administrador");
				userPassword = view.readData();
				if (calculate.AdminLogIn(userName, userPassword)) {
					do {
						view.showMessage(
								"Digite la opción que más le convenga\n1. Crear bus\n2. Crear ruta\n0. Cerrar sesión");
						option = view.readNumber();
						switch (option) {
						case 0:
							option = 0;
							break;
						case 1:
							view.showMessage("Digite la placa del bus");
							String busPlate = view.readData();
							if (busPlate == "0") {
								view.showMessage("Se ha cancelado la operación");
								break;
							}
							view.showMessage("Digite el precio por ticket del bus");
							int busPrice = view.readNumber();
							view.showMessage("Digite la capacidad de transporte del bus");
							int busCapacity = view.readNumber();
							int routeArrayNumber;
							do {
								view.showMessage("Elija la ruta que desea asignarle al nuevo bus");
								for (int i = 0; i < calculate.getRoutes().length; i++) {
									if (calculate.getRoutes()[i] != null) {
										view.showMessage("Ruta " + (i + 1));
									}
								}
								view.showMessage("Si desea ver los detalles, de una ruta digite el número");
								routeArrayNumber = view.readNumber() - 1;
								for (int i = 0; i < calculate.getRoutes()[routeArrayNumber].length; i++) {
									view.showMessage(calculate.getRoutes()[routeArrayNumber][i].getName() + "\nInicio: "
											+ calculate.getRoutes()[routeArrayNumber][i].getStops()[0] + "\nFin: "
											+ calculate.getRoutes()[routeArrayNumber][i].getStops()[1] + "\n");
								}
								view.showMessage("Digite 1 para seleccionar esta ruta, 0 para regresar");
								option = view.readNumber();
							} while (option != 1);
							calculate.busCreator(busPlate, busPrice, busCapacity, routeArrayNumber);
							view.showMessage("Se ha creado el bus correctamente");
							break;
						case 2:
							view.showMessage(
									"Digite los días que estará activa la ruta (1. Lunes, 2. Martes... etc).\n 0. Para terminar\n 8. Para que el bus esté disponible todos los días");
							int[] daysNumber = new int[7];
							boolean cancelate = false;
							for (int i = 0; option != 0 && i < daysNumber.length; i++) {
								option = view.readNumber();
								option = Math.min(option, 8);
								if (option != 0) {
									if (option == 8) {
										daysNumber[i] = option;
										view.showMessage("La ruta funcionará todos los días");
										break;
									}
									daysNumber[i] = option;
								} else if (i == 0) {
									cancelate = true;
									break;
								} else {
									break;
								}
							}
							if (cancelate) {
								view.showMessage("Se ha cancelado la operación");
								option = 1;
								break;
							}
							option = 1;
							view.showMessage("Desde qué hora inicia la ruta (hh:mm:ss)");
							String initialTime = view.readData();
							view.showMessage("¿Cuántas paradas creará?");
							int stopsNumber = view.readNumber() + 1;
							view.showMessage("Digite la duración en minutos");
							int[] durationTime = new int[stopsNumber];
							for (int i = 1; i < durationTime.length; i++) {
								view.showMessage("Duración desde la parada " + i + " hasta la parada " + (i + 1));
								durationTime[i - 1] = view.readNumber();
							}
							calculate.RoutesCreator(stopsNumber, initialTime, durationTime, daysNumber);
							calculate.busDisponibilityChecker();
						}
					} while (option != 0);
					option = 1;
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