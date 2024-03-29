package presenter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import model.*;
import model.Coupon.RedeemType;
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
		calculate.createUser(User.Type.USER, "administrador", "0000");
		calculate.createUser(User.Type.COMPANY, "default", "0000");
		int number = calculate.searchUserArrayNumber(User.Type.COMPANY, "default");
		calculate.createRouteSeq((Company) calculate.getDataCenter().getUsers()[User.Type.COMPANY.ordinal()][number],
				"Ruta 1", "22:30:00", calculate.readLaboralDays(new int[] { 8 }), 6, new int[] { 10, 15, 30, 40, 15 });
		calculate.createVehicle(Vehicle.Type.BUS,
				(Company) calculate.getDataCenter().getUsers()[User.Type.COMPANY.ordinal()][number], "default",
				calculate.getDataCenter().getRouteSeqs()[0], 3500, 30);
	}

	public int readOption(boolean showErrorMessage, int limit) {
		int option;
		try {
			option = view.readNumber();
			if (showErrorMessage && (option == 0 || option > limit)) {
				view.showMessage("Se ha cancelado la operación\n");
			}
			return option;
		} catch (NumberFormatException ex) {
			view.showMessage("Digite un número entero e inténtelo de nuevo\n");
			return Integer.MIN_VALUE;
		}
	}

	public void showBill(User user, Ticket ticket) {
		view.showCurrentLineMessage("Número de Ticket: " + ticket.getName() + "\n"
				+ ticket.getVehicle().getType().getUpperCaseName() + ": " + ticket.getVehicle().getPlate()
				+ "\nEmpresa: " + ticket.getVehicle().getCompany().getName() + "\nPrecio pagado: "
				+ ticket.getPrice()[Ticket.PriceType.PAID.ordinal()] + "\nEntrada: " + "Ruta "
				+ ticket.getRoutes()[Route.StopType.ENTRY.ordinal()].getStopsName()[Route.StopType.ENTRY.ordinal()]
				+ " "
				+ ticket.getRoutes()[Route.StopType.ENTRY.ordinal()].getStops()[Route.StopType.ENTRY.ordinal()]
				+ "\nSalida: " + "Ruta "
				+ ticket.getRoutes()[Route.StopType.EXIT.ordinal()].getStopsName()[Route.StopType.EXIT.ordinal()] + " "
				+ ticket.getRoutes()[Route.StopType.EXIT.ordinal()].getStops()[Route.StopType.EXIT.ordinal()]
				+ "\nEstado Actual: ");
		if (ticket.getIsAvailable()) {
			view.showMessage("Activo");
		} else if (calculate.getDataCenter().getCurrentDate()
				.isBefore(ticket.getRoutes()[Route.StopType.ENTRY.ordinal()].getStops()[Route.StopType.ENTRY.ordinal()])
				|| calculate.getDataCenter().getCurrentDate()
						.isEqual(ticket.getRoutes()[Route.StopType.ENTRY.ordinal()].getStops()[Route.StopType.ENTRY
								.ordinal()])) {
			view.showMessage("Confirmado");
		} else {
			view.showMessage("Inactivo");
		}
		if (!ticket.getOwner().getName().equals(ticket.getBuyer().getName())) {
			view.showMessage("Comprador: " + ticket.getBuyer().getName());
		}
		view.showMessage("");
	}

	public void showCoupons(Coupon[] coupons, int price, boolean detailed) {
		for (int i = 0; i < coupons.length; i++) {
			Coupon coupon = coupons[i];
			if (coupon != null) {
				if (detailed || coupon.getType().equals(RedeemedCoupon.Type.PUBLIC)) {
					String message = "";
					message += "\n" + (i + 1) + ". Nombre: " + coupon.getName();
					if (detailed) {
						message += ". Tipo: " + coupon.getType().getName();
						if (coupon.getType().equals(RedeemedCoupon.Type.RESERVED)) {
							message += ". Código Redimible: " + coupon.getRedeemWord();
						}
						message += ". ID: " + coupon.getId();
					}
					message += "\nDescripción: " + coupon.getDescription() + "\nTipo de Descuento: "
							+ coupon.getDiscountType()
							+ ". Descuento: " + coupon.getDiscount();
					if (coupon.getDiscountType().equals(RedeemedCoupon.DiscountType.PERCENTAGE)) {
						message += "%";
					}
					message += ". Precio Final: " + calculate.calculatePriceAfterDiscount(coupon, price);
					/*
					 * if(coupon.isCumulative()){
					 * view.showMessage("Acumulable");
					 * } else{
					 * view.showMessage("No Acumulable")
					 * }
					 */
					if (detailed) {
						message += "\nRedenciones: " + coupon.getRedeems()[Coupon.RedeemType.CURRENT.ordinal()]
								+ ". Límite: "
								+ coupon.getRedeems()[Coupon.RedeemType.MAXIMUM.ordinal()] + ". Disponibilidad: ";
						if (coupon.getIsAvailable()) {
							message += "Disponible";
						} else {
							message += "No Disponible";
						}
						switch (coupon.getApplicable()) {
							case Coupon.AppliesTo.VEHICLES:
								for (Vehicle vehicle : coupon.getApplicableVehicles()) {
									if (vehicle != null) {
										message += "\nPlaca: " + vehicle.getPlate() + ". Precio Estándar: "
												+ vehicle.getPrice()
												+ ". Precio con Descuento: "
												+ calculate.calculatePriceAfterDiscount(coupon, vehicle.getPrice());
									}
								}
								break;
							case Coupon.AppliesTo.ROUTE_SEQS:
								message += "\nRutas: ";
								for (RouteSequence routeSeq : coupon.getApplicableRouteSeqs()) {
									if (routeSeq != null) {
										message += routeSeq.getName() + " ";
									}
								}
								break;
							case Coupon.AppliesTo.ROUTES:
								for (Route route : coupon.getApplicableRoutes()) {
									if (route != null) {
										message += "\nNombre: " + route.getName() + ". Entrada: "
												+ route.getStopsName()[Route.StopType.ENTRY.ordinal()] + ". Salida: "
												+ route.getStopsName()[Route.StopType.EXIT.ordinal()];
									}
								}
								break;
						}
					}
					view.showMessage(message);
				}
			}
		}
	}

	public void showCreateTicketMenu(User user, User userWallet) {
		int vehicleNumber = 0, vehicleTypeInt = 0, routeNumberEntry = 0, routeNumberExit = 0;

		view.showMessage("1. " + Vehicle.Type.AIRPLANE.getUpperCaseName() + "\n2. "
				+ Vehicle.Type.BUS.getUpperCaseName()
				+ "\n3. " + Vehicle.Type.SHIP.getUpperCaseName() + "\n4. " + Vehicle.Type.TRAVEL_BUS.getUpperCaseName()
				+ "\n0. Salir");

		vehicleTypeInt = readOption(false, 4) - 1;
		if (vehicleTypeInt <= -1 || vehicleTypeInt > 4) {
			return;
		}

		Vehicle.Type type = calculate.convertIntToVehicleType(vehicleTypeInt);

		Vehicle[] vehicles = calculate.getDataCenter().getVehicles()[type.ordinal()];

		do {
			calculate.checkVehiclesAvailability(vehicles);
			view.showMessage("\nDigite el número del " + type.getName() + " para ver más información");
			view.showMessage("Los " + type.getPluralName() + " disponibles son los siguientes: \n0. Cancelar");
			calculate.checkVehiclesAvailability(calculate.getDataCenter().getVehicles()[type.ordinal()]);
			for (int i = 0; i < vehicles.length; i++) {
				if (vehicles[i] != null) {
					if (vehicles[i].getIsAvailable()) {
						view.showMessage(type.getUpperCaseName() + " " + (i + 1) + "   "
								+ vehicles[i].getCompany().getName() + "\n Ticket: " + vehicles[i].getPrice());
					}
				}
			}

			vehicleNumber = readOption(false, vehicles.length - 1) - 1;
			if (vehicleNumber <= -1 || vehicleNumber >= vehicles.length) {
				break;
			}

			Vehicle vehicle = vehicles[vehicleNumber];

			calculate.checkVehicleAvailability(vehicle);
			if (vehicle == null) {
				view.showMessage("El " + type.getName() + " seleccionado es inexistente");
				option = 1;
			} else if (!vehicle.getIsAvailable()) {
				view.showMessage("El " + type.getName() + " seleccionado no está disponible");
				option = 1;
			} else {
				int limit = vehicle.getRouteSeq().getRoutes().length;
				for (int i = 0; i < limit; i++) {
					if (vehicle.getRouteSeq().getRoutes()[i] != null) {
						if (vehicle.getRouteSeq().getRoutes()[i].getIsAvailable()) {
							view.showMessage("\nRuta " + (i + 1) + "\n"
									+ vehicle.getRouteSeq().getRoutes()[i].getName() + "\nEntrada: "
									+ vehicle.getRouteSeq().getRoutes()[i]
											.getStops()[Route.StopType.ENTRY.ordinal()]
									+ "\nSalida: " + vehicle.getRouteSeq().getRoutes()[i]
											.getStops()[Route.StopType.EXIT.ordinal()]);
						}
					}
				}
				limit -= 1;
				view.showMessage("Seleccione el número de ruta en el cual va a ingresar, 0 para cancelar");
				routeNumberEntry = readOption(false, limit) - 1;
				if (routeNumberEntry <= -1
						|| routeNumberEntry > limit) {
					if (routeNumberEntry > limit) {
						view.showMessage("La ruta seleccionada no existe");
					}
					break;
				}

				calculate.checkRouteSequenceAvailability(vehicle.getRouteSeq());
				if (vehicle.getRouteSeq().getRoutes()[routeNumberEntry] == null) {
					view.showMessage("La ruta seleccionada no existe");
					option = -1;
				} else if (!vehicle.getRouteSeq().getRoutes()[routeNumberEntry].getIsAvailable()) {
					view.showMessage("La ruta seleccionada no está disponible");
					option = -1;
				} else {

					view.showMessage("Seleccione el número de ruta en el cual saldrá");
					routeNumberExit = readOption(false, limit) - 1;
					if (routeNumberExit <= -1
							|| routeNumberExit > limit) {
						if (routeNumberExit > limit) {
							view.showMessage("La ruta seleccionada no existe");
						}
						break;
					}

					calculate.checkRouteSequenceAvailability(vehicle.getRouteSeq());
					if (routeNumberExit < routeNumberEntry) {
						view.showMessage("La entrada no puede ir después de la salida");
						option = 1;
					} else if (vehicle.getRouteSeq().getRoutes()[routeNumberExit] == null) {
						view.showMessage("La ruta seleccionada es inexistente");
						option = 1;
					} else if (!vehicle.getRouteSeq().getRoutes()[routeNumberExit].getIsAvailable()) {
						view.showMessage("La ruta seleccionada no está disponible");
						option = 1;
					} else {
						Route[] routes = new Route[] { vehicle.getRouteSeq().getRoutes()[routeNumberEntry],
								vehicle.getRouteSeq().getRoutes()[routeNumberExit] };
						Coupon activeCoupon = calculate.findBestCoupon(calculate.findPublicCoupons(
								(Coupon[]) calculate.combineArrays(
										calculate.combineArrays(vehicle.getApplicableCoupons(),
												vehicle.getRouteSeq().getApplicableCoupons()),
										calculate.combineArrays(
												routes[Route.StopType.ENTRY.ordinal()].getApplicableCoupons(),
												routes[Route.StopType.EXIT.ordinal()].getApplicableCoupons()))),
								vehicle.getPrice());
						do {
							view.showCurrentLineMessage(
									"1. Comprar\n2. Agregar/Cambiar Cupón\n0. Salir\nCupón actual: ");
							if (activeCoupon == null) {
								view.showCurrentLineMessage(" Ninguno");
							} else {
								view.showCurrentLineMessage(activeCoupon.getName());
							}

							view.showMessage(
									". Precio: "
											+ calculate.calculatePriceAfterDiscount(activeCoupon, vehicle.getPrice())
											+ ".\nTu saldo: " + user.getWallet());

							option = readOption(true, 2);
							if (option <= 0 || option > 2) {
								if (option != 0) {
									continue;
								}
								break;
							}
							if (option == 2) {
								Coupon[] availableCoupons = calculate.findAvailableCoupons(user, vehicle,
										vehicle.getRouteSeq(),
										new Route[] { vehicle.getRouteSeq().getRoutes()[routeNumberEntry],
												vehicle.getRouteSeq().getRoutes()[routeNumberExit] });
								if (availableCoupons.length == 0) {
									view.showMessage("No hay cupones aplicables en este momento\n");
								} else {
									view.showMessage("Estos son los cupones disponibles para ti");
									showCoupons(availableCoupons,
											vehicle.getPrice(), false);
									view.showMessage("Seleccione el cupón que desea aplicar");

								}
								view.showMessage("-1. Para redimir un cupón con una clave");
								option = readOption(true, availableCoupons.length) - 1;
								if (option <= -3 || option == -1 || option >= availableCoupons.length) {
									option = -3;
									continue;
								}
								if (option == -2) {
									view.showMessage("Escriba la palabra para redimir el cupón");
									String redeemingWord = view.readData();
									Coupon redeemCoupon = calculate.searchCouponByWord(redeemingWord);
									if (redeemCoupon == null) {
										view.showMessage("El cupón es inexistente\n");
										option = -3;
										continue;
									}
									boolean exists = false;
									for (Coupon coupon : availableCoupons) {
										if (redeemCoupon.equals(coupon)) {
											activeCoupon = redeemCoupon;
											exists = true;
										}
									}
									if (!exists)
										view.showMessage("El cupón no está disponible o es inexistente\n");
								} else {
									activeCoupon = availableCoupons[option];
								}
								view.showMessage("Ha seleccionado el cupón: " + activeCoupon.getName());
								option = -1;
								continue;
							}
							option = 0;
						} while (option != 0);

						if (calculate.isEnoughMoney(userWallet, activeCoupon, vehicle)) {
							calculate.createTicket(user, userWallet, activeCoupon, vehicle,
									new Route[] { vehicle.getRouteSeq().getRoutes()[routeNumberEntry],
											vehicle.getRouteSeq().getRoutes()[routeNumberExit] });
							view.showMessage("Gracias por usar nuestros servicios!\n");
							int ticketNumber = 0;
							for (int i = 0; i < user.getTicketHistory().length; i++) {
								if (user.getTicketHistory()[i] == null) {
									ticketNumber = i - 1;
									break;
								}
							}
							showBill(user, user.getTicketHistory()[ticketNumber]);
						} else {
							view.showMessage("Su saldo no es suficiente para realizar la transacción\n");
						}
					}
				}
			}
		} while (option != 0);
		option = 1;
	}

	public void showCreateSubscriptionMenu(User user) {
		view.showMessage("1. "
				+ Vehicle.Type.AIRPLANE.getUpperCaseName() + "\n2. "
				+ Vehicle.Type.BUS.getUpperCaseName() + "\n3. "
				+ Vehicle.Type.SHIP.getUpperCaseName() + "\n4. "
				+ Vehicle.Type.TRAVEL_BUS.getUpperCaseName()
				+ "\n0. Salir");
		int vehicleTypeInt = readOption(true, 4) - 1;
		if (vehicleTypeInt <= -1 || vehicleTypeInt >= 3) {
			return;
		}
		Vehicle.Type type = calculate.convertIntToVehicleType(vehicleTypeInt);
		int routeNumberEntry, routeNumberExit, vehicleNumber;
		Vehicle[] vehicles = calculate.getDataCenter().getVehicles()[type.ordinal()];
		view.showMessage(
				"¿En qué día de la semana deseas agregar a tu horario?\n1. Lunes\n2. Martes\n3. Miércoles\n4. Jueves\n5. Viernes\n6. Sábado\n7. Domingo\n0. Salir");

		option = readOption(true, 7);
		if (option <= 0 || option > 7) {
			return;
		}

		view.showMessage("Estos son los " + type.getPluralName()
				+ " disponibles para el día de la semana que seleccionaste, digite el número del bus para ver más detalles");
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
		Vehicle vehicle = calculate.getDataCenter().getVehicles()[type.ordinal()][vehicleNumber];
		calculate.createSubscription(user, calculate.readLaboralDays(new int[] { option })[0], vehicle,
				new Route[] { vehicle.getRouteSeq().getRoutes()[routeNumberEntry],
						vehicle.getRouteSeq().getRoutes()[routeNumberExit] });
		view.showMessage("La suscripción se ha creado correctamente");
	}

	public void showCreateVehicleMenu(Vehicle.Type type, Company company) {
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

			int limit = company.getRouteSeqs().length - 1;

			routeNumber = readOption(false, limit) - 1;
			if (routeNumber <= -1 || routeNumber >= limit) {
				if (routeNumber >= limit) {
					view.showMessage("La ruta seleccionada no existe");
				}
				break;
			}
			try {
				for (int i = 0; i < company.getRouteSeqs()[routeNumber].getRoutes().length; i++) {
					view.showMessage(company.getRouteSeqs()[routeNumber].getRoutes()[i].getName() + "\nInicio: "
							+ company.getRouteSeqs()[routeNumber].getRoutes()[i].getStops()[Route.StopType.ENTRY
									.ordinal()]
							+ "\nFin: "
							+ company.getRouteSeqs()[routeNumber].getRoutes()[i].getStops()[Route.StopType.EXIT
									.ordinal()]
							+ "\n");
				}
			} catch (NullPointerException ex) {
				view.showMessage("La ruta seleccionada es inexistente, por favor, inténtelo de nuevo");
				option = -1;
				continue;
			}
			view.showMessage("Digite 1 para seleccionar esta ruta, 0 para regresar");
			option = readOption(true, 1);
			if (option <= 0 || option > 1) {
				break;
			}
			calculate.createVehicle(type, company, plate, calculate.getDataCenter().getRouteSeqs()[routeNumber], price,
					capacity);
			view.showMessage("El " + type.getName() + " se ha creado correctamente\n");
		} while (option != 1);
	}

	public void showRoutes(Vehicle.Type type) {
		int vehicleNumber;
		Vehicle[] vehicles = calculate.getDataCenter().getVehicles()[type.ordinal()];
		String available = "Disponible";
		String unavailable = "No disponible";

		do {
			calculate.checkVehiclesAvailability(vehicles);
			view.showMessage("Seleccione un " + type.getName() + "\n0. Salir");
			for (int i = 0; i < vehicles.length; i++) {
				if (vehicles[i] != null) {
					view.showCurrentLineMessage(type.getUpperCaseName() + ": " + (i + 1) + "\n Ticket: "
							+ vehicles[i].getPrice() + "\nDisponibilidad: ");
					if (vehicles[i].getIsAvailable()) {
						view.showMessage(available);
					} else {
						view.showMessage(unavailable);
					}
				}
			}
			vehicleNumber = readOption(true, vehicles.length) - 1;
			if (vehicleNumber <= -1 || vehicleNumber > vehicles.length - 1) {
				break;
			}

			for (int i = 0; i < vehicles[vehicleNumber].getRouteSeq().getRoutes().length; i++) {
				if (vehicles[vehicleNumber].getRouteSeq().getRoutes()[i] != null) {
					view.showCurrentLineMessage("\nRuta " + (i + 1) + "\n"
							+ vehicles[vehicleNumber].getRouteSeq().getRoutes()[i].getName() + "\n Entrada: "
							+ vehicles[vehicleNumber].getRouteSeq().getRoutes()[i].getStops()[Route.StopType.ENTRY
									.ordinal()]
							+ "\n Salida: "
							+ vehicles[vehicleNumber].getRouteSeq().getRoutes()[i].getStops()[Route.StopType.EXIT
									.ordinal()]
							+ "\n Disponibilidad: ");
					if (vehicles[vehicleNumber].getRouteSeq().getRoutes()[i].getIsAvailable()) {
						view.showMessage(available);
					} else {
						view.showMessage(unavailable);
					}
				}
			}
			view.showMessage("\n1 Regresar, 0 Salir");
			option = readOption(false, 1);
			if (option <= 0 || option > 1) {
				break;
			}
		} while (option != 0);
		option = -1;
	}

	public Vehicle showSelectVehicleMenu(boolean isAvailableFilter) {
		view.showMessage("1. " + Vehicle.Type.AIRPLANE.getUpperCaseName() + "\n2. "
				+ Vehicle.Type.BUS.getUpperCaseName()
				+ "\n3. " + Vehicle.Type.SHIP.getUpperCaseName() + "\n4. " + Vehicle.Type.TRAVEL_BUS.getUpperCaseName()
				+ "\n0. Salir");

		int vehicleTypeInt = readOption(false, 4) - 1;
		if (vehicleTypeInt <= -1 || vehicleTypeInt > 3) {
			return null;
		}

		Vehicle.Type type = calculate.convertIntToVehicleType(vehicleTypeInt);

		Vehicle[] vehicles = calculate.getDataCenter().getVehicles()[type.ordinal()];

		do {
			view.showMessage("\nDigite el número del " + type.getName() + " para ver más información");
			if (isAvailableFilter) {
				calculate.checkVehiclesAvailability(vehicles);
				view.showMessage("Los " + type.getPluralName() + " disponibles son los siguientes:");
			}
			view.showMessage("0. Cancelar");
			for (int i = 0; i < vehicles.length; i++) {
				if (vehicles[i] != null) {
					if (isAvailableFilter && !vehicles[i].getIsAvailable()) {
						continue;
					}
					view.showMessage(type.getUpperCaseName() + " " + (i + 1) + "   "
							+ vehicles[i].getCompany().getName() + "\n Ticket: " + vehicles[i].getPrice());
				}
			}
			int vehicleNumber = readOption(false, vehicles.length - 1) - 1;
			if (vehicleNumber <= -1 || vehicleNumber >= vehicles.length) {
				break;
			}

			calculate.checkVehicleAvailability(vehicles[vehicleNumber]);
			if (vehicles[vehicleNumber] == null) {
				view.showMessage("El " + type.getName() + " seleccionado es inexistente");
				continue;
			} else if (isAvailableFilter && !vehicles[vehicleNumber].getIsAvailable()) {
				view.showMessage("El " + type.getName() + " seleccionado no está disponible");
				continue;
			}
			return vehicles[vehicleNumber];
		} while (option != 0);
		return null;
	}

	public void showStatistics(Company company, Value value) {
		calculate.checkCompanyRevenue(company);

		view.showMessage("Ingresos: " + company.getRevenue()[Value.GENERAL.getValue()] + "\n Anuales:    "
				+ company.getRevenue()[Value.YEARLY.getValue()] + "\n Mensuales: "
				+ company.getRevenue()[Value.MONTHLY.getValue()] + "\n Diarios:   "
				+ company.getRevenue()[Value.DAILY.getValue()]);
		do {
			view.showMessage("\n-2 Informe Completo\n-1. Informe completo (Sin Mostrar Tickets)\n"
					+ (Vehicle.Type.AIRPLANE.ordinal() + 1) + "." + Vehicle.Type.AIRPLANE.getUpperCaseName() + "\n"
					+ (Vehicle.Type.BUS.ordinal() + 1) + "." + Vehicle.Type.BUS.getUpperCaseName() + "\n"
					+ (Vehicle.Type.SHIP.ordinal() + 1) + "." + Vehicle.Type.SHIP.getUpperCaseName() + "\n"
					+ (Vehicle.Type.TRAVEL_BUS.ordinal() + 1) + "." + Vehicle.Type.TRAVEL_BUS.getUpperCaseName()
					+ "\n0. Salir");

			int reportType = readOption(true, 4) - 1;
			if (reportType < -3 || reportType == -1 || reportType > 3) {
				break;
			}

			int repeat = 1;
			int vehicleTypeNumber = reportType;
			if (reportType == -3 || reportType == -2) {
				vehicleTypeNumber = 0;
				repeat = 4;
			}

			for (int r = 0; r < repeat; r++, vehicleTypeNumber++) {
				view.showMessage(calculate.convertIntToVehicleType(vehicleTypeNumber).getUpperCaseName() + "\n");
				for (int i = 0; i < calculate.getDataCenter().getVehicles()[vehicleTypeNumber].length; i++) {
					Vehicle vehicle = calculate.getDataCenter().getVehicles()[vehicleTypeNumber][i];

					if (vehicle != null) {
						view.showMessage((i + 1) + ". " + vehicle.getPlate() + "\nGanancias Totales: "
								+ vehicle.getRevenue()[Value.GENERAL.getValue()][Value.REVENUE.getValue()]
								+ "\nGanancias Anuales: "
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
											+ vehicle.getTickets()[j].getRoutes()[Route.StopType.ENTRY.ordinal()]
													.getStops()[Route.StopType.ENTRY.ordinal()]
											+ ". Salida: "
											+ vehicle.getTickets()[j].getRoutes()[Route.StopType.EXIT.ordinal()]
													.getStops()[Route.StopType.EXIT.ordinal()]
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

	public String buildRouteSeqInfo(RouteSequence routeSeq) {
		StringBuilder text = new StringBuilder();
		text.append(routeSeq.getName()).append(". Días laborales: ");
		for (int i = 0; i < routeSeq.getLaboralDays().length; i++) {
			if (routeSeq.getLaboralDays() != null)
				text.append(routeSeq.getLaboralDays()[i]);
		}
		return text.toString();
	}

	public String buildRouteSeqDetailedInfo(RouteSequence routeSeq) {
		StringBuilder text = new StringBuilder();
		text.append(buildRouteSeqInfo(routeSeq));
		for (int i = 0; i < routeSeq.getRoutes().length; i++) {
			if (routeSeq.getRoutes()[i] != null)
				text.append("\n").append(i + 1).append(". ").append(buildRouteInfo(routeSeq.getRoutes()[i]))
						.append("\n");
		}
		return text.toString();
	}

	public String buildRouteInfo(Route route) {
		StringBuilder text = new StringBuilder();
		text.append(route.getName()).append("\nEntrada: ").append(route.getStops()[Route.StopType.ENTRY.ordinal()])
				.append(". Salida: ").append(route.getStops()[Route.StopType.EXIT.ordinal()]);
		return text.toString();
	}

	public String buildVehInfo(Vehicle vehicle) {
		StringBuilder text = new StringBuilder();
		text.append(vehicle.getPlate()).append(" Ticket: ").append(vehicle.getPrice());
		return text.toString();
	}

	public String buildCouponInfo(Coupon coupon) {
		StringBuilder text = new StringBuilder();
		text.append(coupon.getId()).append("Tipo: ").append(coupon.getType()).append("Descuento: ")
				.append(coupon.getDiscount()).append(" ").append(coupon.getDiscountType())
				.append(coupon.getDiscountType()).append("\n Redenciones: ")
				.append(coupon.getRedeems()[Coupon.RedeemType.CURRENT.ordinal()]).append("/")
				.append(Coupon.RedeemType.MAXIMUM.ordinal()).append(". Aplica: ").append(coupon.getApplicable());
		return text.toString();
	}

	public String buildDetailedCouponInfo(Coupon coupon) {
		StringBuilder text = new StringBuilder();
		text.append(buildCouponInfo(coupon));
		switch (coupon.getApplicable()) {
			case Coupon.AppliesTo.VEHICLES:
				for (int i = 0; i < coupon.getApplicableVehicles().length; i++)
					text.append(i + 1).append(". ").append(buildVehInfo(coupon.getApplicableVehicles()[i]))
							.append("\n");
				break;
			case Coupon.AppliesTo.ROUTE_SEQS:
				for (int i = 0; i < coupon.getApplicableVehicles().length; i++)
					text.append(i + 1).append(". ");
				break;
			case Coupon.AppliesTo.ROUTES:
				for (int i = 0; i < coupon.getApplicableVehicles().length; i++)
					text.append(i + 1).append(". ");
				break;
		}
		return text.toString();
	}

	public Vehicle.Type selVehType() {
		StringBuilder text = new StringBuilder();
		text.append("Selecciona el tipo de vehículo\n1. ").append(Vehicle.Type.AIRPLANE.getUpperCaseName())
				.append("\n2. ")
				.append(Vehicle.Type.BUS.getUpperCaseName()).append("\n3. ")
				.append(Vehicle.Type.SHIP.getUpperCaseName())
				.append("\n4. ").append(Vehicle.Type.TRAVEL_BUS.getUpperCaseName());
		view.showMessage(text.toString());
		int vehTypeN = readOption(true, 4) - 1;
		if (vehTypeN <= -1 || vehTypeN > 3) {
			return null;
		}
		return calculate.convertIntToVehicleType(vehTypeN);
	}

	public void showVehInfo(Vehicle vehicle, boolean availability, boolean detailed) {
		StringBuilder text = new StringBuilder();
		text.append(vehicle.getPlate()).append("Empresa: ").append(vehicle.getCompany().getName()).append("\nTicket: ")
				.append(vehicle.getPrice());
		if (availability && vehicle.getIsAvailable())
			text.append(". Disponible");
		if (availability && !vehicle.getIsAvailable())
			text.append(". No Disponible");
		if (detailed) {
			text.append("Ruta: ").append(vehicle.getRouteSeq().getName()).append("\n").append("\nCapacidad: ")
					.append(vehicle.getCapacity()[Value.CURRENT.ordinal()]).append("/")
					.append(vehicle.getCapacity()[Value.MAXIMUM.ordinal()]);
		}
		view.showMessage(text.toString());
	}

	public void run() {
		boolean cancelate = false;
		int subscriptionNumber;
		int stopsNumber = 0;
		int vehicleTypeInt;
		do {
			view.showMessage(
					"Bienvenido a mi sistema de transporte masivo\n\nSeleccione la opción que más le convenga: \n1. Iniciar sesión\n2. Ver rutas\n3. Registrarse\n4. Soy una empresa\n0. Salir");

			option = readOption(false, 5);
			if (option <= 0 || option > 5) {
				if (option == 0)
					break;
				continue;
			}
			switch (option) {
				case 1:
					view.showMessage("Ingrese su usuario\n0. Cancelar");
					String name = view.readData();
					if (name.equals("0")) {
						view.showMessage("Se ha cancelado la operación");
						break;
					}
					view.showMessage("Ingrese su contraseña");
					String password = view.readData();
					if (calculate.logIn(User.Type.USER, name, password)) {
						int userNumber = calculate.searchUserArrayNumber(User.Type.USER, name);
						User currentUser = calculate.getDataCenter().getUsers()[User.Type.USER.ordinal()][userNumber];
						view.showMessage("Su número de usuario es: " + userNumber);
						calculate.checkSubscriptionsPayment(currentUser);
						do {
							view.showMessage(
									"¿Qué desea hacer?\n1. Comprar ticket\n2. Suscripciones\n3. Mi billetera\n4. Ver historial y estado de mis tickets\n5. Familia y Amigos\n6. Perfil\n0. Cerrar sesión");

							option = readOption(false, 6);
							if (option <= 0 || option > 6) {
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

									option = readOption(true, 2);
									if (option <= 0 || option > 2) {
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
													view.showMessage("Suscripción activa Nro " + (i + 1)
															+ "\nVehículo: "
															+ currentUser.getSubscriptions()[i].getVehicle().getType()
																	.getUpperCaseName()
															+ " "
															+ currentUser.getSubscriptions()[i].getVehicle().getPlate()
															+ "\nEmpresa: "
															+ currentUser.getSubscriptions()[i].getVehicle()
																	.getCompany()
															+ "\nEntrada: "
															+ currentUser.getSubscriptions()[i]
																	.getRoutes()[Route.StopType.ENTRY.ordinal()]
																	.getStopsName()[Route.StopType.ENTRY.ordinal()]
															+ "  "
															+ currentUser.getSubscriptions()[i]
																	.getRoutes()[Route.StopType.ENTRY.ordinal()]
																	.getStops()[Route.StopType.ENTRY.ordinal()]
															+ "\nSalida: "
															+ currentUser.getSubscriptions()[i]
																	.getRoutes()[Route.StopType.EXIT.ordinal()]
																	.getStopsName()[Route.StopType.EXIT.ordinal()]
															+ "  "
															+ currentUser.getSubscriptions()[i]
																	.getRoutes()[Route.StopType.EXIT.ordinal()]
																	.getStops()[Route.StopType.EXIT.ordinal()]
															+ "\nPrecio: "
															+ currentUser.getSubscriptions()[i].getVehicle()
																	.getPrice());
													hasActiveSubscriptions = true;
												}
												if (hasActiveSubscriptions == false) {
													view.showMessage("No tienes suscripciones Activas\n");
													break;
												}
											}
											view.showMessage(
													"\nDigite el número de una de tus suscripciones activas para cancelarla\n0. Salir");

											subscriptionNumber = readOption(true,
													currentUser.getSubscriptions().length - 1) - 1;
											if (subscriptionNumber <= -1 || subscriptionNumber >= currentUser
													.getSubscriptions().length) {
												break;
											}

											if (currentUser.getSubscriptions()[subscriptionNumber] != null) {
												view.showMessage("Has seleccionado la suscripción número "
														+ (subscriptionNumber + 1)
														+ "\n1. Cancelar Suscripción\n0. Volver");

												option = readOption(true, 1);
												if (option <= 0 || option > 1) {
													break;
												}
												calculate.deleteSubscription(currentUser, subscriptionNumber);
												view.showMessage(
														"La suscripción se ha cancelado correctamente\n");
												break;
											} else {
												view.showMessage("Has seleccionado una suscripción inválida");
												break;
											}
										case 2:
											view.showMessage(
													"¿Para qué tipo de vehículo deseas crear tu suscripción?\nSuscríbete y así compramos automáticamente tus tickets\n");
											showCreateSubscriptionMenu(currentUser);
											break;
									}
									break;
								case 3:
									view.showMessage("Su saldo actual es: " + currentUser.getWallet()
											+ " pesos\nDigite 1 para agregar fondos, 0 para volver");
									option = readOption(true, 1);
									if (option <= 0 || option > 1) {
										break;
									}
									view.showMessage(
											"Cuánto dinero desea agregar? Agregue 0 para cancelar la operación");
									int userAddMoney = view.readNumber();
									if (userAddMoney != 0) {
										calculate.addWallet(currentUser, userAddMoney);
										view.showMessage("Se ha agregado correctamente la cantidad de "
												+ userAddMoney
												+ " pesos\nNuevo saldo disponible: " + currentUser.getWallet());
									} else {
										view.showMessage("Se ha cancelado la operación");
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

									option = readOption(true, 2);
									if (option <= 0 || option > 2) {
										break;
									}

									switch (option) {
										case 1:
											view.showMessage("Digite el número de un usuario para ver más opciones");
											for (int i = 0; i < currentUser.getRelationships().length; i++) {
												if (currentUser.getRelationships()[i] != null) {
													view.showMessage(
															(i + 1) + ". Nombre: "
																	+ currentUser.getRelationships()[i].getName());
												}
											}
											view.showMessage("0. Salir");
											int relationshipUserNumber = 0;

											relationshipUserNumber = readOption(true,
													currentUser.getRelationships().length - 1) - 1;
											if (relationshipUserNumber <= -1 || relationshipUserNumber >= currentUser
													.getRelationships().length) {
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

											option = readOption(true, 2);
											if (option <= 0 || option > 2) {
												break;
											}

											switch (option) {
												case 1:
													showCreateTicketMenu(relationship, currentUser);
													break;
												case 2:
													view.showMessage("Está seguro de que desea eliminar el usuario "
															+ relationship.getName()
															+ " de su lista de amigos?\n1. Sí 0. No");

													option = readOption(true, 1);
													if (option <= 0 || option > 1) {
														break;
													}
													currentUser.getRelationships()[relationshipUserNumber] = null;
													view.showMessage(
															"Se ha eliminado al usuario de su lista de amigos");
													break;
											}
											break;
										case 2:
											view.showMessage("Digite el nombre del usuario a agregar");
											String userRelationshipName = view.readData();
											if (!calculate.isUserAvailable(userRelationshipName)) {
												calculate.createRelationship(currentUser,
														calculate.getDataCenter().getUsers()[User.Type.USER
																.ordinal()][calculate
																		.searchUserArrayNumber(User.Type.USER,
																				userRelationshipName)]);
												view.showMessage(
														"Se ha añadido a su lista de amigos el usuario "
																+ userRelationshipName);
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

									option = readOption(true, 1);
									if (option <= 0 || option > 1) {
										break;
									}
									view.showMessage("1. Cambiar Usuario\n2. Cambiar Contraseña\n0. Volver");

									option = readOption(true, 2);
									if (option <= 0 || option > 2) {
										break;
									}
									switch (option) {
										case 0:
											view.showMessage("Se ha cancelado la operación");
											break;
										case 1:
											do {
												view.showMessage(
														"Digite su nuevo usuario, usuario actual: "
																+ currentUser.getName());
												String newUserName = view.readData();
												if (calculate.isUserAvailable(newUserName)) {
													view.showMessage("Su nuevo usuario será: " + newUserName
															+ "\n1. Confirmar\n0. Cancelar");

													option = readOption(true, 1);
													if (option <= 0 || option > 1) {
														break;
													}
													currentUser.setName(newUserName);
													view.showMessage("Se ha cancelado la operación");
												} else {
													view.showMessage(
															"El nombre de usuario no está disponible, por favor inténtelo de nuevo\n");
													option = 1;
													continue;
												}
											} while (option != 0);
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
												view.showMessage(
														"Las contraseñas no coinciden, se ha cancelado la operación");
												option = -1;
												break;
											}
											break;
									}
									break;
							}
							option = -1;
						} while (option != 0);
					} else {
						view.showMessage("Ha ocurrido un error al intentar iniciar sesión");
					}
					break;
				case 2:
					view.showMessage("¿De cuál vehículo desea consultar las rutas?\n1. "
							+ Vehicle.Type.AIRPLANE.getUpperCaseName() + "\n2. " + Vehicle.Type.BUS.getUpperCaseName()
							+ "\n3. " + Vehicle.Type.SHIP.getUpperCaseName() + "\n4. "
							+ Vehicle.Type.TRAVEL_BUS.getUpperCaseName() + "\n0. Salir");

					vehicleTypeInt = readOption(false, 4) - 1;
					if (vehicleTypeInt <= -1 || vehicleTypeInt > 3) {
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
						calculate.createUser(User.Type.USER, name, password);
						view.showMessage("Se ha registrado correctamente");
					} else {
						view.showMessage("El nombre de usuario digitado ya está en uso");
						break;
					}
					break;
				case 4:
					view.showMessage("1. Iniciar sesión\n2. Registrarse\n0. Salir");

					option = readOption(true, 2);
					if (option <= 0 || option > 2) {
						break;
					}

					switch (option) {
						case 1:
							view.showMessage("Digite el nombre de la empresa");
							name = view.readData();
							view.showMessage("Digite su contraseña");
							password = view.readData();
							if (calculate.logIn(User.Type.COMPANY, name, password)) {
								Company company = (Company) calculate.getDataCenter()
										.getUsers()[User.Type.COMPANY
												.ordinal()][calculate.searchUserArrayNumber(User.Type.COMPANY, name)];
								do {
									view.showMessage(
											"¿Qué desea hacer?\n1. Datos financieros\n2. Cupones\n3. Vehículos\n4. Crear Ruta\n0. Cerrar sesión");

									option = readOption(false, 4);
									if (option <= 0 || option > 4) {
										if (option == 0) {
											view.showMessage("Se ha cerrado sesión de manera exitosa");
											break;
										}
										continue;
									}
									switch (option) {
										case 1:
											view.showMessage(
													company.getName() + " sus ingresos son los siguientes:");
											showStatistics(company, Value.GENERAL);
											break;
										case 2:
											view.showMessage(
													"1. Manejar Cupones\n2. Crear Cupón\n3. Eliminar Cupón\n0. Salir");
											option = readOption(true, 3);
											if (option <= 0 || option > 3) {
												break;
											}
											switch (option) {
												case 1:
													view.showMessage(
															"Digite el número del cupón a seleccionar\n0. Salir");
													showCoupons(company.getCoupons(), 0, true);
													int couponNumber = readOption(true, company.getCoupons().length)
															- 1;
													if (couponNumber <= -1
															|| couponNumber > company.getCoupons().length) {
														break;
													}
													Coupon coupon = company.getCoupons()[couponNumber];

													view.showMessage(
															"1. Edición Completa\n2. Editar Descuento\n3. Editar objetos aplicables\n4. Editar privacidad\n5. Editar máximas redenciones\n6. Eliminar\n0. Salir");
													option = readOption(true, 6);
													if (option <= 0 || option > 6) {
														break;
													}
													boolean general = false;
													switch (option) {
														case 1:
															view.showMessage(
																	"Digite 0 en cualquier momento para cancelar la operación");
															general = true;
														case 2:
															view.showCurrentLineMessage(
																	"Descuento actual:\n" + coupon.getDiscountType()
																			+ ": " + coupon.getDiscount());
															if (coupon.getDiscountType()
																	.equals(RedeemedCoupon.DiscountType.PERCENTAGE)) {
																view.showMessage("%");
															}
															view.showMessage(
																	"Digite el valor que desee\n1. Descuento por cantidad\n2. Descuento por porcentaje");
															RedeemedCoupon.DiscountType discountType = RedeemedCoupon.DiscountType.QUANTITY;
															option = readOption(true, 2);
															if (option <= 0 || option > 2) {
																break;
															}
															if (option == 2) {
																discountType = RedeemedCoupon.DiscountType.PERCENTAGE;
															}
															view.showMessage("Digite el valor de descuento");
															option = readOption(true, Integer.MAX_VALUE);
															if (option <= 0 || option > Integer.MAX_VALUE) {
																break;
															}
															coupon.setDiscountType(discountType);
															coupon.setDiscount(option);
															view.showMessage(
																	"Se ha modificado el cupón correctamente!\nNuevo descuento: "
																			+ coupon.getDiscountType() + ": "
																			+ coupon.getDiscount());
															if (!general) {
																view.showMessage("Digite cualquier tecla para salir");
																view.readData();
																break;
															}
														case 3:
															view.showMessage(
																	"Para editar los objetos aplicables, por favor, seleccione una de las siguientes opciones:\n1. Vehículos.\n2. Secuencias de Rutas.\n3. Rutas");
															option = readOption(true, 3);
															if (option <= 0 || option > 3) {
																break;
															}
															Coupon.AppliesTo appliesTo = null;
															Vehicle[] vehicles = null;
															RouteSequence[] routeSeqs = null;
															Route[] routes = new Route[2];
															switch (option) {
																case 1:
																	Vehicle.Type vehType = selVehType();
																	view.showMessage(
																			"Seleccione los vehículos a incluir en el descuento del cupón\n-1. Terminar\n0. Cancelar");
																	for (int i = 0; i < company.getVehicles()[vehType
																			.ordinal()].length; i++) {
																		Vehicle vehicle = company.getVehicles()[vehType
																				.ordinal()][i];
																		if (vehicle != null) {
																			StringBuilder text = new StringBuilder();
																			text.append(i + 1).append(". ")
																					.append(buildVehInfo(vehicle));
																			view.showMessage(text.toString());
																		}
																	}
																	ArrayList<Vehicle> vehiclesList = new ArrayList<Vehicle>();
																	ArrayList<Integer> vehicleNumbers = new ArrayList<Integer>();
																	do {
																		option = readOption(false,
																				company.getVehicles()[vehType
																						.ordinal()].length - 1)
																				- 1;
																		if (option <= -3 || option > company
																				.getVehicles()[vehType.ordinal()].length
																				- 1) {
																			view.showMessage(
																					"Número inválido, por favor inténtelo de nuevo");
																			continue;
																		}
																		if (option == -1) {
																			view.showMessage(
																					"Se ha cancelado la operación\n");
																			option = 0;
																			break;
																		}
																		if (option == -2) {
																			if (vehiclesList.size() == 0) {
																				view.showMessage(
																						"Selecciona al menos un vehículo o digite 0 para cancelar");
																				continue;
																			}
																			view.showMessage("Se ha terminado de leer");
																			break;
																		}
																		if (company
																				.getVehicles()[vehType
																						.ordinal()][option] == null) {
																			view.showMessage(
																					"El vehículo seleccionado es inexistente");
																			continue;
																		}
																		boolean repeated = false;
																		for (int i = 0; i < vehicleNumbers
																				.size(); i++) {
																			if (vehicleNumbers.get(i) == option) {
																				view.showMessage(
																						"El vehículo ya ha sido seleccionado, inténtelo de nuevo");
																				repeated = true;
																				break;
																			}
																		}
																		if (repeated)
																			continue;
																		vehiclesList.add(company.getVehicles()[vehType
																				.ordinal()][option]);
																		vehicleNumbers.add(option);
																		StringBuilder text = new StringBuilder();
																		text.append("Se ha añadido el vehículo: ")
																				.append(buildVehInfo(
																						company.getVehicles()[vehType
																								.ordinal()][option]));
																		view.showMessage(text.toString());
																		option = -1;
																	} while (option != -2);
																	if (option == 0)
																		break;
																	vehicles = vehiclesList
																			.toArray(new Vehicle[vehiclesList.size()]);
																	appliesTo = Coupon.AppliesTo.VEHICLES;
																	option = -1;
																	break;
																case 2:
																	ArrayList<RouteSequence> routeSeqsList = new ArrayList<RouteSequence>();
																	ArrayList<Integer> routeSeqNumbers = new ArrayList<Integer>();
																	do {
																		view.showMessage(
																				"\nEstas son sus secuencias de rutas actuales\nSeleccione alguna para ver más información\n-1. Terminar");
																		for (int i = 0; i < company
																				.getRouteSeqs().length; i++) {
																			if (company.getRouteSeqs()[i] != null)
																				view.showMessage((i + 1) + ". "
																						+ buildRouteSeqInfo(
																								company.getRouteSeqs()[i]));
																		}
																		int routeSeqNumber = readOption(false,
																				company.getRouteSeqs().length - 1) - 1;
																		if (routeSeqNumber < -2
																				|| routeSeqNumber >= company
																						.getRouteSeqs().length) {
																			view.showMessage(
																					"Número inválido, por favor inténtelo de nuevo");
																			continue;
																		}
																		if (routeSeqNumber == -1) {
																			view.showMessage(
																					"Se ha cancelado la operación\n");
																			option = 0;
																			break;
																		}
																		if (routeSeqNumber == -2) {
																			if (routeSeqsList.size() == 0) {
																				view.showMessage(
																						"Selecciona al menos una secuencia o digite 0 para cancelar");
																				continue;
																			}
																			view.showMessage("Se ha terminado de leer");
																			break;
																		}
																		if (company
																				.getRouteSeqs()[routeSeqNumber] == null) {
																			view.showMessage(
																					"La secuencia seleccionada es inexistente");
																			continue;
																		}
																		boolean repeated = false;
																		for (int i = 0; i < routeSeqNumbers
																				.size(); i++) {
																			if (routeSeqNumbers
																					.get(i) == routeSeqNumber) {
																				view.showMessage(
																						"La secuencia ya ha sido seleccionada, inténtelo de nuevo");
																				repeated = true;
																				break;
																			}
																		}
																		if (repeated)
																			continue;

																		view.showMessage(buildRouteSeqDetailedInfo(
																				company.getRouteSeqs()[routeSeqNumber])
																				+ "1. Seleccionar. 2. Volver. 0. Salir");
																		option = readOption(true, 2);
																		if (option <= 0 || option > 2) {
																			option = 0;
																			break;
																		}
																		if (option == 2)
																			continue;
																		routeSeqsList.add(
																				company.getRouteSeqs()[routeSeqNumber]);
																		routeSeqNumbers.add(routeSeqNumber);
																		view.showMessage("Se ha añadido la ruta "
																				+ company.getRouteSeqs()[routeSeqNumber]
																						.getName()
																				+ " exitosamente");
																	} while (option != 0);
																	if (option == 0)
																		break;
																	routeSeqs = routeSeqsList.toArray(
																			new RouteSequence[routeSeqsList.size()]);
																	appliesTo = Coupon.AppliesTo.ROUTE_SEQS;
																	option = -1;
																	break;
																case 3:
																	view.showMessage(
																			"Estas son sus rutas actuales\nSeleccione alguna para ver más información");
																	for (int i = 0; i < company
																			.getRouteSeqs().length; i++) {
																		if (company.getRouteSeqs()[i] != null)
																			view.showMessage(
																					(i + 1) + ". " + buildRouteSeqInfo(
																							company.getRouteSeqs()[i]));
																	}
																	view.showMessage("");
																	int routeSeqNumber = readOption(true,
																			company.getRouteSeqs().length - 1) - 1;
																	if (routeSeqNumber <= -1
																			|| routeSeqNumber >= company
																					.getRouteSeqs().length) {
																		break;
																	}
																	view.showMessage(buildRouteSeqDetailedInfo(
																			company.getRouteSeqs()[routeSeqNumber])
																			+ "\nSeleccione la ruta de entrada");
																	int routeNumberEntry = readOption(true,
																			company.getRouteSeqs()[routeSeqNumber]
																					.getRoutes().length - 1)
																			- 1;
																	if (routeNumberEntry <= -1
																			|| routeNumberEntry >= company
																					.getRouteSeqs()[routeSeqNumber]
																					.getRoutes().length) {
																		break;
																	}
																	view.showMessage(
																			"Ahora, seleccione la ruta de salida");
																	int routeNumberExit = readOption(true,
																			company.getRouteSeqs()[routeSeqNumber]
																					.getRoutes().length - 1)
																			- 1;
																	if (routeNumberExit <= -1
																			|| routeNumberExit >= company
																					.getRouteSeqs()[routeSeqNumber]
																					.getRoutes().length) {
																		break;
																	}
																	if (routeNumberExit < routeNumberEntry) {
																		view.showMessage(
																				"La ruta de salida no puede ser anterior a la entrada");
																		break;
																	}

																	view.showMessage("Ha seleccionado:\n"
																			+ buildRouteInfo(company
																					.getRouteSeqs()[routeSeqNumber]
																					.getRoutes()[routeNumberEntry])
																			+ "\ny\n"
																			+ buildRouteInfo(
																					company.getRouteSeqs()[routeSeqNumber]
																							.getRoutes()[routeNumberExit])
																			+ "\ncomo las rutas aplicables\n1. Confirmar. 0. Cancelar");
																	option = readOption(true, 1);
																	if (option <= 0 || option > 1) {
																		break;
																	}
																	if (option == 1) {
																		routes[0] = company
																				.getRouteSeqs()[routeSeqNumber]
																				.getRoutes()[routeNumberEntry];
																		routes[1] = company
																				.getRouteSeqs()[routeSeqNumber]
																				.getRoutes()[routeNumberExit];

																		view.showMessage(
																				"Se han añadido las rutas exitosamente");
																	}
																	appliesTo = Coupon.AppliesTo.ROUTES;
																	option = -1;
																	break;
															}
															if (option == 0)
																break;
															coupon.setApplicable(appliesTo);
															coupon.setApplicableVehicles(vehicles);
															coupon.setApplicableRouteSeqs(routeSeqs);
															coupon.setApplicableRoutes(routes);
															calculate.setApplicableCouponObjects(coupon, vehicles, routeSeqs, routes);

															if (!general) {
																view.showMessage("Digite cualquier tecla para salir");
																view.readData();
																break;
															}
														case 4:
															view.showMessage("Privacidad actual: " + coupon.getType()
																	+ "\n1. Público\n2. Reservado");
															RedeemedCoupon.Type type = RedeemedCoupon.Type.PUBLIC;
															option = readOption(true, 2);
															if (option <= 0 || option > 2) {
																break;
															}
															if (option == 2) {
																type = RedeemedCoupon.Type.RESERVED;
																String redeemText;
																boolean isRedeemWordAvailable = false;
																do {
																	view.showMessage(
																			"Escriba la cadena de texto para redimir el cupón");
																	redeemText = view.readData();
																	if (redeemText.equals("0")) {
																		view.showMessage(
																				"Se ha cancelado la operación");
																		break;
																	}
																	isRedeemWordAvailable = calculate
																			.isRedeemWordAvailable(redeemText);
																	if (!isRedeemWordAvailable) {
																		view.showMessage(
																				"La cadena de texto no está disponible, por favor, inténtelo de nuevo con una distinta");
																		continue;
																	}
																	coupon.setRedeemWord(redeemText);
																} while (!isRedeemWordAvailable);
																coupon.setType(type);
																view.showCurrentLineMessage(
																		"Se ha modificado el cupón correctamente!\nNueva privacidad: "
																				+ coupon.getType());
																if (option == 2) {
																	view.showMessage(". Texto redimible: "
																			+ coupon.getRedeemWord());
																}
															}
															if (!general) {
																view.showMessage("Digite cualquier tecla para salir");
																view.readData();
																break;
															}
														case 5:
															view.showMessage(
																	"Qué desea editar?\n1. Máximas redenciones por usuario\n2. Máximas redenciones generales\n3. Ambas");
															option = readOption(true, 3);
															if (option <= 0 || option > 3) {
																break;
															}
															switch (option) {
																case 3:
																case 1:
																	view.showMessage(
																			"Digite la máxima cantidad de redenciones por usuario\nCantidad actual: "
																					+ coupon.getRedeems()[RedeemType.USER_MAXIMUM
																							.ordinal()]);
																	int maxUserRedeems = readOption(true,
																			Integer.MAX_VALUE);
																	if (maxUserRedeems <= 0
																			|| maxUserRedeems > Integer.MAX_VALUE) {
																		break;
																	}
																	coupon.setRedeems(new int[] {
																			coupon.getRedeems()[Coupon.RedeemType.USER
																					.ordinal()],
																			coupon.getRedeems()[Coupon.RedeemType.CURRENT
																					.ordinal()],
																			maxUserRedeems,
																			coupon.getRedeems()[Coupon.RedeemType.MAXIMUM
																					.ordinal()] });
																	view.showMessage(
																			"Se ha modificado el cupón correctamente!\n");
																	if (option != 3) {
																		break;
																	}

																case 2:
																	view.showMessage(
																			"Digite la máxima cantidad de redenciones\nCantidad actual: "
																					+ coupon.getRedeems()[RedeemType.MAXIMUM
																							.ordinal()]);
																	int maxRedeems = readOption(true,
																			Integer.MAX_VALUE);
																	if (maxRedeems <= 0
																			|| maxRedeems > Integer.MAX_VALUE) {
																		break;
																	}
																	coupon.setRedeems(new int[] {
																			coupon.getRedeems()[Coupon.RedeemType.USER
																					.ordinal()],
																			coupon.getRedeems()[Coupon.RedeemType.CURRENT
																					.ordinal()],
																			coupon.getRedeems()[Coupon.RedeemType.USER_MAXIMUM
																					.ordinal()],
																			maxRedeems });
																	break;
															}
															view.showMessage("Digite cualquier tecla para salir");
															view.readData();
															break;
														case 6:
															view.showMessage(
																	"Está seguro que desea eliminar el cupón "
																			+ coupon.getName() + "?\n1. Sí	0. No");
															option = readOption(true, 2);
															if (option <= 0 || option > 2) {
																view.showMessage("Se ha cancelado la operación");
																break;
															}
															coupon = null;
															view.showMessage("El cupón se ha eliminado exitosamente");
															break;
													}
													break;
												case 2:
													view.showMessage(
															"Escriba el nombre del cupón (Este no se usará para redimirlo)\nEscriba 0 en cualquier momento para cancelar la operación");
													String couponName = view.readData();
													if (couponName.equals("0")) {
														option = 0;
														break;
													}
													view.showMessage(
															"Escriba la descripción del cupón (Aquí se escriben los términos y condiciones)");
													String description = view.readData();
													if (description.equals("0")) {
														option = 0;
														break;
													}
													boolean isCumulative = false;
													/*
													 * view.showMessage("El cupón es acumulable?\n1. Sí. 2. No");
													 * option = readOption(true, 2);
													 * if (option <= 0 || option > 2) {
													 * break;
													 * }
													 * if (option == 1) {
													 * isCumulative = true;
													 * }
													 */
													view.showMessage("Privacidad del cupón\n1. Público\n2. Reservado");
													option = readOption(true, 2);
													if (option <= 0 || option > 2) {
														break;
													}
													RedeemedCoupon.Type couponType = RedeemedCoupon.Type.PUBLIC;
													String redeemWord = couponName;
													if (option == 2) {
														couponType = RedeemedCoupon.Type.RESERVED;
														boolean isRedeemWordAvailable = false;
														do {
															view.showMessage(
																	"Escriba la cadena de texto para redimir el cupón");
															redeemWord = view.readData();
															isRedeemWordAvailable = calculate
																	.isRedeemWordAvailable(redeemWord);
															if (!isRedeemWordAvailable) {
																view.showMessage(
																		"La cadena de texto no está disponible, por favor, inténtelo de nuevo con una distinta");
															}
														} while (!isRedeemWordAvailable);
													}
													view.showMessage(
															"Aplicable a:\n1. Vehículos\n2. Secuencias de Rutas\n3. Rutas");
													option = readOption(true, 3);
													if (option <= 0 || option > 3) {
														break;
													}
													Coupon.AppliesTo appliesTo = null;
													Vehicle[] vehicles = null;
													RouteSequence[] routeSeqs = null;
													Route[] routes = new Route[2];
													switch (option) {
														case 1:
															Vehicle.Type vehType = selVehType();
															view.showMessage(
																	"Seleccione los vehículos a incluir en el descuento del cupón\n-1. Terminar\n0. Cancelar");
															for (int i = 0; i < company.getVehicles()[vehType
																	.ordinal()].length; i++) {
																Vehicle vehicle = company.getVehicles()[vehType
																		.ordinal()][i];
																if (vehicle != null) {
																	StringBuilder text = new StringBuilder();
																	text.append(i + 1).append(". ")
																			.append(buildVehInfo(vehicle));
																	view.showMessage(text.toString());
																}
															}
															ArrayList<Vehicle> vehiclesList = new ArrayList<Vehicle>();
															ArrayList<Integer> vehicleNumbers = new ArrayList<Integer>();
															do {
																option = readOption(false,
																		company.getVehicles()[vehType.ordinal()].length
																				- 1)
																		- 1;
																if (option <= -3 || option > company
																		.getVehicles()[vehType.ordinal()].length - 1) {
																	view.showMessage(
																			"Número inválido, por favor inténtelo de nuevo");
																	continue;
																}
																if (option == -1) {
																	view.showMessage("Se ha cancelado la operación\n");
																	option = 0;
																	break;
																}
																if (option == -2) {
																	if (vehiclesList.size() == 0) {
																		view.showMessage(
																				"Selecciona al menos un vehículo o digite 0 para cancelar");
																		continue;
																	}
																	view.showMessage("Se ha terminado de leer");
																	break;
																}
																if (company
																		.getVehicles()[vehType
																				.ordinal()][option] == null) {
																	view.showMessage(
																			"El vehículo seleccionado es inexistente");
																	continue;
																}
																boolean repeated = false;
																for (int i = 0; i < vehicleNumbers.size(); i++) {
																	if (vehicleNumbers.get(i) == option) {
																		view.showMessage(
																				"El vehículo ya ha sido seleccionado, inténtelo de nuevo");
																		repeated = true;
																		break;
																	}
																}
																if (repeated)
																	continue;
																vehiclesList.add(company.getVehicles()[vehType
																		.ordinal()][option]);
																vehicleNumbers.add(option);
																StringBuilder text = new StringBuilder();
																text.append("Se ha añadido el vehículo: ")
																		.append(buildVehInfo(
																				company.getVehicles()[vehType
																						.ordinal()][option]));
																view.showMessage(text.toString());
																option = -1;
															} while (option != 0);
															if (option == 0)
																break;
															vehicles = vehiclesList
																	.toArray(new Vehicle[vehiclesList.size()]);
															appliesTo = Coupon.AppliesTo.VEHICLES;
															option = -1;
															break;
														case 2:
															ArrayList<RouteSequence> routeSeqsList = new ArrayList<RouteSequence>();
															ArrayList<Integer> routeSeqNumbers = new ArrayList<Integer>();
															do {
																view.showMessage(
																		"\nEstas son sus secuencias de rutas actuales\nSeleccione alguna para ver más información\n-1. Terminar");
																for (int i = 0; i < company
																		.getRouteSeqs().length; i++) {
																	if (company.getRouteSeqs()[i] != null)
																		view.showMessage(
																				(i + 1) + ". " + buildRouteSeqInfo(
																						company.getRouteSeqs()[i]));
																}
																int routeSeqNumber = readOption(false,
																		company.getRouteSeqs().length - 1) - 1;
																if (routeSeqNumber < -2 || routeSeqNumber >= company
																		.getRouteSeqs().length) {
																	view.showMessage(
																			"Número inválido, por favor inténtelo de nuevo");
																	continue;
																}
																if (routeSeqNumber == -1) {
																	view.showMessage("Se ha cancelado la operación\n");
																	option = 0;
																	break;
																}
																if (routeSeqNumber == -2) {
																	if (routeSeqsList.size() == 0) {
																		view.showMessage(
																				"Selecciona al menos una secuencia o digite 0 para cancelar");
																		continue;
																	}
																	view.showMessage("Se ha terminado de leer");
																	break;
																}
																if (company.getRouteSeqs()[routeSeqNumber] == null) {
																	view.showMessage(
																			"La secuencia seleccionada es inexistente");
																	continue;
																}
																boolean repeated = false;
																for (int i = 0; i < routeSeqNumbers.size(); i++) {
																	if (routeSeqNumbers.get(i) == routeSeqNumber) {
																		view.showMessage(
																				"La secuencia ya ha sido seleccionada, inténtelo de nuevo");
																		repeated = true;
																		break;
																	}
																}
																if (repeated)
																	continue;

																view.showMessage(buildRouteSeqDetailedInfo(
																		company.getRouteSeqs()[routeSeqNumber])
																		+ "1. Seleccionar. 2. Volver. 0. Salir");
																option = readOption(true, 2);
																if (option <= 0 || option > 2) {
																	option = 0;
																	break;
																}
																if (option == 2)
																	continue;
																if (option == 1) {
																	routeSeqsList.add(
																			company.getRouteSeqs()[routeSeqNumber]);
																	routeSeqNumbers.add(routeSeqNumber);
																	view.showMessage("Se ha añadido la ruta "
																			+ company.getRouteSeqs()[routeSeqNumber]
																					.getName()
																			+ " exitosamente");
																}
															} while (option != 0);
															if (option == 0)
																break;

															routeSeqs = routeSeqsList
																	.toArray(new RouteSequence[routeSeqsList.size()]);
															appliesTo = Coupon.AppliesTo.ROUTE_SEQS;
															option = -1;
															break;
														case 3:
															view.showMessage(
																	"Estas son sus rutas actuales\nSeleccione alguna para ver más información");
															for (int i = 0; i < company.getRouteSeqs().length; i++) {
																if (company.getRouteSeqs()[i] != null)
																	view.showMessage((i + 1) + ". " + buildRouteSeqInfo(
																			company.getRouteSeqs()[i]));
															}
															view.showMessage("");
															int routeSeqNumber = readOption(true,
																	company.getRouteSeqs().length - 1) - 1;
															if (routeSeqNumber <= -1 || routeSeqNumber >= company
																	.getRouteSeqs().length) {
																break;
															}
															view.showMessage(buildRouteSeqDetailedInfo(
																	company.getRouteSeqs()[routeSeqNumber])
																	+ "\nSeleccione la ruta de entrada");
															int routeNumberEntry = readOption(true,
																	company.getRouteSeqs()[routeSeqNumber]
																			.getRoutes().length - 1)
																	- 1;
															if (routeNumberEntry <= -1
																	|| routeNumberEntry >= company
																			.getRouteSeqs()[routeSeqNumber]
																			.getRoutes().length) {
																break;
															}
															view.showMessage("Ahora, seleccione la ruta de salida");
															int routeNumberExit = readOption(true,
																	company.getRouteSeqs()[routeSeqNumber]
																			.getRoutes().length - 1)
																	- 1;
															if (routeNumberExit <= -1
																	|| routeNumberExit >= company
																			.getRouteSeqs()[routeSeqNumber]
																			.getRoutes().length) {
																break;
															}
															if (routeNumberExit < routeNumberEntry) {
																view.showMessage(
																		"La ruta de salida no puede ser anterior a la entrada");
																break;
															}

															view.showMessage("Ha seleccionado:\n"
																	+ buildRouteInfo(
																			company.getRouteSeqs()[routeSeqNumber]
																					.getRoutes()[routeNumberEntry])
																	+ "\ny\n"
																	+ buildRouteInfo(
																			company.getRouteSeqs()[routeSeqNumber]
																					.getRoutes()[routeNumberExit])
																	+ "\ncomo las rutas aplicables\n1. Confirmar. 0. Cancelar");
															option = readOption(true, 1);
															if (option <= 0 || option > 1) {
																break;
															}
															if (option == 1) {
																routes[0] = company.getRouteSeqs()[routeSeqNumber]
																		.getRoutes()[routeNumberEntry];
																routes[1] = company.getRouteSeqs()[routeSeqNumber]
																		.getRoutes()[routeNumberExit];

																view.showMessage(
																		"Se han añadido las rutas exitosamente");
															}
															appliesTo = Coupon.AppliesTo.ROUTES;
															option = -1;
															break;
													}
													if (option == 0)
														break;
													view.showMessage(
															"Tipo de descuento\n1. Cantidad		2. Porcentaje");
													RedeemedCoupon.DiscountType discountType = RedeemedCoupon.DiscountType.QUANTITY;
													option = readOption(true, 2);
													if (option <= 0 || option > 2) {
														break;
													}
													if (option == 2) {
														discountType = RedeemedCoupon.DiscountType.PERCENTAGE;
														view.showMessage("Porcentaje de descuento:");
													} else {
														view.showMessage("Cantidad de dinero a descontar:");
													}
													int discount = readOption(true, Integer.MAX_VALUE);
													if (discount <= 0 || discount > Integer.MAX_VALUE) {
														break;
													}
													view.showMessage("Máximas redenciones generales");
													int maxRedeems = readOption(true, Integer.MAX_VALUE);
													if (maxRedeems <= 0 || maxRedeems > Integer.MAX_VALUE) {
														break;
													}
													view.showMessage("Máximas redenciones por usuario");
													int userMaxRedeems = readOption(true, Integer.MAX_VALUE);
													if (userMaxRedeems <= 0 || userMaxRedeems > Integer.MAX_VALUE) {
														break;
													}

													boolean shouldContinue = false;
													LocalDateTime[] dates = null;
													do {
														view.showMessage(
																"Digite el día de inicio y el día de expiración del cupón \nFormato: yyyy-MM-dd'T'HH:mm:ss");
														String startingDateFormat = view.readData();
														String expiringDateFormat = view.readData();
														try {
															dates = new LocalDateTime[] {
																	LocalDateTime.parse(startingDateFormat,
																			DateTimeFormatter.ofPattern(
																					"yyyy-MM-dd'T'HH:mm:ss")),
																	LocalDateTime.parse(expiringDateFormat,
																			DateTimeFormatter.ofPattern(
																					"yyyy-MM-dd'T'HH:mm:ss")) };
															if (dates[1].isAfter(dates[0])) {
																shouldContinue = true;
															} else {
																view.showMessage(
																		"La fecha de expiración no puede ir antes de la fecha de inicio\n");
															}
														} catch (DateTimeParseException dtpex) {
															view.showMessage(
																	"La fecha se ha escrito de forma incorrecta\n");
														}
													} while (!shouldContinue);

													view.showMessage(
															"Digite los días que estará activo el cupón (1. Lunes, 2. Martes... etc).\n 0. Para terminar\n 8. Para que el bus esté disponible todos los días");
													int[] daysNumber = new int[7];
													cancelate = false;
													for (int i = 0; option != 0 && i < daysNumber.length; i++) {
														option = readOption(true, 8);
														if (option <= 0 || option > 8) {
															if (i == 0) {
																cancelate = true;
															}
															break;
														}
														daysNumber[i] = option;
														if (option == 8) {
															view.showMessage(
																	"El cupón estará disponible todos los días");
															break;
														}
													}
													if (cancelate) {
														option = 1;
														break;
													}
													calculate.createCoupon(couponType, 12345, company, couponName,
															description, discountType,
															redeemWord, discount, dates, isCumulative, appliesTo,
															vehicles, routeSeqs, routes,
															calculate.readLaboralDays(daysNumber), userMaxRedeems,
															maxRedeems);
													view.showMessage("Se ha creado el cupón correctamente\n");
													break;
												case 3:
													view.showMessage(
															"Seleccione el cupón que desea eliminar\n0. Salir");
													showCoupons(company.getCoupons(), 0, false);
													couponNumber = readOption(true, company.getCoupons().length) - 1;
													if (couponNumber <= -1
															|| couponNumber > company.getCoupons().length) {
														break;
													}
													view.showMessage("Está seguro que desea eliminar el cupón "
															+ company.getCoupons()[couponNumber].getName()
															+ "?\n1. Sí	0. No");
													option = readOption(true, 2);
													if (option <= 0 || option > 2) {
														break;
													}
													company.getCoupons()[couponNumber] = null;
													view.showMessage("Se ha eliminado el cupón correctamente");
													break;
											}
											break;
										case 3:
											view.showMessage(
													"Seleccione una de las siguientes opciones\n1. Crear vehículo\n2. Editar vehículo\n3. Eliminar vehículo\n0. Regresar");

											option = readOption(true, 3);
											if (option <= 0 || option > 3) {
												break;
											}
											switch (option) {
												case 1:
													view.showMessage("¿Qué tipo de vehículo desea crear?\n1. "
															+ Vehicle.Type.AIRPLANE.getUpperCaseName() + "\n2. "
															+ Vehicle.Type.BUS.getUpperCaseName() + "\n3. "
															+ Vehicle.Type.SHIP.getUpperCaseName() + "\n4. "
															+ Vehicle.Type.TRAVEL_BUS.getUpperCaseName()
															+ "\n0. Salir");

													vehicleTypeInt = readOption(true, 3) - 1;
													if (vehicleTypeInt <= -1 || vehicleTypeInt > 3) {
														break;
													}
													try {
														showCreateVehicleMenu(
																calculate.convertIntToVehicleType(vehicleTypeInt),
																company);
													} catch (NullPointerException ex) {
														option = -1;
														view.showMessage(
																"Opción inválida, se ha cancelado la operación\n");
														break;
													}
													break;
												case 2:
													view.showMessage("¿Qué tipo de vehículo desea editar?\n1. "
															+ Vehicle.Type.AIRPLANE.getUpperCaseName() + "\n2. "
															+ Vehicle.Type.BUS.getUpperCaseName() + "\n3. "
															+ Vehicle.Type.SHIP.getUpperCaseName() + "\n4. "
															+ Vehicle.Type.TRAVEL_BUS.getUpperCaseName()
															+ "\n0. Salir");

													vehicleTypeInt = readOption(true, 4) - 1;
													if (vehicleTypeInt <= 0 || vehicleTypeInt > 3) {
														break;
													}
													Vehicle.Type vehicleType = calculate
															.convertIntToVehicleType(vehicleTypeInt);
													if (vehicleType == null) {
														view.showMessage(
																"Digite un número entero válido, por favor inténtelo de nuevo");
														option = -1;
														break;
													}
													view.showMessage(
															"\nDigite el número del vehículo para editarlo\n0. Cancelar");
													Vehicle[] vehicles;
													switch (vehicleType) {
														case AIRPLANE:
															vehicles = company
																	.getVehicles()[Vehicle.Type.AIRPLANE.ordinal()];
															break;
														case BUS:
															vehicles = company.getVehicles()[Vehicle.Type.BUS
																	.ordinal()];
															break;
														case SHIP:
															vehicles = company.getVehicles()[Vehicle.Type.SHIP
																	.ordinal()];
															break;
														case TRAVEL_BUS:
															vehicles = company.getVehicles()[Vehicle.Type.TRAVEL_BUS
																	.ordinal()];
															break;
														default:
															vehicles = company.getVehicles()[Vehicle.Type.BUS
																	.ordinal()];
															view.showCurrentLineMessage("Opción inválida");
													}
													for (int i = 0; i < vehicles.length; i++) {
														if (vehicles[i] != null) {
															view.showMessage(
																	(i + 1) + "   " + "\nRuta: " + "\n Ticket: "
																			+ vehicles[i].getPrice());
														}
													}

													int vehicleNumber = readOption(true, vehicles.length - 1) - 1;
													if (vehicleNumber <= 0 || vehicleNumber >= vehicles.length) {
														break;
													}

													if (vehicles[vehicleNumber] == null) {
														view.showMessage("Has seleccionado un vehículo inexistente");
														vehicleNumber = -1;
														break;
													}

													view.showMessage("El vehículo seleccionado es el siguiente:\n"
															+ (vehicleNumber + 1)
															+ "   " + "\nRuta: "
															+ vehicles[vehicleNumber].getRouteSeq().getName()
															+ "\n Ticket: " + vehicles[vehicleNumber].getPrice());
													for (int i = 0; i < vehicles[vehicleNumber].getRouteSeq()
															.getRoutes().length; i++) {
														if (vehicles[vehicleNumber].getRouteSeq()
																.getRoutes()[i] != null) {
															view.showCurrentLineMessage("\nRuta " + (i + 1) + ": "
																	+ vehicles[vehicleNumber].getRouteSeq()
																			.getRoutes()[i].getName()
																	+ "\n Entrada: "
																	+ vehicles[vehicleNumber].getRouteSeq()
																			.getRoutes()[i].getStops()[0]
																	+ "\n Salida: "
																	+ vehicles[vehicleNumber].getRouteSeq()
																			.getRoutes()[i].getStops()[1]);
														}
													}
													view.showMessage("\n1. Editar\n0. Cancelar");

													option = readOption(true, 1);
													if (option <= 0 || option > 1) {
														break;
													}

													view.showMessage(
															"1. Edición completa\n2. Editar Ruta\n3. Editar precio\n4. Eliminar\n0. Cancelar");

													option = readOption(true, 4);
													if (option <= 0 || option > 4) {
														break;
													}

													switch (option) {
														case 1:
															view.showMessage("Edición completa");
														case 2:
															view.showMessage("Con qué ruta desea reemplazar?");
															view.showMessage(
																	"Elija la ruta que desea asignarle al vehículo y ver más detalles\n0. Salir");
															for (int i = 0; i < company
																	.getRouteSeqs().length; i++) {
																if (company.getRouteSeqs()[i] != null) {
																	view.showMessage("Ruta " + (i + 1) + ": "
																			+ company.getRouteSeqs()[i]
																					.getName());
																}
															}
															int routeNumber = readOption(true,
																	company.getRouteSeqs().length - 1) - 1;
															if (routeNumber <= -1 || routeNumber >= company
																	.getRouteSeqs().length) {
																break;
															}
															try {
																for (int i = 0; i < company
																		.getRouteSeqs()[routeNumber]
																		.getRoutes().length; i++) {
																	view.showMessage(
																			company.getRouteSeqs()[routeNumber]
																					.getRoutes()[i].getName()
																					+ "\nInicio: "
																					+ company
																							.getRouteSeqs()[routeNumber]
																							.getRoutes()[i]
																							.getStops()[0]
																					+ "\nFin: "
																					+ company
																							.getRouteSeqs()[routeNumber]
																							.getRoutes()[i]
																							.getStops()[1]
																					+ "\n");
																}
															} catch (NullPointerException ex) {
																view.showMessage(
																		"La ruta seleccionada es inexistente, por favor, inténtelo de nuevo");
																option = -1;
																continue;
															}
															view.showMessage(
																	"Digite 1 para seleccionar esta ruta, 0 para regresar");

															option = readOption(true, 1);
															if (option <= 0 || option > 1) {
																break;
															}

															calculate.editVehicle(vehicles[vehicleNumber],
																	company.getRouteSeqs()[routeNumber],
																	vehicles[vehicleNumber].getPrice());
															view.showMessage(
																	"La nueva ruta se ha asignado correctamente");
															if (option != 1) {
																break;
															}
														case 3:
															view.showMessage(
																	"Digite el nuevo precio que tendrá el ticket. Precio anterior: "
																			+ vehicles[vehicleNumber].getPrice());

															int price = readOption(false, Integer.MAX_VALUE);
															if (price < 0) {
																if (price < 0) {
																	view.showMessage("El precio no puede ser negativo");
																}
																break;
															}

															calculate.editVehicle(vehicles[vehicleNumber],
																	vehicles[vehicleNumber].getRouteSeq(), price);

															view.showMessage(
																	"El nuevo precio se ha asignado correctamente");
															break;
														case 4:
															view.showMessage(
																	"Está a punto de eliminar el vehículo, esta acción no se puede revertir\n1. Confirmar\n0. Cancelar");

															option = readOption(true, 1);
															if (option <= 0 || option > 1) {
																break;
															}
															calculate.deleteVehicle(vehicles[vehicleNumber]);
															view.showMessage(
																	"Se ha eliminado el vehículo de manera exitosa");
													}

													break;
												case 3:
													view.showMessage("Seleccione el vehículo que desea eliminar");
													Vehicle vehicle = showSelectVehicleMenu(false);
													view.showMessage("Está seguro que desea eliminar el vehículo: "
															+ vehicle.getPlate()
															+ "\n1. Sí\n0. Cancelar");
													option = readOption(true, 1);
													if (option == 1) {
														vehicle = null;
													}
													break;
											}

										case 4:
											view.showMessage("Digite el nombre de la ruta");
											String routeName = view.readData();
											view.showMessage(
													"Digite los días que estará activa la ruta (1. Lunes, 2. Martes... etc).\n 0. Para terminar\n 8. Para que el bus esté disponible todos los días");
											int[] daysNumber = new int[7];
											cancelate = false;
											for (int i = 0; option != 0 && i < daysNumber.length; i++) {
												option = readOption(true, 8);
												if (option <= 0 || option > 8) {
													if (i == 0) {
														cancelate = true;
													}
													break;
												}
												daysNumber[i] = option;
												if (option == 8) {
													view.showMessage("La ruta funcionará todos los días");
													break;
												}
											}
											if (cancelate) {
												break;
											}
											view.showMessage("Desde qué hora inicia la ruta (hh:mm:ss)");
											String initialTime = view.readData();
											view.showMessage("¿Cuántas paradas creará?");
											stopsNumber = readOption(true, Integer.MAX_VALUE);
											if (stopsNumber < 2) {
												view.showMessage("Debe crear al menos 2 paradas");
												break;
											}
											view.showMessage("Digite la duración en minutos");
											int[] durationTime = new int[stopsNumber];
											for (int i = 1; i < durationTime.length; i++) {
												view.showMessage("Duración desde la parada " + i + " hasta la parada "
														+ (i + 1));

												durationTime[i - 1] = readOption(false, Integer.MAX_VALUE);
												if (durationTime[i - 1] <= 0) {
													view.showMessage(
															"Digite un número entero e inténtelo de nuevo e inténtalo de nuevo\n");
													i--;
												}
											}
											try {
												calculate.createRouteSeq(company, routeName, initialTime,
														calculate.readLaboralDays(daysNumber), stopsNumber,
														durationTime);
											} catch (DateTimeParseException ex) {
												view.showMessage("Se ha digitado mal la hora, inténtelo nuevamente");
												option = -1;
												break;
											}
											view.showMessage("La ruta se ha creado correctamente\n");
											break;
										case 5:
											for (int i = 0; i < company.getRouteSeqs().length; i++) {
												if (company.getRouteSeqs()[i] != null) {
													view.showMessage("\nSecuencia de Rutas Nro" + (i + 1) + ": "
															+ company.getRouteSeqs()[i].getName());
												}
											}
											view.showMessage(
													"Digite el número de la secuencia de rutas para ver más detalles\0. Salir");

											option = readOption(true, company.getRouteSeqs().length - 1) - 1;
											if (option <= -1 || option >= company.getRouteSeqs().length) {
												break;
											}
											RouteSequence routeSeqSelected = company.getRouteSeqs()[option - 1];
											for (int i = 0; i < routeSeqSelected.getRoutes().length; i++) {
												if (routeSeqSelected.getRoutes()[i] != null) {
													view.showCurrentLineMessage("\nRuta " + option + ". "

															+ routeSeqSelected.getRoutes()[i].getName() + "\n Entrada: "
															+ routeSeqSelected.getRoutes()[i]

																	.getStops()[Route.StopType.ENTRY.ordinal()]
															+ "\n Salida: "
															+ routeSeqSelected.getRoutes()[i]
																	.getStops()[Route.StopType.EXIT.ordinal()]
															+ "\n Disponibilidad: ");
													if (routeSeqSelected.getRoutes()[i].getIsAvailable()) {
														view.showMessage("available");
													} else {
														view.showMessage("unavailable");
													}
												}
											}

											view.showMessage("1. Eliminar permanentemente\n0. Cancelar y salir");

											option = readOption(true, 1) - 1;
											if (option <= -1 || option > 1) {
												break;
											}
											routeSeqSelected = null;
											break;
										default:
											break;
									}
									option = -1;
								} while (option != 0);
							} else {
								view.showMessage(
										"No ha sido posible iniciar sesión, por favor, verifique los datos e intente nuevamente");
							}
							break;
						case 2:
							view.showMessage("Digite el nombre de la empresa");
							name = view.readData();
							if (name.equals("0")) {
								view.showMessage("Se ha cancelado la operación\n");
								break;
							}
							view.showMessage("Digite su contraseña");
							password = view.readData();
							if (calculate.isUserAvailable(name)) {
								calculate.createUser(User.Type.COMPANY, name, password);
								view.showMessage(
										"La empresa se ha creado correctamente\n¿Cuál es la descripción de su empresa? (Esto se puede modificar en el futuro)");
								String description = view.readData();
								calculate.editCompanyDescription(
										(Company) calculate.getDataCenter().getUsers()[User.Type.COMPANY
												.ordinal()][calculate.searchUserArrayNumber(User.Type.COMPANY,
														name)],
										description);
							} else {
								view.showMessage("El nombre de usuario ya está en uso");
								option = -1;
								break;
							}
							break;
						default:
							view.showMessage("Digite un número entero válido");
					}
					break;

				case 5:
					view.showMessage("Menú de administrador\nDigite el usuario:");
					name = view.readData();
					view.showMessage("Digite la contraseña");
					password = view.readData();
					if (calculate.logInAdmin(name, password)) {
						do {
							view.showMessage(
									"1. Ver estadísticas generales y por empresa\n2. Eliminar empresa\n3. Eliminar Usuario\n0. Cerrar Sesión");

							option = readOption(true, 3);
							if (option <= 0 || option > 3) {
								break;
							}
							switch (option) {
								case 1:
									for (int i = 0; i < calculate.getDataCenter().getUsers()[User.Type.COMPANY

											.ordinal()].length; i++) {
										Company company = (Company) calculate.getDataCenter()
												.getUsers()[User.Type.COMPANY
														.ordinal()][i];
										if (company != null) {
											calculate.checkCompanyRevenue(company);
											view.showMessage(company.getName() + "\n Ingresos: "
													+ company.getRevenue()[Value.GENERAL.getValue()] + "\n Anuales:    "
													+ company.getRevenue()[Value.YEARLY.getValue()] + "\n Mensuales: "
													+ company.getRevenue()[Value.MONTHLY.getValue()] + "\n Diarios:   "
													+ company.getRevenue()[Value.DAILY.getValue()]);
										}
									}
									view.showMessage("Digite cualquier tecla para salir");
									view.readData();
									break;
								case 2:
									User company = null;
									for (int i = 0; i < calculate.getDataCenter().getUsers()[User.Type.COMPANY
											.ordinal()].length; i++) {
										company = calculate.getDataCenter().getUsers()[User.Type.COMPANY
												.ordinal()][i];
										if (company != null) {
											view.showMessage((i + 1) + ". " + company.getName());
										}
									}
									view.showMessage(
											"Digite el número de la empresa que desea eliminar\n0. Cancelar y salir");

									option = readOption(true,
											calculate.getDataCenter().getUsers()[User.Type.COMPANY.ordinal()].length
													- 1)
											- 1;
									if (option <= -1 || option >= calculate.getDataCenter().getUsers()[User.Type.COMPANY
											.ordinal()].length) {
										break;
									}
									company = calculate.getDataCenter().getUsers()[User.Type.COMPANY
											.ordinal()][option];
									view.showMessage(
											"Está seguro de que desea eliminar la empresa " + company.getName()
													+ "? Esta acción no se puede revertir\n1. Eliminar\n0. Cancelar");

									option = readOption(true, 1);
									if (option <= 0 || option > 1) {
										break;
									}
									company = null;
									view.showMessage("La empresa se ha eliminado exitosamente");
									break;
								case 3:
									User currentUser = null;
									for (int i = 1; i < calculate.getDataCenter().getUsers()[User.Type.USER
											.ordinal()].length; i++) {
										currentUser = calculate.getDataCenter().getUsers()[User.Type.USER.ordinal()][i];
										if (currentUser != null) {
											view.showMessage(i + ". " + currentUser.getName());
										}
									}
									view.showMessage(
											"Digite el número del usuario que desea eliminar\n0. Cancelar y salir");

									option = readOption(true,
											calculate.getDataCenter().getUsers()[User.Type.USER.ordinal()].length - 1);
									if (option <= 0 || option >= calculate.getDataCenter().getUsers()[User.Type.USER
											.ordinal()].length) {
										break;
									}
									currentUser = calculate.getDataCenter().getUsers()[User.Type.USER
											.ordinal()][option];
									if (currentUser == null) {
										view.showMessage(
												"El usuario seleccionado es inexistente, se ha cancelado la operación");
										option = -1;
										break;
									}
									view.showMessage(
											"Está seguro de que desea eliminar el usuario " + currentUser.getName()
													+ "? Esta acción no se puede revertir\n1. Eliminar\0. Cancelar");

									option = readOption(true, 1);
									if (option <= 0 || option > 1) {
										break;
									}
									currentUser = null;
									view.showMessage("El usuario se ha eliminado exitosamente");
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
			option = -1;
		} while (option != 0);
	}

	public static void main(String[] args) {
		Presenter presenter = new Presenter();

		presenter.defaultValues();
		presenter.run();
	}
}