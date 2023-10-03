package presenter;

import java.time.format.DateTimeParseException;

import model.Calculator;
import model.Vehicle;
import model.VehicleType;
import view.View;

public class Presenter {
	private View view;
	private Calculator calculate;
	// Option se usa en todos los menús y está enlazado entre ellos
	int option;
	
	public Presenter() {
		view = new View();
		calculate = new Calculator();
	}

	public void registerAdmin() {
		calculate.createUser("administrador", "0000");
	}

	public void showBill(int userNumber, int ticketNumber) {
		calculate.checkVehiclesAvailability(VehicleType.AIRPLANE);
		calculate.checkVehiclesAvailability(VehicleType.BUS);
		calculate.checkVehiclesAvailability(VehicleType.SHIP);
		calculate.checkVehiclesAvailability(VehicleType.TRAVEL_BUS);
		view.showCurrentLineMessage("Número de Ticket: "
				+ calculate.getDataCenter().getUsers()[userNumber].getTicketHistory()[ticketNumber].getName() + "\n"
				+ calculate.getDataCenter().getUsers()[userNumber].getTicketHistory()[ticketNumber].getVehicle()
						.getVehicleType().getUpperCaseName()
				+ ": "
				+ calculate.getDataCenter().getUsers()[userNumber].getTicketHistory()[ticketNumber].getVehicle()
						.getPlate()
				+ "\nEmpresa: "
				+ calculate.getDataCenter().getUsers()[userNumber].getTicketHistory()[ticketNumber].getVehicle()
						.getCompany()
				+ "\nPrecio pagado: "
				+ calculate.getDataCenter().getUsers()[userNumber].getTicketHistory()[ticketNumber].getVehicle()
						.getPrice()
				+ "\nEntrada: " + "Ruta "
				+ calculate.getDataCenter().getUsers()[userNumber].getTicketHistory()[ticketNumber].getRoutesNumber()[0]
				+ " " + calculate.getDataCenter().getUsers()[userNumber].getTicketHistory()[ticketNumber].getDates()[0]
				+ "\nSalida: " + "Ruta "
				+ calculate.getDataCenter().getUsers()[userNumber].getTicketHistory()[ticketNumber].getRoutesNumber()[1]
				+ " " + calculate.getDataCenter().getUsers()[userNumber].getTicketHistory()[ticketNumber].getDates()[1]
				+ "\nEstado Actual: ");
		if (calculate.getDataCenter().getUsers()[userNumber].getTicketHistory()[ticketNumber].getAvailability()) {
			view.showMessage("Activo");
		} else if (calculate.getDataCenter().getCurrentDate().isBefore(
				calculate.getDataCenter().getUsers()[userNumber].getTicketHistory()[ticketNumber].getDates()[0])
				|| calculate.getDataCenter().getCurrentDate()
						.isEqual(calculate.getDataCenter().getUsers()[userNumber].getTicketHistory()[ticketNumber]
								.getDates()[0])) {
			view.showMessage("Confirmado");
		} else {
			view.showMessage("Inactivo");
		}
		view.showMessage("");
	}

