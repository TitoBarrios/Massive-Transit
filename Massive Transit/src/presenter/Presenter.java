package presenter;

import java.time.format.DateTimeParseException;

import model.*;
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

	public void defaultValues() {
		calculate.createUser(Value.USER, "administrador", "0000");
		calculate.createUser(Value.COMPANY, "default", "0000");
		int number = calculate.searchUserArrayNumber(Value.COMPANY, "default");
		calculate.createRouteSeq((Company) calculate.getDataCenter().getUsers()[Value.COMPANY.getValue()][number],
				"Ruta 1", "20:30:00", calculate.readLaboralDays(new int[] { 8 }), 6, new int[] { 10, 15, 30, 40, 15 });
		calculate.createVehicle(VehicleType.BUS,
				(Company) calculate.getDataCenter().getUsers()[Value.COMPANY.getValue()][number], "default",
				calculate.getDataCenter().getRouteSeqs()[0], 3500, 30);
	}

	public void showBill(User user, Ticket ticket) {
		view.showCurrentLineMessage("Número de Ticket: " + ticket.getName() + "\n"
				+ ticket.getVehicle().getVehicleType().getUpperCaseName() + ": " + ticket.getVehicle().getPlate()
				+ "\nEmpresa: " + ticket.getVehicle().getCompany().getName() + "\nPrecio pagado: "
				+ ticket.getVehicle().getPrice() + "\nEntrada: " + "Ruta "
				+ ticket.getRoutes()[Value.ENTRY.getValue()].getStopsName()[Value.ENTRY.getValue()] + " "
				+ ticket.getRoutes()[Value.ENTRY.getValue()].getStops()[Value.ENTRY.getValue()] + "\nSalida: " + "Ruta "
				+ ticket.getRoutes()[Value.EXIT.getValue()].getStopsName()[Value.EXIT.getValue()] + " "
				+ ticket.getRoutes()[Value.EXIT.getValue()].getStops()[Value.EXIT.getValue()] + "\nEstado Actual: ");
		if (ticket.getAvailability()) {
			view.showMessage("Activo");
		} else if (calculate.getDataCenter().getCurrentDate()
				.isBefore(ticket.getRoutes()[Value.ENTRY.getValue()].getStops()[Value.ENTRY.getValue()])
				|| calculate.getDataCenter().getCurrentDate()
						.isEqual(ticket.getRoutes()[Value.ENTRY.getValue()].getStops()[Value.ENTRY.getValue()])) {
			view.showMessage("Confirmado");
		} else {
			view.showMessage("Inactivo");
		}
		if (!ticket.getOwner().getName().equals(ticket.getBuyer().getName())) {
			view.showMessage("Comprador: " + ticket.getBuyer().getName());
		}
	}

	public void showCreateTicketMenu(User user, User userWallet) {
		int vehicleNumber = 0;
		int vehicleTypeInt = 0;
		int routeNumberEntry = 0;
		int routeNumberExit = 0;
		view.showMessage("1. " + VehicleType.AIRPLANE.getUpperCaseName() + "\n2. " + VehicleType.BUS.getUpperCaseName()
				+ "\n3. " + VehicleType.SHIP.getUpperCaseName() + "\n4. " + VehicleType.TRAVEL_BUS.getUpperCaseName()
				+ "\n0. Salir");
		try {
			vehicleTypeInt = view.readNumber() - 1;
		} catch (NumberFormatException ex) {
			vehicleTypeInt = 0;
			option = -1;
			view.showMessage("Digite un número entero e intételo de nuevo\n");
			return;
		}

		if (vehicleTypeInt == -1 || vehicleTypeInt > 3) {
			view.showMessage("La operación se ha cancelado");
			option = -1;
			return;
		}
		VehicleType type = calculate.convertIntToVehicleType(vehicleTypeInt);

		Vehicle[] vehicles = calculate.getDataCenter().getVehicles()[type.getValue()];

		do {
			calculate.checkVehiclesAvailability(vehicles);
			view.showMessage("\nDigite el número del " + type.getName() + " para ver más información");
			view.showMessage("Los " + type.getPluralName() + " disponibles son los siguientes: \n0. Cancelar");
			calculate.checkVehiclesAvailability(calculate.getDataCenter().getVehicles()[type.getValue()]);
			for (int i = 0; i < vehicles.length; i++) {
				if (vehicles[i] != null) {
					if (vehicles[i].getAvailability()) {
						view.showMessage(type.getUpperCaseName() + " " + (i + 1) + "   "
								+ vehicles[i].getCompany().getName() + "\n Ticket: " + vehicles[i].getPrice());
					}
				}
			}
			vehicleNumber = 0;
			routeNumberEntry = 0;
			routeNumberExit = 0;
			try {
				vehicleNumber = view.readNumber() - 1;
			} catch (NumberFormatException ex) {
				view.showMessage("Digite un número entero e intételo de nuevo\n");
				break;
			}
			if (vehicleNumber == -1 || vehicleNumber >= vehicles.length) {
				option = 0;
				break;
			}
			calculate.checkVehicleAvailability(vehicles[vehicleNumber]);
			if (vehicles[vehicleNumber] == null) {
				view.showMessage("El " + type.getName() + " seleccionado es inexistente");
				option = 1;
			} else if (!vehicles[vehicleNumber].getAvailability()) {
				view.showMessage("El " + type.getName() + " seleccionado no está disponible");
				option = 1;
			} else {
				for (int i = 0; i < vehicles[vehicleNumber].getRouteSeq().getRoutes().length; i++) {
					if (vehicles[vehicleNumber].getRouteSeq().getRoutes()[i] != null) {
						if (vehicles[vehicleNumber].getRouteSeq().getRoutes()[i].getAvailability()) {
							view.showMessage("\nRuta " + (i + 1) + "\n"
									+ vehicles[vehicleNumber].getRouteSeq().getRoutes()[i].getName() + "\nEntrada: "
									+ vehicles[vehicleNumber].getRouteSeq().getRoutes()[i].getStops()[Value.ENTRY
											.getValue()]
									+ "\nSalida: " + vehicles[vehicleNumber].getRouteSeq().getRoutes()[i]
											.getStops()[Value.EXIT.getValue()]);
						}
					}
				}
				view.showMessage("Seleccione el número de ruta en el cual va a ingresar, 0 para cancelar");
				try {
					routeNumberEntry = view.readNumber() - 1;
				} catch (NumberFormatException ex) {
					view.showMessage("Digite un número entero e intételo de nuevo\n");
					break;
				}
				if (routeNumberEntry == -1) {
					view.showMessage("Se ha cancelado la operación\n");
					option = -1;
					break;
				}
				if (routeNumberEntry >= vehicles[vehicleNumber].getRouteSeq().getRoutes().length) {
					view.showMessage("La ruta seleccionada no existe");
					option = -1;
					break;
				}

				calculate.checkRouteSequenceAvailability(vehicles[vehicleNumber].getRouteSeq());
				if (vehicles[vehicleNumber].getRouteSeq().getRoutes()[routeNumberEntry] == null) {
					view.showMessage("La ruta seleccionada no existe");
					option = -1;
				} else if (!vehicles[vehicleNumber].getRouteSeq().getRoutes()[routeNumberEntry].getAvailability()) {
					view.showMessage("La ruta seleccionada no está disponible");
					option = -1;
				} else {
					view.showMessage("Seleccione el número de ruta en el cual saldrá");
					try {
						routeNumberExit = view.readNumber() - 1;
					} catch (NumberFormatException ex) {
						view.showMessage("Digite un número entero e intételo de nuevo\n");
						break;
					}
					if (routeNumberExit >= vehicles[vehicleNumber].getRouteSeq().getRoutes().length) {
						view.showMessage("La ruta seleccionada no existe");
						option = -1;
						break;
					}
					calculate.checkRouteSequenceAvailability(vehicles[vehicleNumber].getRouteSeq());
					if (routeNumberExit < routeNumberEntry) {
						view.showMessage("La entrada no puede ir después de la salida");
						option = 1;
					} else if (vehicles[vehicleNumber].getRouteSeq().getRoutes()[routeNumberExit] == null) {
						view.showMessage("La ruta seleccionada es inexistente");
						option = 1;
					} else if (!vehicles[vehicleNumber].getRouteSeq().getRoutes()[routeNumberExit].getAvailability()) {
						view.showMessage("La ruta seleccionada no está disponible");
						option = 1;
					} else {
						if (calculate.isEnoughMoney(userWallet, vehicles[vehicleNumber])) {
							calculate.createTicket(user, userWallet, vehicles[vehicleNumber],
									new Route[] { vehicles[vehicleNumber].getRouteSeq().getRoutes()[routeNumberEntry],
											vehicles[vehicleNumber].getRouteSeq().getRoutes()[routeNumberExit] });
							view.showMessage("Gracias por usar nuestros servicios!\n");
							int ticketNumber = 0;
							for (int i = 0; i < user.getTicketHistory().length; i++) {
								if (user.getTicketHistory()[i] == null) {
									ticketNumber = i - 1;
									break;
								}
							}
							showBill(user, user.getTicketHistory()[ticketNumber]);
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

	public void showCreateSubscriptionMenu(User user, VehicleType type) {
		int routeNumberEntry;
		int routeNumberExit;
		int vehicleNumber;
		Vehicle[] vehicles = calculate.getDataCenter().getVehicles()[type.getValue()];
		view.showMessage(
				"¿En qué día de la semana deseas agregar a tu horario?\n1. Lunes\n2. Martes\n3. Miércoles\n4. Jueves\n5. Viernes\n6. Sábado\n7. Domingo\n0. Salir");
		try {
			option = view.readNumber();
		} catch (NumberFormatException ex) {
			option = -1;
			view.showMessage("Digite un número entero e intételo de nuevo\n");
		}
		if (option == 0) {
			option = -1;
			return;
		}
		view.showMessage("Estos son los " + type.getPluralName()
				+ " disponibles para el día de la semana que seleccionaste, digita el número del bus para ver más detalles");
		for (int i = 0; i < vehicles.length; i++) {
			if (vehicles[i] != null) {
				if (vehicles[i].getRouteSeq().getRoutes()[0].getStops()[0].getDayOfWeek()
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
		for (int i = 0; i < vehicles[vehicleNumber].getRouteSeq().getRoutes().length; i++) {
			if (vehicles[vehicleNumber].getRouteSeq().getRoutes()[i] != null) {
				view.showMessage(
						"\nRuta " + (i + 1) + "\n" + vehicles[vehicleNumber].getRouteSeq().getRoutes()[i].getName()
								+ "\nEntrada: " + vehicles[vehicleNumber].getRouteSeq().getRoutes()[i].getStops()[0]
								+ "\nSalida: " + vehicles[vehicleNumber].getRouteSeq().getRoutes()[i].getStops()[1]);
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
		Vehicle vehicle = calculate.getDataCenter().getVehicles()[type.getValue()][vehicleNumber];
		calculate.createSubscription(user, calculate.readLaboralDays(new int[] { option })[0], vehicle,
				new Route[] { vehicle.getRouteSeq().getRoutes()[routeNumberEntry],
						vehicle.getRouteSeq().getRoutes()[routeNumberExit] });
		view.showMessage("La suscripción se ha creado correctamente");
	}

	public void showCreateVehicleMenu(VehicleType type, Company company) {
		boolean cancelate = true;

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
			for (int i = 0; i < company.getRouteSeqs().length; i++) {
				if (company.getRouteSeqs()[i] != null) {
					view.showMessage("Ruta " + (i + 1) + ". " + company.getRouteSeqs()[i].getName());
				}
			}
			try {
				routeNumber = view.readNumber() - 1;
			} catch (NumberFormatException ex) {
				option = -1;
				view.showMessage("Digite un número entero e intételo de nuevo\n");
				continue;
			}
			if (routeNumber == -1) {
				cancelate = true;
				break;
			}
			try {
				for (int i = 0; i < company.getRouteSeqs()[routeNumber].getRoutes().length; i++) {
					view.showMessage(company.getRouteSeqs()[routeNumber].getRoutes()[i].getName() + "\nInicio: "
							+ company.getRouteSeqs()[routeNumber].getRoutes()[i].getStops()[Value.ENTRY.getValue()]
							+ "\nFin: "
							+ company.getRouteSeqs()[routeNumber].getRoutes()[i].getStops()[Value.EXIT.getValue()]
							+ "\n");
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
				view.showMessage("Digite un número entero e intételo de nuevo\n");
			}
			calculate.createVehicle(type, company, plate, calculate.getDataCenter().getRouteSeqs()[routeNumber], price,
					capacity);
			view.showMessage("El " + type.getName() + " se ha creado correctamente\n");
			cancelate = false;
		} while (option != 1);
		if (cancelate) {
			view.showMessage("Se ha cancelado la operación de forma exitosa\n");
		}
	}

	public void showRoutes(VehicleType type) {
		int vehicleNumber;
		Vehicle[] vehicles = calculate.getDataCenter().getVehicles()[type.getValue()];
		String available = "Disponible";
		String unavailable = "No disponible";

		do {
			calculate.checkVehiclesAvailability(vehicles);
			view.showMessage("Seleccione un " + type.getName() + "\n0. Salir");
			for (int i = 0; i < vehicles.length; i++) {
				if (vehicles[i] != null) {
					view.showCurrentLineMessage(type.getUpperCaseName() + ": " + (i + 1) + "\n Ticket: "
							+ vehicles[i].getPrice() + "\n Disponibilidad: ");
					if (vehicles[i].getAvailability()) {
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
				for (int i = 0; i < vehicles[vehicleNumber].getRouteSeq().getRoutes().length; i++) {
					if (vehicles[vehicleNumber].getRouteSeq().getRoutes()[i] != null) {
						view.showCurrentLineMessage("\nRuta " + (i + 1) + "\n"
								+ vehicles[vehicleNumber].getRouteSeq().getRoutes()[i].getName() + "\n Entrada: "
								+ vehicles[vehicleNumber].getRouteSeq().getRoutes()[i].getStops()[Value.ENTRY
										.getValue()]
								+ "\n Salida: "
								+ vehicles[vehicleNumber].getRouteSeq().getRoutes()[i].getStops()[Value.EXIT.getValue()]
								+ "\n Disponibilidad: ");
						if (vehicles[vehicleNumber].getRouteSeq().getRoutes()[i].getAvailability()) {
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
				view.showMessage("Digite un número entero e intételo de nuevo\n");
			}
		} while (option != 0);
		option = -1;
	}

	public void showStatistics(Company company, Value value) {
		int totalRevenue = 0;
		int yearlyRevenue = 0;
		int monthlyRevenue = 0;
		int dailyRevenue = 0;

		for (Vehicle[] vehicles : company.getVehicles()) {
			for (Vehicle vehicle : vehicles) {
				if (vehicle != null) {
					calculate.checkVehicleRevenue(vehicle, Value.GENERAL);
					totalRevenue += vehicle.getRevenue()[Value.GENERAL.getValue()][Value.REVENUE.getValue()];
					yearlyRevenue += vehicle.getRevenue()[Value.YEARLY.getValue()][Value.REVENUE.getValue()];
					monthlyRevenue += vehicle.getRevenue()[Value.MONTHLY.getValue()][Value.REVENUE.getValue()];
					dailyRevenue += vehicle.getRevenue()[Value.DAILY.getValue()][Value.REVENUE.getValue()];
				}
			}
		}

		view.showMessage("\nIngresos totales: " + totalRevenue + "\nIngreso anuales: " + yearlyRevenue
				+ "\nIngresos mensuales: " + monthlyRevenue + "\nIngresos diarios: " + dailyRevenue);
		do {
			view.showMessage("\n-2 Informe Completo\n-1. Informe completo (Sin Mostrar Tickets)\n"
					+ (VehicleType.AIRPLANE.getValue() + 1) + "." + VehicleType.AIRPLANE.getUpperCaseName() + "\n"
					+ (VehicleType.BUS.getValue() + 1) + "." + VehicleType.BUS.getUpperCaseName() + "\n"
					+ (VehicleType.SHIP.getValue() + 1) + "." + VehicleType.SHIP.getUpperCaseName() + "\n"
					+ (VehicleType.TRAVEL_BUS.getValue() + 1) + "." + VehicleType.TRAVEL_BUS.getUpperCaseName()
					+ "\n0. Salir");
			int reportType = 0;
			try {
				reportType = view.readNumber() - 1;
			} catch (NumberFormatException ex) {
				view.showMessage("Digite un número entero");
			}

			if (reportType == -1) {
				view.showMessage("Se ha terminado la operación");
				return;
			}
			int repeat = 1;
			int vehicleTypeNumber = reportType;
			if (reportType == -3 || reportType == -2) {
				vehicleTypeNumber = 0;
				repeat = 4;
			}

			for (int r = 0; r < repeat; r++, vehicleTypeNumber++) {
				view.showMessage(VehicleType.createByInteger(vehicleTypeNumber).getUpperCaseName() + "\n");
				for (int i = 0; i < calculate.getDataCenter().getVehicles()[vehicleTypeNumber].length; i++) {
					Vehicle vehicle = calculate.getDataCenter().getVehicles()[vehicleTypeNumber][i];

					if (vehicle != null) {
						view.showMessage((i + 1) + ". " + vehicle.getPlate() + "\nGanancias Totales: "
								+ vehicle.getRevenue()[Value.GENERAL.getValue()][Value.REVENUE.getValue()]
								+ "\nGanacias Anuales: "
								+ vehicle.getRevenue()[Value.YEARLY.getValue()][Value.REVENUE.getValue()]
								+ "\nGanancias Mensuales: "
								+ vehicle.getRevenue()[Value.MONTHLY.getValue()][Value.REVENUE.getValue()]
								+ "\nGanancias Diarias: "
								+ vehicle.getRevenue()[Value.DAILY.getValue()][Value.REVENUE.getValue()]);
						if (vehicle.getTickets()[0] == null) {
							view.showMessage("Cantidad de Tickets Vendidos: 0\n");
						} else {
							view.showMessage("Cantidad de Tickets Vendidos: "
									+ (vehicle.getRevenue()[Value.GENERAL.getValue()][Value.CURRENT_TICKET.getValue()]
											+ 1)
									+ "\n");
						}

						if (reportType == -3) {
							for (int j = 0; j < vehicle.getTickets().length; j++) {
								if (vehicle.getTickets()[j] != null) {
									view.showMessage("Ticket Nro: " + (j + 1) + ". Nombre: "
											+ vehicle.getTickets()[j].getName() + ". Cliente: "
											+ vehicle.getTickets()[j].getOwner().getName() + "\nEntrada "
											+ vehicle.getTickets()[j].getRoutes()[Value.ENTRY.getValue()]
													.getStops()[Value.ENTRY.getValue()]
											+ ". Salida: "
											+ vehicle.getTickets()[j].getRoutes()[Value.EXIT.getValue()]
													.getStops()[Value.EXIT.getValue()]
											+ "\nPrecio Pagado: " + vehicle.getTickets()[j].getPrice() + "\n");
								}
							}
						}
					}
				}
			}
			view.showMessage("Digite cualquier tecla para continuar");
			view.readData();
		} while (option != 0);
	}

	public void run() {
		boolean cancelate = false;
		int subscriptionNumber;
		String name;
		String password;
		int stopsNumber = 0;
		int vehicleTypeInt;
		do {
			view.showMessage(
					"Bienvenido a mi sistema de transporte masivo\n\nSeleccione la opción que más le convenga: \n1. Iniciar sesión\n2. Ver rutas\n3. Registrarse\n4. Soy una empresa\n0. Salir");
			try {
				option = view.readNumber();
			} catch (NumberFormatException ex) {
				option = -1;
				view.showMessage("Digite un número entero e intételo de nuevo\n");
			}
			switch (option) {
			case 1:
				view.showMessage("Ingrese su usuario\n0. Cancelar");
				name = view.readData();
				if (name.equals("0")) {
					view.showMessage("Se ha cancelado la operación");
					break;
				}
				view.showMessage("Ingrese su contraseña");
				password = view.readData();
				if (calculate.logIn(Value.USER, name, password)) {
					int userNumber = calculate.searchUserArrayNumber(Value.USER, name);
					User currentUser = calculate.getDataCenter().getUsers()[Value.USER.getValue()][userNumber];
					view.showMessage("Su número de usuario es: " + userNumber);
					calculate.checkSubscriptionsPayment(currentUser);
					do {
						view.showMessage(
								"¿Qué desea hacer?\n1. Comprar ticket\n2. Suscripciones\n3. Mi billetera\n4. Ver historial y estado de mis tickets\n5. Familia y Amigos\n6. Perfil\n0. Cerrar sesión");
						try {
							option = view.readNumber();
						} catch (NumberFormatException ex) {
							option = -1;
							view.showMessage("Digite un número entero e intételo de nuevo\n");
						}
						if (option == 0) {
							view.showMessage("Se ha cerrado sesión correctamente\n");
							option = -1;
							break;
						}
						switch (option) {
						case 1:
							view.showMessage("¿Para qué tipo de vehículo desea su reserva?");
							showCreateTicketMenu(currentUser, currentUser);
							break;
						case 2:
							view.showMessage(
									"1. Gestionar mis suscripciones\n2. Crear una nueva suscripción\n0. Volver");
							try {
								option = view.readNumber();
							} catch (NumberFormatException ex) {
								option = -1;
								view.showMessage("Digite un número entero e intételo de nuevo\n");
							}
							if (option == 0) {
								option = 1;
								break;
							}
							switch (option) {
							case 1:
								boolean hasActiveSubscriptions = false;
								for (int i = 0; i < currentUser.getSubscriptions().length; i++) {
									if (currentUser.getSubscriptions()[i] != null) {
										if (!hasActiveSubscriptions) {
											view.showMessage("Estas son tus suscripciones activas: ");
										}
										view.showMessage("Suscripción activa Nro " + (i + 1) + "\nVehículo: "
												+ currentUser.getSubscriptions()[i].getVehicle().getVehicleType()
														.getUpperCaseName()
												+ " " + currentUser.getSubscriptions()[i].getVehicle().getPlate()
												+ "\nEmpresa: "
												+ currentUser.getSubscriptions()[i].getVehicle().getCompany()
												+ "\nEntrada: "
												+ currentUser.getSubscriptions()[i].getRoutes()[Value.ENTRY.getValue()]
														.getStopsName()[Value.ENTRY.getValue()]
												+ "  "
												+ currentUser.getSubscriptions()[i].getRoutes()[Value.ENTRY.getValue()]
														.getStops()[Value.ENTRY.getValue()]
												+ "\nSalida: "
												+ currentUser.getSubscriptions()[i].getRoutes()[Value.EXIT.getValue()]
														.getStopsName()[Value.EXIT.getValue()]
												+ "  "
												+ currentUser.getSubscriptions()[i].getRoutes()[Value.EXIT.getValue()]
														.getStops()[Value.EXIT.getValue()]
												+ "\nPrecio: "
												+ currentUser.getSubscriptions()[i].getVehicle().getPrice());
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
									if (currentUser.getSubscriptions()[subscriptionNumber] != null) {
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
											calculate.deleteSubscription(currentUser, subscriptionNumber);
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
												+ VehicleType.AIRPLANE.getUpperCaseName() + "\n2. "
												+ VehicleType.BUS.getUpperCaseName() + "\n3. "
												+ VehicleType.SHIP.getUpperCaseName() + "\n4. "
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
								showCreateSubscriptionMenu(currentUser,
										calculate.convertIntToVehicleType(vehicleTypeInt));
								break;
							default:
								view.showMessage("Opción inválida");
								option = -1;
								break;
							}
							break;
						case 3:
							view.showMessage("Su saldo actual es: " + currentUser.getWallet()
									+ " pesos\nDigite 1 para agregar fondos, 2 para volver");
							try {
								option = view.readNumber();
							} catch (NumberFormatException ex) {
								option = -1;
								view.showMessage("Digite un número entero e intételo de nuevo\n");
							}
							if (option == 1) {
								view.showMessage("Cuánto dinero desea agregar? Agregue 0 para cancelar la operación");
								int userAddMoney = view.readNumber();
								if (userAddMoney != 0) {
									calculate.addWallet(currentUser, userAddMoney);
									view.showMessage("Se ha agregado correctamente la cantidad de " + userAddMoney
											+ " pesos\nNuevo saldo disponible: " + currentUser.getWallet());
								} else {
									view.showMessage("Se ha cancelado la operación");
								}
							}
							break;
						case 4:
							view.showMessage("Su historial de compras es el siguiente: ");
							for (int i = 0; i < currentUser.getTicketHistory().length; i++) {
								if (currentUser.getTicketHistory()[i] != null) {
									calculate.checkRouteSequenceAvailability(
											currentUser.getTicketHistory()[i].getVehicle().getRouteSeq());
									showBill(currentUser, currentUser.getTicketHistory()[i]);
								}
							}
							view.showMessage("Digite cualquier tecla para volver");
							view.readData();
							break;
						case 5:
							view.showMessage(
									"Familiares y Amigos\n1. Ver usuarios\n2. Agregar nuevo usuario\n0. Salir");
							try {
								option = view.readNumber();
							} catch (NumberFormatException ex) {
								option = -1;
								view.showMessage("Digite un número entero e intételo de nuevo\n");
							}
							if (option == 0) {
								option = -1;
								break;
							}
							switch (option) {
							case 1:
								view.showMessage("Digite el número de un usuario para ver más opciones");
								for (int i = 0; i < currentUser.getRelationships().length; i++) {
									if (currentUser.getRelationships()[i] != null) {
										view.showMessage(
												(i + 1) + ". Nombre: " + currentUser.getRelationships()[i].getName());
									}
								}
								view.showMessage("0. Salir");
								int relationshipUserNumber = 0;
								try {
									relationshipUserNumber = view.readNumber() - 1;
								} catch (NumberFormatException ex) {
									option = -1;
									view.showMessage("Digite un número entero e intételo de nuevo\n");
								}
								if (relationshipUserNumber == -1
										|| relationshipUserNumber >= currentUser.getRelationships().length) {
									option = -1;
									break;
								}
								User relationship = currentUser.getRelationships()[relationshipUserNumber];
								if (relationship == null) {
									view.showMessage("Opción Inválida, por favor, inténtelo de nuevo");
									option = -1;
									break;
								}
								view.showMessage("Opciones para " + relationship.getName()
										+ "\n1. Comprar ticket\n2. Eliminar de mi lista\n0. Salir");
								try {
									option = view.readNumber();
								} catch (NumberFormatException ex) {
									option = -1;
									view.showMessage("Digite un número entero e intételo de nuevo\n");
								}
								if (option == 0) {
									option = -1;
									break;
								}
								switch (option) {
								case 1:
									showCreateTicketMenu(relationship, currentUser);
									break;
								case 2:
									view.showMessage("Está seguro de que desea eliminar el usuario "
											+ relationship.getName() + " de su lista de amigos?\n1. Sí 0. No");
									try {
										option = view.readNumber();
									} catch (NumberFormatException ex) {
										option = -1;
										view.showMessage("Digite un número entero e intételo de nuevo\n");
									}
									if (option == 0) {
										view.showMessage("Se ha cancelado la operación");
									}
									if (option == 1) {
										currentUser.getRelationships()[relationshipUserNumber] = null;
										view.showMessage("Se ha eliminado al usuario de su lista de amigos");
									} else {
										view.showMessage("Digite un número entero válido");
									}
									option = -1;
									break;
								default:
									break;
								}

								break;
							case 2:
								view.showMessage("Digite el nombre del usuario a agregar");
								String userRelationshipName = view.readData();
								if (!calculate.isUserAvailable(userRelationshipName)) {
									calculate.createRelationship(currentUser,
											calculate.getDataCenter().getUsers()[Value.USER.getValue()][calculate
													.searchUserArrayNumber(Value.USER, userRelationshipName)]);
									view.showMessage(
											"Se ha añadido a su lista de amigos el usuario " + userRelationshipName);
									option = -1;
								}
								break;
							default:
								view.showMessage("Digite un número válido e inténtelo de nuevo");
								break;
							}
							break;
						case 6:
							if (userNumber == 0) {
								view.showMessage("El administrador no puede cambiar sus credenciales");
								option = -1;
								break;
							}
							view.showMessage("Usuario: " + currentUser.getName() + "\nContraseña: "
									+ currentUser.getPassword() + "\n1. Editar\n0. Salir");
							try {
								option = view.readNumber();
							} catch (NumberFormatException ex) {
								option = -1;
								view.showMessage("Digite un número entero e intételo de nuevo\n");
							}
							if (option == 1) {
								view.showMessage("1. Cambiar Usuario\n2. Cambiar Contraseña\n0. Volver");
								try {
									option = view.readNumber();
								} catch (NumberFormatException ex) {
									option = -1;
									view.showMessage("Digite un número entero e intételo de nuevo\n");
								}
								switch (option) {
								case 0:
									view.showMessage("Se ha cancelado la operación");
									break;
								case 1:
									do {
										view.showMessage(
												"Digite su nuevo usuario, usuario actual: " + currentUser.getName());
										String newUserName = view.readData();
										if (calculate.isUserAvailable(newUserName)) {
											view.showMessage("Su nuevo usuario será: " + newUserName
													+ "\n1. Confirmar\n0. Cancelar");
											try {
												option = view.readNumber();
											} catch (NumberFormatException ex) {
												option = -1;
												view.showMessage("Digite un número entero e intételo de nuevo\n");
											}
											if (option == 1) {
												currentUser.setName(newUserName);
											} else {
												view.showMessage("Se ha cancelado la operación");
												option = -1;
												break;
											}
										} else {
											view.showMessage(
													"El nombre de usuario no está disponible, por favor inténtelo de nuevo\n");
											option = 1;
											continue;
										}
									} while (option != 0);
									option = 1;
									break;
								case 2:
									view.showMessage("Digite su nueva contraseña, contraseña actual: "
											+ currentUser.getPassword());
									String newPassword = view.readData();
									view.showMessage("Digite de nuevo su contraseña para confirmar");
									String confirmPassword = view.readData();
									if (newPassword.equals(confirmPassword)) {
										currentUser.setPassword(newPassword);
										view.showMessage("Ha cambiado de contraseña correctamente");
									} else {
										view.showMessage("Las contraseñas no coinciden, se ha cancelado la operación");
										option = -1;
										break;
									}
									break;
								default:
									view.showMessage(
											"Digite un número válido e inténtelo de nuevo, se ha cancelado la operación");
									option = -1;
									break;
								}
							}
							break;
						}
					} while (option != 0);

				} else {
					view.showMessage("Ha ocurrido un error al intentar iniciar sesión");
				}
				break;
			case 2:
				view.showMessage("¿De cuál vehículo desea consultar las rutas?\n1. "
						+ VehicleType.AIRPLANE.getUpperCaseName() + "\n2. " + VehicleType.BUS.getUpperCaseName()
						+ "\n3. " + VehicleType.SHIP.getUpperCaseName() + "\n4. "
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
				name = view.readData();
				if (name.equals("0")) {
					break;
				}
				view.showMessage("Ingrese la contraseña");
				password = view.readData();
				if (calculate.isUserAvailable(name)) {
					calculate.createUser(Value.USER, name, password);
					view.showMessage("Se ha registrado correctamente");
				} else {
					view.showMessage("El nombre de usuario digitado ya está en uso");
					option = -1;
					break;
				}
				break;
			case 4:
				view.showMessage("1. Iniciar sesión\n2. Registrarse\n0. Salir");
				try {
					option = view.readNumber();
				} catch (NumberFormatException ex) {
					view.showMessage("Debes escribir un número entero");
					option = -1;
					break;
				}

				if (option == 0) {
					option = -1;
					break;
				}

				switch (option) {
				case 1:
					view.showMessage("Digite el nombre de la empresa");
					name = view.readData();
					view.showMessage("Digite su contraseña");
					password = view.readData();
					if (calculate.logIn(Value.COMPANY, name, password)) {
						Company currentCompany = (Company) calculate.getDataCenter().getUsers()[Value.COMPANY
								.getValue()][calculate.searchUserArrayNumber(Value.COMPANY, name)];
						do {
							view.showMessage(
									"¿Qué desea hacer?\n1. Datos de mis vehículos\n2. Crear nuevo vehículo\n3. Crear nueva ruta\n4. Editar vehículo existente\n5. Borrar ruta\n0. Cerrar sesión");
							try {
								option = view.readNumber();
							} catch (NumberFormatException ex) {
								view.showMessage("Debes escribir un número entero");
								option = -1;
								break;
							}
							if (option == 0) {
								view.showMessage("Se ha cerrado sesión de manera exitosa");
								option = -1;
								break;
							}

							switch (option) {
							case 1:
								view.showMessage(currentCompany.getName() + " sus ingresos son los siguientes:");
								showStatistics(currentCompany, Value.GENERAL);
								break;
							case 2:
								view.showMessage("¿Qué tipo de vehículo desea crear?\n1. "
										+ VehicleType.AIRPLANE.getUpperCaseName() + "\n2. "
										+ VehicleType.BUS.getUpperCaseName() + "\n3. "
										+ VehicleType.SHIP.getUpperCaseName() + "\n4. "
										+ VehicleType.TRAVEL_BUS.getUpperCaseName() + "\n0. Salir");
								try {
									vehicleTypeInt = view.readNumber() - 1;
								} catch (NumberFormatException ex) {
									option = -1;
									view.showMessage(
											"Digite un número entero e intételo de nuevo, por favor, inténtelo de nuevo\n");
									continue;
								}
								if (vehicleTypeInt == -1) {
									view.showMessage("Se ha cancelado la operación\n");
									break;
								}
								try {
									showCreateVehicleMenu(calculate.convertIntToVehicleType(vehicleTypeInt),
											currentCompany);
								} catch (NullPointerException ex) {
									option = -1;
									view.showMessage("Opción inválida, se ha cancelado la operación\n");
									break;
								}
								break;
							case 3:
								view.showMessage("Digite el nombre de la ruta");
								String routeName = view.readData();
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
									view.showMessage("Digite un número entero e intételo de nuevo\n");
									break;
								}
								view.showMessage("Digite la duración en minutos");
								int[] durationTime = new int[stopsNumber];
								for (int i = 1; i < durationTime.length; i++) {
									view.showMessage("Duración desde la parada " + i + " hasta la parada " + (i + 1));
									try {
										durationTime[i - 1] = view.readNumber();
									} catch (NumberFormatException ex) {
										view.showMessage(
												"Digite un número entero e intételo de nuevo e inténtalo de nuevo\n");
										i--;
									}
								}
								try {
									calculate.createRouteSeq(currentCompany, routeName, initialTime,
											calculate.readLaboralDays(daysNumber), stopsNumber, durationTime);
								} catch (DateTimeParseException ex) {
									view.showMessage("Se ha digitado mal la hora, inténtelo nuevamente");
									option = -1;
									break;
								}
								view.showMessage("La ruta se ha creado correctamente\n");
								break;
							case 4:
								view.showMessage("¿Qué tipo de vehículo desea editar?\n1. "
										+ VehicleType.AIRPLANE.getUpperCaseName() + "\n2. "
										+ VehicleType.BUS.getUpperCaseName() + "\n3. "
										+ VehicleType.SHIP.getUpperCaseName() + "\n4. "
										+ VehicleType.TRAVEL_BUS.getUpperCaseName() + "\n0. Salir");
								try {
									vehicleTypeInt = view.readNumber() - 1;
								} catch (NumberFormatException ex) {
									view.showMessage("Debes escribir un número entero válido, inténtalo de nuevo");
									break;
								}
								if (vehicleTypeInt == -1) {
									view.showMessage("Se ha cancelado la operación");
									option = -1;
									break;
								}
								VehicleType vehicleType = calculate.convertIntToVehicleType(vehicleTypeInt);
								if (vehicleType == null) {
									view.showMessage("Digite un número entero válido, por favor inténtelo de nuevo");
									option = -1;
									break;
								}
								view.showMessage("\nDigite el número del vehículo para editarlo\n0. Cancelar");
								Vehicle[] vehicles;
								switch (vehicleType) {
								case AIRPLANE:
									vehicles = currentCompany.getVehicles()[VehicleType.AIRPLANE.getValue()];
									break;
								case BUS:
									vehicles = currentCompany.getVehicles()[VehicleType.BUS.getValue()];
									break;
								case SHIP:
									vehicles = currentCompany.getVehicles()[VehicleType.SHIP.getValue()];
									break;
								case TRAVEL_BUS:
									vehicles = currentCompany.getVehicles()[VehicleType.TRAVEL_BUS.getValue()];
									break;
								default:
									vehicles = currentCompany.getVehicles()[VehicleType.BUS.getValue()];
									view.showCurrentLineMessage("Opción inválida");
								}
								for (int i = 0; i < vehicles.length; i++) {
									if (vehicles[i] != null) {
										view.showMessage(
												(i + 1) + "   " + "\nRuta: " + "\n Ticket: " + vehicles[i].getPrice());
									}
								}
								int vehicleNumber = 0;
								try {
									vehicleNumber = view.readNumber() - 1;
								} catch (NumberFormatException ex) {
									view.showMessage("Digite un número entero e intételo de nuevo\n");
									break;
								}
								if (vehicleNumber == -1) {
									view.showMessage("Se ha cancelado la operación");
									vehicleNumber = -1;
									break;
								}
								if (vehicles[vehicleNumber] == null) {
									view.showMessage("Has seleccionado un vehículo inexistente");
									vehicleNumber = -1;
									break;
								}

								view.showMessage("El vehículo seleccionado es el siguiente:\n" + (vehicleNumber + 1)
										+ "   " + "\nRuta: " + vehicles[vehicleNumber].getRouteSeq().getName()
										+ "\n Ticket: " + vehicles[vehicleNumber].getPrice());
								for (int i = 0; i < vehicles[vehicleNumber].getRouteSeq().getRoutes().length; i++) {
									if (vehicles[vehicleNumber].getRouteSeq().getRoutes()[i] != null) {
										view.showCurrentLineMessage("\nRuta " + (i + 1) + ": "
												+ vehicles[vehicleNumber].getRouteSeq().getRoutes()[i].getName()
												+ "\n Entrada: "
												+ vehicles[vehicleNumber].getRouteSeq().getRoutes()[i].getStops()[0]
												+ "\n Salida: "
												+ vehicles[vehicleNumber].getRouteSeq().getRoutes()[i].getStops()[1]);
									}
								}
								view.showMessage("\n1. Editar\n0. Cancelar");
								try {
									option = view.readNumber();
								} catch (NumberFormatException ex) {
									view.showMessage("Digite un número entero e intételo de nuevo\n");
									break;
								}
								if (option == 1) {
									view.showMessage(
											"1. Edición completa\n2. Editar Ruta\n3. Editar precio\n4. Eliminar\n0. Cancelar");
									try {
										option = view.readNumber();
									} catch (NumberFormatException ex) {
										view.showMessage("Digite un número entero e intételo de nuevo\n");
										break;
									}
									switch (option) {
									case 1:
										view.showMessage("Edición completa");
									case 2:
										view.showMessage("Con qué ruta desea reemplazar?");
										view.showMessage(
												"Elija la ruta que desea asignarle al vehículo y ver más detalles\n0. Salir");
										for (int i = 0; i < currentCompany.getRouteSeqs().length; i++) {
											if (currentCompany.getRouteSeqs()[i] != null) {
												view.showMessage("Ruta " + (i + 1) + ": "
														+ currentCompany.getRouteSeqs()[i].getName());
											}
										}
										int routeNumber = -1;
										try {
											routeNumber = view.readNumber() - 1;
										} catch (NumberFormatException ex) {
											option = -1;
											view.showMessage(
													"Digite un número entero e intételo de nuevo, por favor, inténtelo de nuevo\n");
											continue;
										}
										if (routeNumber == -1) {
											cancelate = true;
											break;
										}
										try {
											for (int i = 0; i < currentCompany.getRouteSeqs()[routeNumber]
													.getRoutes().length; i++) {
												view.showMessage(currentCompany.getRouteSeqs()[routeNumber]
														.getRoutes()[i].getName()
														+ "\nInicio: "
														+ currentCompany.getRouteSeqs()[routeNumber].getRoutes()[i]
																.getStops()[0]
														+ "\nFin: "
														+ currentCompany.getRouteSeqs()[routeNumber].getRoutes()[i]
																.getStops()[1]
														+ "\n");
											}
										} catch (NullPointerException ex) {
											view.showMessage(
													"La ruta seleccionada es inexistente, por favor, inténtelo de nuevo");
											option = -1;
											continue;
										}
										view.showMessage("Digite 1 para seleccionar esta ruta, 0 para regresar");
										try {
											option = view.readNumber();
										} catch (NumberFormatException ex) {
											option = -1;
											view.showMessage("Digite un número entero e intételo de nuevo\n");
										}
										if (option == 0) {
											view.showMessage("Se ha cancelado la operación");
											option = -1;
											break;
										}
										calculate.editVehicle(vehicles[vehicleNumber],
												currentCompany.getRouteSeqs()[routeNumber],
												vehicles[vehicleNumber].getPrice());
										view.showMessage("La nueva ruta se ha asignado correctamente");
										if (option != 1) {
											break;
										}
									case 3:
										view.showMessage(
												"Digite el nuevo precio que tendrá el ticket. Precio anterior: "
														+ vehicles[vehicleNumber].getPrice());
										int price = 0;
										try {
											price = view.readNumber();
										} catch (NumberFormatException ex) {
											option = -1;
											view.showMessage("Digite un número entero e intételo de nuevo\n");
											break;
										}
										calculate.editVehicle(vehicles[vehicleNumber],
												vehicles[vehicleNumber].getRouteSeq(), price);

										view.showMessage("El nuevo precio se ha asignado correctamente");
										break;
									case 4:
										view.showMessage(
												"Está a punto de eliminar el vehículo, esta acción no se puede revertir\n1. Confirmar\n0. Cancelar");
										try {
											option = view.readNumber();
										} catch (NumberFormatException ex) {
											view.showMessage("Digite un número entero e intételo de nuevo\n");
											break;
										}
										if (option == 1) {
											calculate.deleteVehicle(vehicles[vehicleNumber]);
											view.showMessage("Se ha eliminado el vehículo de manera exitosa");
										} else {
											view.showMessage("Se ha cancelado la operación");
											option = -1;
										}
									default:
										view.showMessage("Seleccione una opción válida");
										break;
									}
								} else {
									view.showMessage("Se ha cancelado la operación");
									break;
								}
								break;
							case 5:
								for (int i = 0; i < currentCompany.getRouteSeqs().length; i++) {
									if (currentCompany.getRouteSeqs()[i] != null) {
										view.showMessage("\nSecuencia de Rutas Nro" + (i + 1) + ": "
												+ currentCompany.getRouteSeqs()[i].getName());
									}
								}
								view.showMessage(
										"Digite el número de la secuencia de rutas para ver más detalles\0. Salir");
								try {
									option = view.readNumber() - 1;
								} catch (NumberFormatException ex) {
									view.showMessage("Digite un número entero e intételo de nuevo\n");
									break;
								}
								if (option == 0) {
									view.showMessage("Se ha cancelado la operación");
									option = -1;
									break;
								}
								RouteSequence routeSeqSelected = currentCompany.getRouteSeqs()[option - 1];
								for (int i = 0; i < routeSeqSelected.getRoutes().length; i++) {
									if (routeSeqSelected.getRoutes()[i] != null) {
										view.showCurrentLineMessage("\nRuta " + option + ". "
												+ routeSeqSelected.getRoutes()[i].getName() + "\n Entrada: "
												+ routeSeqSelected.getRoutes()[i].getStops()[Value.ENTRY.getValue()]
												+ "\n Salida: "
												+ routeSeqSelected.getRoutes()[i].getStops()[Value.EXIT.getValue()]
												+ "\n Disponibilidad: ");
										if (routeSeqSelected.getRoutes()[i].getAvailability()) {
											view.showMessage("available");
										} else {
											view.showMessage("unavailable");
										}
									}
								}

								view.showMessage("1. Eliminar permanentemente\n0. Cancelar y salir");
								try {
									option = view.readNumber() - 1;
								} catch (NumberFormatException ex) {
									view.showMessage("Digite un número entero e intételo de nuevo\n");
									break;
								}
								if (option == 0) {
									view.showMessage("Se ha cancelado la operación");
									option = -1;
									break;
								}
								routeSeqSelected = null;
								break;
							default:
								break;
							}
						} while (option != 0);
						option = -1;
					} else {
						view.showMessage(
								"No ha sido posible iniciar sesión, por favor, verifique los datos e intente nuevamente");
						option = -1;
					}
					break;
				case 2:
					view.showMessage("Digite el nombre de la empresa");
					name = view.readData();
					view.showMessage("Digite su contraseña");
					password = view.readData();
					if (calculate.isUserAvailable(name)) {
						calculate.createUser(Value.COMPANY, name, password);
						view.showMessage(
								"La empresa se ha creado correctamente\n¿Cuál es la descripción de su empresa? (Esto se puede modificar en el futuro)");
						String description = view.readData();
						calculate
								.editCompanyDescription(
										(Company) calculate.getDataCenter().getUsers()[Value.COMPANY
												.getValue()][calculate.searchUserArrayNumber(Value.COMPANY, name)],
										description);
					} else {
						view.showMessage("El nombre de usuario ya está en uso");
						option = -1;
						break;
					}
					break;
				default:
					view.showMessage("Digita un número entero válido");
				}
				break;

			case 5:
				view.showMessage("Menú de administrador\nDigite el usuario:");
				name = view.readData();
				view.showMessage("Digite la contraseña");
				password = view.readData();
				if (calculate.logInAdmin(name, password)) {
					do {
						view.showMessage("Funcionalidad en proceso");
						// break;
						view.showMessage("1. Eliminar empresa\n2. Eliminar Usuario\n0. Cerrar Sesión");
						try {
							option = view.readNumber() - 1;
						} catch (NumberFormatException ex) {
							view.showMessage("Digite un número entero e inténtelo de nuevo\n");
							break;
						}
						if (option == 0) {
							view.showMessage("Se ha cancelado la operación");
							option = -1;
							break;
						}
						switch (option) {
						case 1:
							User currentCompany = null;
							for (int i = 0; i < calculate.getDataCenter().getUsers()[Value.COMPANY
									.getValue()].length; i++) {
								currentCompany = calculate.getDataCenter().getUsers()[Value.COMPANY.getValue()][i];
								if (currentCompany != null) {
									view.showMessage((i + 1) + ". " + currentCompany.getName());
								}
							}
							view.showMessage("Digite el número de la empresa que desea eliminar\0. Cancelar y salir");
							try {
								option = view.readNumber() - 1;
							} catch (NumberFormatException ex) {
								view.showMessage("Digite un número entero e inténtelo de nuevo\n");
								break;
							}
							if (option == 0) {
								view.showMessage("Se ha cancelado la operación");
								option = -1;
								break;
							}
							currentCompany = calculate.getDataCenter().getUsers()[Value.COMPANY.getValue()][option];
							if (currentCompany == null) {
								view.showMessage(
										"La empresa seleccionada es inexistente, se ha cancelado la operación");
								option = -1;
								break;
							}
							view.showMessage("Está seguro de que desea eliminar la empresa " + currentCompany.getName()
									+ "? Esta acción no se puede revertir\n1. Eliminar\0. Cancelar");
							try {
								option = view.readNumber() - 1;
							} catch (NumberFormatException ex) {
								view.showMessage("Digite un número entero e inténtelo de nuevo\n");
								break;
							}

							switch (option) {
							case 0:
								view.showMessage("Se ha cancelado la operación");
								option = -1;
								break;
							case 1:
								currentCompany = null;
								view.showMessage("La empresa se ha elimiando exitosamente");
								break;
							default:
								view.showMessage("Por favor, digite un número válido, se ha cancelado la operación");
								option = -1;
								break;
							}
							break;
						case 2:
							User currentUser = null;
							for (int i = 1; i < calculate.getDataCenter().getUsers()[Value.USER
									.getValue()].length; i++) {
								currentUser = calculate.getDataCenter().getUsers()[Value.USER.getValue()][i];
								if (currentUser != null) {
									view.showMessage(i + ". " + currentUser.getName());
								}
							}
							view.showMessage("Digite el número del usuario que desea eliminar\0. Cancelar y salir");
							try {
								option = view.readNumber();
							} catch (NumberFormatException ex) {
								view.showMessage("Digite un número entero e inténtelo de nuevo\n");
								break;
							}
							if (option == 0) {
								view.showMessage("Se ha cancelado la operación");
								option = -1;
								break;
							}
							currentUser = calculate.getDataCenter().getUsers()[Value.USER.getValue()][option];
							if (currentUser == null) {
								view.showMessage(
										"El usuario seleccionado es inexistente, se ha cancelado la operación");
								option = -1;
								break;
							}
							view.showMessage("Está seguro de que desea eliminar el usuario " + currentUser.getName()
									+ "? Esta acción no se puede revertir\n1. Eliminar\0. Cancelar");
							try {
								option = view.readNumber() - 1;
							} catch (NumberFormatException ex) {
								view.showMessage("Digite un número entero e inténtelo de nuevo\n");
								break;
							}

							switch (option) {
							case 0:
								view.showMessage("Se ha cancelado la operación");
								option = -1;
								break;
							case 1:
								currentUser = null;
								view.showMessage("El usuario se ha elimiando exitosamente");
								break;
							default:
								view.showMessage("Por favor, digite un número válido, se ha cancelado la operación");
								option = -1;
								break;
							}
							break;
						default:
							view.showMessage("Digite un número válido");
							option = -1;
							break;
						}
						option = -1;
					} while (option != 0);
					option = -1;
				} else {
					view.showMessage("Usuario o contraseña inválidos\n");
				}
				break;
			case 0:
				view.showMessage("Gracias por usar nuestros servicios!");
				return;
			default:
				view.showMessage("Digite una opción válida e inténtelo de nuevo");
				option = -1;
				break;
			}

		} while (option != 0);

	}

	public static void main(String[] args) {
		Presenter presenter = new Presenter();

		presenter.defaultValues();
		presenter.run();
	}
}