	public void showCreateTicketMenu(VehicleType type, int userNumber) {
		int vehicleNumber = 0;
		int routeNumberEntry = 0;
		int routeNumberExit = 0;

		Vehicle[] vehicles = calculate.catchVehicles(type);

		do {
			view.showMessage("\nDigite el número del " + type.getName() + " para ver más información");
			view.showMessage("Los " + type.getPluralName() + " disponibles son los siguientes: \n0. Cancelar");
			calculate.checkVehiclesAvailability(type);
			for (int i = 0; i < vehicles.length; i++) {
				if (vehicles[i] != null) {
					if (vehicles[i].getAvailability()) {
						view.showMessage("Bus " + (i + 1) + "   " + vehicles[i].getCompany() + "\n Ticket: "
								+ vehicles[i].getPrice());
					}
				}
			}
			vehicleNumber = 0;
			routeNumberEntry = 0;
			routeNumberExit = 0;
			try {
				vehicleNumber = view.readNumber() - 1;
				calculate.checkVehiclesAvailability(type);
			} catch (NumberFormatException ex) {
				view.showMessage("Escribe un número entero\n");
				break;

			}
			if (vehicleNumber == -1) {
				option = 0;
				break;
			}
			if (vehicles[vehicleNumber] == null) {
				view.showMessage("El " + type.getName() + " seleccionado es inexistente");
				option = 1;
			} else if (!vehicles[vehicleNumber].getAvailability()) {
				view.showMessage("El " + type.getName() + " seleccionado no está disponible");
				option = 1;
			} else {
				for (int i = 0; i < vehicles[vehicleNumber].getRoutes().length; i++) {
					if (vehicles[vehicleNumber].getRoutes()[i] != null) {
						if (vehicles[vehicleNumber].getRoutes()[i].getAvailability()) {
							view.showMessage(
									"\nRuta " + (i + 1) + "\n" + vehicles[vehicleNumber].getRoutes()[i].getName()
											+ "\nEntrada: " + vehicles[vehicleNumber].getRoutes()[i].getStops()[0]
											+ "\nSalida: " + vehicles[vehicleNumber].getRoutes()[i].getStops()[1]);
						}
					}
				}
				view.showMessage("Seleccione el número de ruta en el cual va a ingresar, 0 para cancelar");
				try {
					routeNumberEntry = view.readNumber() - 1;
				} catch (NumberFormatException ex) {
					view.showMessage("Escribe un número entero\n");
					break;
				}
				calculate.checkVehiclesAvailability(type);
				if (routeNumberEntry == -1) {
					option = 0;
				}
				if (vehicles[vehicleNumber].getRoutes()[routeNumberEntry] == null) {
					view.showMessage("La ruta seleccionada no existe");
					option = 1;
				} else if (!vehicles[vehicleNumber].getRoutes()[routeNumberEntry].getAvailability()) {
					view.showMessage("La ruta seleccionada no está disponible");
					option = 1;
				} else {
					view.showMessage("Seleccione el número de ruta en el cual saldrá");
					try {
						routeNumberExit = view.readNumber() - 1;
					} catch (NumberFormatException ex) {
						view.showMessage("Escribe un número entero\n");
						break;
					}
					calculate.checkVehiclesAvailability(type);
					if (routeNumberExit < routeNumberEntry) {
						view.showMessage("La entrada no puede ir después de la salida");
						option = 1;
					} else if (vehicles[vehicleNumber].getRoutes()[routeNumberExit] == null) {
						view.showMessage("La ruta seleccionada es inexistente");
						option = 1;
					} else if (!vehicles[vehicleNumber].getRoutes()[routeNumberExit].getAvailability()) {
						view.showMessage("La ruta seleccionada no está disponible");
						option = 1;
					} else {
						if (calculate.isEnoughMoney(userNumber, type, vehicleNumber)) {
							calculate.createTicket(userNumber, type, vehicleNumber, routeNumberEntry, routeNumberExit);

							view.showMessage("Gracias por usar nuestros servicios!\n");
							int ticketNumber = 0;
							for (int i = 0; i < calculate.getDataCenter().getUsers()[userNumber]
									.getTicketHistory().length; i++) {
								if (calculate.getDataCenter().getUsers()[userNumber].getTicketHistory() == null) {
									ticketNumber = i - 1;
									break;
								}
							}
							showBill(userNumber, ticketNumber);
							option = 0;
						} else {
							view.showMessage("Su saldo no es suficiente para realizar la transacción\n");
							option = 0;
						}
					}
				}
			}
		} while (option != 0);
		option = 1;
	}

	public void showCreateSubscriptionMenu(int userNumber, VehicleType type) {
		int routeNumberEntry;
		int routeNumberExit;
		int vehicleNumber;
		Vehicle[] vehicles = calculate.catchVehicles(type);
		view.showMessage(
				"¿En qué día de la semana deseas agregar a tu horario?\n1. Lunes\n2. Martes\n3. Miércoles\n4. Jueves\n5. Viernes\n6. Sábado\n7. Domingo\n0. Salir");
		try {
			option = view.readNumber();
		} catch (NumberFormatException ex) {
			option = -1;
			view.showMessage("Escribe un número entero\n");
		}
		if (option == 0) {
			option = -1;
			return;
		}
		view.showMessage("Estos son los " + type.getPluralName()
				+ " disponibles para el día de la semana que seleccionaste, digita el número del bus para ver más detalles");
		for (int i = 0; i < vehicles.length; i++) {
			if (vehicles[i] != null) {
				if (vehicles[i].getRoutes()[0].getStops()[0].getDayOfWeek()
						.equals(calculate.getDataCenter().getCurrentDate().getDayOfWeek())) {
					view.showMessage(
							type.getUpperCaseName() + " Nro. " + (i + 1) + "\nTicket: " + vehicles[i].getPrice());
				}
			}
		}
		view.showMessage("0. Salir");
		vehicleNumber = view.readNumber() - 1;
		if (vehicleNumber == -1) {
			option = -1;
			return;
		}
		view.showMessage("Estas son las rutas del " + type.getName());
		for (int i = 0; i < vehicles[vehicleNumber].getRoutes().length; i++) {
			if (vehicles[vehicleNumber].getRoutes()[i] != null) {
				view.showMessage("\nRuta " + (i + 1) + "\n" + vehicles[vehicleNumber].getRoutes()[i].getName()
						+ "\nEntrada: " + vehicles[vehicleNumber].getRoutes()[i].getStops()[0] + "\nSalida: "
						+ vehicles[vehicleNumber].getRoutes()[i].getStops()[1]);
			}
		}
		view.showMessage("0. Salir\nDigite el número de la ruta en la cual entrará");
		routeNumberEntry = view.readNumber() - 1;
		if (routeNumberEntry == -1) {
			option = -1;
			return;
		}
		view.showMessage("Digite el número de la ruta en la cual saldrá");
		routeNumberExit = view.readNumber() - 1;
		if (routeNumberExit == -1) {
			option = -1;
			return;
		}
		calculate.createSubscription(userNumber, option, type, vehicleNumber, routeNumberEntry, routeNumberExit);
		view.showMessage("La suscripción se ha creado correctamente");
	}

	public void showCreateVehicleMenu(VehicleType type) {
		boolean cancelate = true;

		view.showMessage("Digite el nombre de la empresa a la que pertenece el " + type.getName());
		String company = view.readData();
		view.showMessage("Digite la placa del " + type.getName() + "\n0. Salir");
		String plate = view.readData();
		if (plate == "0") {
			view.showMessage("Se ha cancelado la operación");
			return;
		}
		view.showMessage("Digite el precio por ticket del " + type.getName());
		int price = view.readNumber();
		view.showMessage("Digite la capacidad de transporte del " + type.getName());
		int capacity = view.readNumber();
		int routeNumber = 0;
		do {
			view.showMessage(
					"Elija la ruta que desea asignarle al nuevo " + type.getName() + " y ver más detalles\n0. Salir");
			for (int i = 0; i < calculate.getDataCenter().getRoutes().length; i++) {
				if (calculate.getDataCenter().getRoutes()[i] != null) {
					view.showMessage("Ruta " + (i + 1));
				}
			}
			try {
				routeNumber = view.readNumber() - 1;
			} catch (NumberFormatException ex) {
				option = -1;
				view.showMessage("Escribe un número entero, por favor, inténtelo de nuevo\n");
				continue;
			}
			if (routeNumber == -1) {
				cancelate = true;
				break;
			}
			try {
				for (int i = 0; i < calculate.getDataCenter().getRoutes()[routeNumber].length; i++) {
					view.showMessage(calculate.getDataCenter().getRoutes()[routeNumber][i].getName() + "\nInicio: "
							+ calculate.getDataCenter().getRoutes()[routeNumber][i].getStops()[0] + "\nFin: "
							+ calculate.getDataCenter().getRoutes()[routeNumber][i].getStops()[1] + "\n");
				}
			} catch (NullPointerException ex) {
				view.showMessage("La ruta seleccionada es inexistente, por favor, inténtelo de nuevo");
				option = -1;
				continue;
			}
			view.showMessage("Digite 1 para seleccionar esta ruta, 0 para regresar");
			try {
				option = view.readNumber();
			} catch (NumberFormatException ex) {
				option = -1;
				view.showMessage("Escribe un número entero\n");
			}
			calculate.createVehicle(plate, type, company, price, capacity, routeNumber);
			view.showMessage("El " + type.getName() + " se ha creado correctamente\n");
			cancelate = false;
		} while (option != 1);
		if (cancelate) {
			view.showMessage("Se ha cancelado la operación de forma exitosa\n");
		}
	}

	public void showRoutes(VehicleType type) {
		int vehicleNumber;
		Vehicle[] vehicles = calculate.catchVehicles(type);
		String available = "Disponible";
		String unavailable = "No disponible";

		do {
			calculate.checkVehiclesAvailability(type);
			view.showMessage("Seleccione un " + type.getName() + "\n0. Salir");
			for (int i = 0; i < vehicles.length; i++) {
				if (vehicles[i] != null) {
					view.showCurrentLineMessage(type.getUpperCaseName() + ": " + (i + 1) + "\n Ticket: " + vehicles[i].getPrice()
							+ "\n Disponibilidad: ");
					if(vehicles[i].getAvailability()) {
						view.showMessage(available);
					} else {
						view.showMessage(unavailable);
					}
				}
			}
			vehicleNumber = view.readNumber() - 1;
			if (vehicleNumber == -1) {
				option = 0;
				break;
			}
			if (vehicles[vehicleNumber] != null) {
				for (int i = 0; i < vehicles[vehicleNumber].getRoutes().length; i++) {
					if (vehicles[vehicleNumber].getRoutes()[i] != null) {
						view.showCurrentLineMessage("\nRuta " + (i + 1) + "\n" + vehicles[vehicleNumber].getRoutes()[i].getName()
								+ "\n Entrada: " + vehicles[vehicleNumber].getRoutes()[i].getStops()[0] + "\n Salida: "
								+ vehicles[vehicleNumber].getRoutes()[i].getStops()[1] + "\n Disponibilidad: ");
						if(vehicles[vehicleNumber].getRoutes()[i].getAvailability()) {
							view.showMessage(available);
						} else {
							view.showMessage(unavailable);
						}
					}
				}
			} else {
				view.showMessage("El bus seleccionado es inexistente");
			}
			view.showMessage("\n1 Regresar, 0 Salir");
			try {
				option = view.readNumber();
			} catch (NumberFormatException ex) {
				option = -1;
				view.showMessage("Escribe un número entero\n");
			}
		} while (option != 0);
		option = -1;
	}

	public void run() {
		boolean cancelate = false;
		int subscriptionNumber;
		String userName;
		String userPassword;
		int stopsNumber = 0;
		int userNumber;
		int vehicleTypeInt;
		do {
			view.showMessage(
					"Bienvenido a mi sistema de transporte masivo\n\nSeleccione la opción que más le convenga: \n1. Iniciar sesión\n2. Ver rutas\n3. Registrarse\n4. Ingresar como administrador\n0. Salir");
			try {
				option = view.readNumber();
			} catch (NumberFormatException ex) {
				option = -1;
				view.showMessage("Escribe un número entero\n");
			}
			switch (option) {
			case 1:
				view.showMessage("Ingrese su usuario\n0. Cancelar");
				userName = view.readData();
				if (userName.equals("0")) {
					view.showMessage("Se ha cancelado la operación");
					break;
				}
				view.showMessage("Ingrese su contraseña");
				userPassword = view.readData();
				if (calculate.logIn(userName, userPassword)) {
					userNumber = calculate.searchUserArrayNumber(userName, userPassword);
					view.showMessage("Su número de usuario es: " + userNumber);
					calculate.checkSubscriptionPayment(userNumber);
					do {
						view.showMessage(
								"¿Qué desea hacer?\n1. Comprar ticket\n2. Suscripciones\n3. Agregar fondos a mi billetera\n4. Ver historial y estado de mis tickets\n0. Cerrar sesión");
						try {
							option = view.readNumber();
						} catch (NumberFormatException ex) {
							option = -1;
							view.showMessage("Escribe un número entero\n");
						}
						switch (option) {
						case 1:
							view.showMessage("¿Para qué tipo de vehículo desea su reserva?\n1. "
									+ VehicleType.AIRPLANE.getUpperCaseName() + "\n2. " + VehicleType.BUS.getUpperCaseName() + "\n3. "
									+ VehicleType.SHIP.getUpperCaseName() + "\n4. " + VehicleType.TRAVEL_BUS.getUpperCaseName() + "\n0. Salir");
							try {
								vehicleTypeInt = view.readNumber() - 1;
							} catch (NumberFormatException ex) {
								vehicleTypeInt = 0;
								option = -1;
								view.showMessage("Escribe un número entero\n");
								break;
							}
							if (vehicleTypeInt != -1) {
								switch (calculate.convertIntToVehicleType(vehicleTypeInt)) {
								case AIRPLANE:
									showCreateTicketMenu(VehicleType.AIRPLANE, userNumber);
									break;
								case BUS:
									showCreateTicketMenu(VehicleType.BUS, userNumber);
									break;
								case SHIP:
									showCreateTicketMenu(VehicleType.SHIP, userNumber);
									break;
								case TRAVEL_BUS:
									showCreateTicketMenu(VehicleType.TRAVEL_BUS, userNumber);
									break;
								default:
									view.showCurrentLineMessage("Opción inválida");
								}
							} else {
								view.showMessage("La operación se ha cancelado");
								break;
							}
							break;
						case 2:
							view.showMessage(
									"1. Gestionar mis suscripciones\n2. Crear una nueva suscripción\n0. Volver");
							try {
								option = view.readNumber();
							} catch (NumberFormatException ex) {
								option = -1;
								view.showMessage("Escribe un número entero\n");
							}
							if (option == 0) {
								option = 1;
								break;
							}
							switch (option) {
							case 1:
								boolean hasActiveSubscriptions = false;
								for (int i = 0; i < calculate.getDataCenter().getUsers()[userNumber]
										.getSubscription().length; i++) {
									if (calculate.getDataCenter().getUsers()[userNumber].getSubscription()[i] != null) {
										if (!hasActiveSubscriptions) {
											view.showMessage("Estas son tus suscripciones activas: ");
										}
										view.showMessage("Suscripción activa Nro " + (i + 1) + "\nBus: "
												+ (calculate.getDataCenter().getUsers()[userNumber].getSubscription()[i]
														.getVehicleArrayNumber() + 1)
												+ " "
												+ calculate.getDataCenter()
														.getBuses()[calculate.getDataCenter().getUsers()[userNumber]
																.getSubscription()[i].getVehicleArrayNumber()]
														.getPlate()
												+ "\nEmpresa: "
												+ calculate.getDataCenter()
														.getBuses()[calculate.getDataCenter().getUsers()[userNumber]
																.getSubscription()[i].getVehicleArrayNumber()]
														.getCompany()
												+ "\nEntrada: "
												+ calculate.getDataCenter()
														.getBuses()[calculate.getDataCenter().getUsers()[userNumber]
																.getSubscription()[i].getVehicleArrayNumber()]
														.getRoutes()[calculate.getDataCenter().getUsers()[userNumber]
																.getSubscription()[i].getRouteExitArrayNumber()]
														.getName()
												+ "  "
												+ calculate.getDataCenter()
														.getBuses()[calculate.getDataCenter().getUsers()[userNumber]
																.getSubscription()[i].getVehicleArrayNumber()]
														.getRoutes()[calculate.getDataCenter().getUsers()[userNumber]
																.getSubscription()[i]
																.getRouteEntryArrayNumber()]
														.getStops()[0]
												+ "\nSalida: "
												+ calculate.getDataCenter()
														.getBuses()[calculate.getDataCenter().getUsers()[userNumber]
																.getSubscription()[i].getVehicleArrayNumber()]
														.getRoutes()[calculate.getDataCenter().getUsers()[userNumber]
																.getSubscription()[i].getRouteExitArrayNumber()]
														.getName()
												+ "  "
												+ calculate.getDataCenter()
														.getBuses()[calculate.getDataCenter().getUsers()[userNumber]
																.getSubscription()[i].getVehicleArrayNumber()]
														.getRoutes()[calculate.getDataCenter().getUsers()[userNumber]
																.getSubscription()[i].getRouteExitArrayNumber()]
														.getStops()[1]
												+ "\nPrecio: "
												+ calculate.getDataCenter()
														.getBuses()[calculate.getDataCenter().getUsers()[userNumber]
																.getSubscription()[i].getVehicleArrayNumber()]
														.getPrice());
										hasActiveSubscriptions = true;
									}
									if (hasActiveSubscriptions == false) {
										view.showMessage("No tienes suscripciones Activas\n");
										option = -1;
										break;
									}
									view.showMessage(
											"\nDigita el número de una de tus suscripciones activas para cancelarla\n0. Salir");
									try {
										subscriptionNumber = view.readNumber() - 1;
									} catch (NumberFormatException ex) {
										option = -1;
										view.showMessage("Digita un número entero válido");
										break;
									}
									if (subscriptionNumber == -1) {
										option = -1;
										break;
									}
									if (calculate.getDataCenter().getUsers()[userNumber]
											.getSubscription()[subscriptionNumber] != null) {
										view.showMessage("Has seleccionado la suscripción número "
												+ (subscriptionNumber + 1) + "\n1. Cancelar Suscripción\n0. Volver");
										try {
											option = view.readNumber();
										} catch (NumberFormatException ex) {
											view.showMessage("Debes escribir un número entero válido");
											option = -1;
											break;
										}
										if (option == 0) {
											option = -1;
											break;
										}
										if (option == 1) {
											calculate.deleteSubscription(userNumber, subscriptionNumber);
											view.showMessage("La suscripción se ha cancelado correctamente\n");
											break;
										}
									} else {
										option = -1;
										view.showMessage("Has seleccionado una suscripción inválida");
										break;
									}
								}
								break;
							case 2:
								view.showMessage(
										"¿Para qué tipo de vehículo deseas crear tu suscripción?\nSuscríbete y así compramos automáticamente tus tickets\n\n1. "
												+ VehicleType.AIRPLANE.getUpperCaseName() + "\n2. " + VehicleType.BUS.getUpperCaseName()
												+ "\n3. " + VehicleType.SHIP.getUpperCaseName() + "\n4. "
												+ VehicleType.TRAVEL_BUS.getUpperCaseName() + "\n0. Salir");
								try {
									vehicleTypeInt = view.readNumber() - 1;
								} catch (NumberFormatException ex) {
									view.showMessage("Debes escribir un número entero válido, inténtalo de nuevo");
									break;
								}

								if (vehicleTypeInt == -1) {
									view.showMessage("Se ha cancelado la operación");
									break;
								}
								showCreateSubscriptionMenu(userNumber,
										calculate.convertIntToVehicleType(vehicleTypeInt));
								break;
							default:
								view.showMessage("Opción inválida");
								option = -1;
								break;
							}
							break;
						case 3:
							view.showMessage("Su saldo actual es: "
									+ calculate.getDataCenter().getUsers()[userNumber].getWallet()
									+ " pesos\nDigite 1 para agregar fondos, 2 para volver");
							try {
								option = view.readNumber();
							} catch (NumberFormatException ex) {
								option = -1;
								view.showMessage("Escribe un número entero\n");
							}
							if (option == 1) {
								view.showMessage("Cuánto dinero desea agregar? Agregue 0 para cancelar la operación");
								int userAddMoney = view.readNumber();
								if (userAddMoney != 0) {
									calculate.addWallet(userNumber, userAddMoney);
									view.showMessage("Se ha agregado correctamente la cantidad de " + userAddMoney
											+ " pesos\nNuevo saldo disponible: "
											+ calculate.getDataCenter().getUsers()[userNumber].getWallet());
								} else {
									view.showMessage("Se ha cancelado la operación");
								}
							}
							break;
						case 4:
							view.showMessage("Su historial de compras es el siguiente: ");
							for (int i = 0; i < calculate.getDataCenter().getUsers()[userNumber]
									.getTicketHistory().length; i++) {
								if (calculate.getDataCenter().getUsers()[userNumber].getTicketHistory()[i] != null) {
									showBill(userNumber, i);
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
				view.showMessage("¿De cuál vehículo desea consultar las rutas?\n1. " + VehicleType.AIRPLANE.getUpperCaseName()
						+ "\n2. " + VehicleType.BUS.getUpperCaseName() + "\n3. " + VehicleType.SHIP.getUpperCaseName() + "\n4. "
						+ VehicleType.TRAVEL_BUS.getUpperCaseName() + "\n0. Salir");
				try {
					vehicleTypeInt = view.readNumber() - 1;
				} catch (NumberFormatException ex) {
					view.showMessage("Debes escribir un número entero válido");
					option = -1;
					break;
				}

				if (vehicleTypeInt == -1) {
					break;
				}
				showRoutes(calculate.convertIntToVehicleType(vehicleTypeInt));
				break;
			case 3:
				view.showMessage("Para registrarse, ingrese su nuevo usuario\n0. Volver");
				userName = view.readData();
				if (userName.equals("0")) {
					break;
				}
				view.showMessage("Ingrese la contraseña");
				userPassword = view.readData();
				if (calculate.isUserAvailable(userName)) {
					calculate.createUser(userName, userPassword);
					view.showMessage("Se ha registrado correctamente");
				} else {
					view.showMessage("El nombre de usuario digitado ya está en uso");
				}
				break;
			case 4:
				view.showMessage("Ingrese Usuario de administrador\n0. Volver");
				userName = view.readData();
				if (userName.equals("0")) {
					break;
				}
				view.showMessage("Ingrese contraseña de administrador");
				userPassword = view.readData();
				if (calculate.logInAdmin(userName, userPassword)) {
					do {
						view.showMessage(
								"Digite la opción que más le convenga\n1. Crear vehículo\n2. Crear ruta\n0. Cerrar sesión");
						option = view.readNumber();
						switch (option) {
						case 0:
							option = 0;
							break;
						case 1:
							view.showMessage("¿Qué tipo de vehículo desea crear?\n1. " + VehicleType.AIRPLANE.getUpperCaseName()
									+ "\n2. " + VehicleType.BUS.getUpperCaseName() + "\n3. " + VehicleType.SHIP.getUpperCaseName()
									+ "\n4. " + VehicleType.TRAVEL_BUS.getUpperCaseName() + "\n0. Salir");
							try {
								vehicleTypeInt = view.readNumber() - 1;
							} catch (NumberFormatException ex) {
								option = -1;
								view.showMessage("Escribe un número entero, por favor, inténtelo de nuevo\n");
								continue;
							}
							if (vehicleTypeInt == -1) {
								view.showMessage("Se ha cancelado la operación\n");
								break;
							}
							try {
								showCreateVehicleMenu(calculate.convertIntToVehicleType(vehicleTypeInt));
							} catch (NullPointerException ex) {
								option = -1;
								view.showMessage("Opción inválida, se ha cancelado la operación\n");
								break;
							}
							break;
						case 2:
							view.showMessage(
									"Digite los días que estará activa la ruta (1. Lunes, 2. Martes... etc).\n 0. Para terminar\n 8. Para que el bus esté disponible todos los días");
							int[] daysNumber = new int[7];
							cancelate = false;
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
							try {
								stopsNumber = view.readNumber() + 1;
							} catch (NumberFormatException ex) {
								view.showMessage("Escribe un número entero\n");
								break;
							}
							view.showMessage("Digite la duración en minutos");
							int[] durationTime = new int[stopsNumber];
							for (int i = 1; i < durationTime.length; i++) {
								view.showMessage("Duración desde la parada " + i + " hasta la parada " + (i + 1));
								try {
									durationTime[i - 1] = view.readNumber();
								} catch (NumberFormatException ex) {
									view.showMessage("Escribe un número entero e inténtalo de nuevo\n");
									i--;
								}
							}
							try {
								calculate.createRoutes(stopsNumber, initialTime, durationTime, daysNumber);
							} catch (DateTimeParseException ex) {
								view.showMessage("Se ha digitado mal la hora, inténtelo nuevamente");
								option = -1;
								break;
							}
							view.showMessage("La ruta se ha creado correctamente\n");
						}
					} while (option != 0);
					option = 1;
				} else {
					view.showMessage("Usuario o contraseña inválidos\n");
				}
			}
		} while (option != 0);
	}

	public static void main(String[] args) {
		Presenter presenter = new Presenter();

		presenter.registerAdmin();
		presenter.run();
	}
}