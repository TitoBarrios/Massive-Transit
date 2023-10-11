package model;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;

import java.time.DayOfWeek;

public class Calculator {
	private DataCenter dataCenter;

	public Calculator() {
		dataCenter = new DataCenter();
	}

	public void addWallet(int userNumber, int money) {
		dataCenter.getUsers()[userNumber].setWallet(money + dataCenter.getUsers()[userNumber].getWallet());
	}

	public void deleteSubscription(int userArrayNumber, int subscriptionArrayNumber) {
		dataCenter.getUsers()[userArrayNumber].deleteSubscription(subscriptionArrayNumber);
	}

	public boolean isDayForWork(DayOfWeek[] laboralDays, LocalDateTime timeToCheck) {
		boolean dayForWork = false;
		for (int i = 0; i < laboralDays.length; i++) {
			if (timeToCheck.getDayOfWeek().equals(laboralDays[i])) {
				dayForWork = true;
				break;
			}
		}
		return dayForWork;
	}

	public boolean isEnoughMoney(User user, Vehicle vehicle) {
		if (user.getWallet() >= vehicle.getPrice()) {
			return true;
		}
		return false;
	}

	public boolean isUserAvailable(String name) {
		boolean availability = true;
		for (int i = 0; i < dataCenter.getUsers().length; i++) {
			if (dataCenter.getUsers()[i] != null) {
				if (dataCenter.getUsers()[i].getName().equals(name)) {
					availability = false;
					break;
				}
			}
		}
		return availability;
	}

	public void editRoute(int routeArrayNumber, String[] routesName) {
		if (dataCenter.getRoutes()[routeArrayNumber] != null) {
			if (routesName.length == dataCenter.getRoutes().length) {
				for (int i = 0; i < dataCenter.getRoutes().length; i++) {
					dataCenter.getRoutes()[routeArrayNumber][i].setName(routesName[i]);
				}
			}
		}
	}

	public Vehicle catchVehicle(VehicleType vehicleType, int vehicleNumber) {
		try {
			switch (vehicleType) {
			case AIRPLANE:
				return dataCenter.getAirplanes()[vehicleNumber];
			case BUS:
				return dataCenter.getBuses()[vehicleNumber];
			case SHIP:
				return dataCenter.getShips()[vehicleNumber];
			case TRAVEL_BUS:
				return dataCenter.getTravelBuses()[vehicleNumber];
			default:
				return null;
			}
		} catch (ArrayIndexOutOfBoundsException | NullPointerException ex) {
			return null;
		}
	}

	public Vehicle[] catchVehicles(VehicleType vehicleType) {
		try {
			switch (vehicleType) {
			case AIRPLANE:
				return dataCenter.getAirplanes();
			case BUS:
				return dataCenter.getBuses();
			case SHIP:
				return dataCenter.getShips();
			case TRAVEL_BUS:
				return dataCenter.getTravelBuses();
			default:
				return null;
			}
		} catch (NullPointerException ex) {
			return null;
		}
	}

	public void checkSubscriptionsPayment(User user) {
		for (int i = 0; i < user.getSubscriptions().length; i++) {
			if (user.getSubscriptions()[i] != null) {
				if (user.getSubscriptions()[i].getDayOfWeek().equals(dataCenter.getCurrentDate().getDayOfWeek())
						&& user.getSubscriptions()[i].getRoutes()[Value.ENTRY.getValue()]
								// Tomo solo 5 minutos pero se puede escribir para que sea 15 minutos antes, etc
								.getStops()[0].plusMinutes(5).isAfter(dataCenter.getCurrentDate())
						&& isEnoughMoney(user, user.getSubscriptions()[i].getVehicle())) {
					createTicket(user, user.getSubscriptions()[i].getVehicle(), user.getSubscriptions()[i].getRoutes());
				}
			}
		}
	}

	public void checkVehiclesAvailability(VehicleType vehicleType) {
		boolean isCapacityAvailable = false;
		boolean isRouteAvailable = false;

		Vehicle[] vehicles = catchVehicles(vehicleType);

		for (int i = 0; i < vehicles.length; i++) {
			if (vehicles[i] != null) {
				for (int j = 0; j < vehicles[i].getRoutes().length; j++) {
					if (vehicles[i].getRoutes()[j] != null) {
						if (dataCenter.getCurrentDate().isAfter(vehicles[i].getRoutes()[j].getStops()[0])
								|| dataCenter.getCurrentDate().isEqual(vehicles[i].getRoutes()[j].getStops()[0])) {
							vehicles[i].getRoutes()[j].setAvailability(false);
						} else {
							vehicles[i].getRoutes()[j].setAvailability(true);
							isRouteAvailable = true;
						}
					}
				}

				for (int j = 0; j < vehicles[i].getTickets().length; j++) {
					if (vehicles[i].getTickets()[j] != null) {
						if (dataCenter.getCurrentDate()
								.isAfter(vehicles[i].getTickets()[j].getRoutes()[Value.ENTRY.getValue()]
										.getStops()[Value.ENTRY.getValue()])
								|| dataCenter.getCurrentDate()
										.isAfter(vehicles[i].getTickets()[j].getRoutes()[Value.EXIT.getValue()]
												.getStops()[Value.EXIT.getValue()])) {
							setUsersTicketAvailability(vehicles[i].getTickets()[j], false);
							vehicles[i].deleteTicket(j);
							vehicles[i].setCurrentCapacity(vehicles[i].getCapacity()[Value.CURRENT.getValue()] - 1);
						} else if (!vehicles[i].getTickets()[j].getAvailability()
								&& (vehicles[i].getTickets()[j].getRoutes()[Value.ENTRY.getValue()]
										.getStops()[Value.ENTRY.getValue()].isAfter(dataCenter.getCurrentDate())
										|| vehicles[i].getTickets()[j].getRoutes()[Value.ENTRY.getValue()]
												.getStops()[Value.ENTRY.getValue()]
												.isAfter(dataCenter.getCurrentDate()))) {
							vehicles[i].getTickets()[j].setAvailability(true);
							setUsersTicketAvailability(vehicles[i].getTickets()[j], true);
							vehicles[i].setCurrentCapacity(vehicles[i].getCapacity()[Value.CURRENT.getValue()] + 1);
						}
					}
				}

				if (vehicles[i].getCapacity()[Value.MAXIMUM.getValue()] <= vehicles[i].getCapacity()[Value.CURRENT
						.getValue()]) {
					isCapacityAvailable = false;
				} else {
					isCapacityAvailable = true;
				}

				if (isCapacityAvailable && isRouteAvailable) {
					vehicles[i].setAvailability(true);
				} else {
					vehicles[i].setAvailability(false);
				}
			}
		}
	}

	public VehicleType convertIntToVehicleType(int vehicleTypeInt) {
		switch (vehicleTypeInt) {
		case 0:
			return VehicleType.AIRPLANE;
		case 1:
			return VehicleType.BUS;
		case 2:
			return VehicleType.SHIP;
		case 3:
			return VehicleType.TRAVEL_BUS;
		default:
			return null;
		}
	}

	public void createRoutes(String initialTime, DayOfWeek[] laboralDays, int stopsNumber, int[] timeLapse) {
		Route[] routes = new Route[stopsNumber - 1];
		LocalTime[] stopTime = new LocalTime[stopsNumber];
		LocalDateTime[][] stops = new LocalDateTime[stopsNumber][2];
		stopTime[Value.ENTRY.getValue()] = LocalTime.parse(initialTime);
		stops[0][Value.ENTRY.getValue()] = setLaboralDays(laboralDays, stopTime[Value.ENTRY.getValue()]);
		stopTime[Value.EXIT.getValue()] = stopTime[0].plusMinutes(timeLapse[0]);
		stops[0][Value.EXIT.getValue()] = setLaboralDays(laboralDays, stopTime[Value.EXIT.getValue()]);
		routes[0] = new Route("0 a 1",
				new LocalDateTime[] { stops[0][Value.ENTRY.getValue()], stops[0][Value.EXIT.getValue()] },
				new String[] { "0", "1" });
		for (int i = 1; i < routes.length; i++) {
			stopTime[i + 1] = stopTime[i].plusMinutes(timeLapse[i]);
			stops[i][Value.ENTRY.getValue()] = stops[i - 1][Value.EXIT.getValue()];
			stops[i][Value.EXIT.getValue()] = setLaboralDays(laboralDays, stopTime[i + 1]);
			routes[i] = new Route(i + " a " + (i + 1),
					new LocalDateTime[] { stops[i][Value.ENTRY.getValue()], stops[i][Value.EXIT.getValue()] },
					new String[] { i + "", (i + 1) + "" });
		}

		for (int i = 0; i < this.dataCenter.getRoutes().length; i++) {
			if (this.dataCenter.getRoutes()[i] == null) {
				this.dataCenter.getRoutes()[i] = new Route[routes.length];
				for (int j = 0; j < this.dataCenter.getRoutes()[i].length; j++) {
					if (this.dataCenter.getRoutes()[i][j] == null) {
						this.dataCenter.getRoutes()[i][j] = routes[j];
					}
				}
				break;
			}
		}
	}

	public void createSubscription(User user, DayOfWeek dayOfWeek, Vehicle vehicle, Route[] routes) {
		Subscription subscription = new Subscription(dayOfWeek, vehicle, routes);
		user.setNewSubscription(subscription);
	}

	public void createTicket(User user, Vehicle vehicle, Route[] routes) {
		if (isEnoughMoney(user, vehicle)) {
			user.setWallet(user.getWallet() - vehicle.getPrice());
			Ticket ticket = new Ticket(user, vehicle, routes);
			// String name = dataCenter.getCurrentDate().getYear() + "_" + userNumber + "_"
			// + vehicleType.getValue() + vehicleNumber + routePositionEntry +
			// routePositionExit;
			ticket.setName("DEFAULT NAME");
			vehicle.setTicket(ticket);
			user.setTicket(ticket);
		}
	}

	public void createUser(String name, String password) {
		if (isUserAvailable(name)) {
			for (int i = 0; i < dataCenter.getUsers().length; i++) {
				if (dataCenter.getUsers()[i] == null) {
					dataCenter.getUsers()[i] = new User(name, password);
					break;
				}
			}
		}
	}

	public void createVehicle(VehicleType vehicleType, String company, String plate, Route[] routes, int price,
			int capacity) {
		Vehicle[] vehicles = catchVehicles(vehicleType);
		for (int i = 0; i < vehicles.length; i++) {
			if (vehicles[i] == null) {
				switch (vehicleType) {
				case AIRPLANE:
					vehicles[i] = new Airplane(company, plate, routes, price, capacity);
					break;
				case BUS:
					vehicles[i] = new Bus(company, plate, routes, price, capacity);
					break;
				case SHIP:
					vehicles[i] = new Ship(company, plate, routes, price, capacity);
					break;
				case TRAVEL_BUS:
					vehicles[i] = new TravelBus(company, plate, routes, price, capacity);
					break;
				}
				break;
			}
		}
	}

	public boolean logIn(String name, String password) {
		boolean logIn = false;
		for (int i = 0; i < dataCenter.getUsers().length; i++) {
			if (dataCenter.getUsers()[i] != null) {
				if (dataCenter.getUsers()[i].getName().equals(name)
						&& dataCenter.getUsers()[i].getPassword().equals(password)) {
					logIn = true;
					break;
				}
			}
		}
		return logIn;
	}

	public boolean logInAdmin(String name, String password) {
		boolean adminLogIn = false;
		if (dataCenter.getUsers()[0].getName().equals(name)
				&& dataCenter.getUsers()[0].getPassword().equals(password)) {
			adminLogIn = true;
		}
		return adminLogIn;
	}

	public DayOfWeek[] readLaboralDays(int[] laboralDaysNumber) {
		DayOfWeek[] daysOfWeek = new DayOfWeek[7];
		for (int i = 0; i < laboralDaysNumber.length; i++) {
			switch (laboralDaysNumber[i]) {
			case 1:
				daysOfWeek[i] = DayOfWeek.MONDAY;
				break;
			case 2:
				daysOfWeek[i] = DayOfWeek.TUESDAY;
				break;
			case 3:
				daysOfWeek[i] = DayOfWeek.WEDNESDAY;
				break;
			case 4:
				daysOfWeek[i] = DayOfWeek.THURSDAY;
				break;
			case 5:
				daysOfWeek[i] = DayOfWeek.FRIDAY;
				break;
			case 6:
				daysOfWeek[i] = DayOfWeek.SATURDAY;
				break;
			case 7:
				daysOfWeek[i] = DayOfWeek.SUNDAY;
				break;
			case 8:
				daysOfWeek[0] = DayOfWeek.MONDAY;
				daysOfWeek[1] = DayOfWeek.TUESDAY;
				daysOfWeek[2] = DayOfWeek.WEDNESDAY;
				daysOfWeek[3] = DayOfWeek.THURSDAY;
				daysOfWeek[4] = DayOfWeek.FRIDAY;
				daysOfWeek[5] = DayOfWeek.SATURDAY;
				daysOfWeek[6] = DayOfWeek.SUNDAY;
				break;
			}
			if (laboralDaysNumber[i] == 8) {
				break;
			}
		}
		return daysOfWeek;
	}

	public int searchUserArrayNumber(String name, String password) {
		int arrayNumber = 0;
		for (int i = 0; i < dataCenter.getUsers().length; i++) {
			if (dataCenter.getUsers()[i] != null) {
				if (dataCenter.getUsers()[i].getName().equals(name)
						&& dataCenter.getUsers()[i].getPassword().equals(password)) {
					arrayNumber = i;
					break;
				}
			}
		}
		return arrayNumber;
	}

	public LocalDateTime setLaboralDays(DayOfWeek[] laboralDays, LocalTime time) {
		LocalDate dateForWork = dataCenter.getCurrentDate().toLocalDate();
		LocalDateTime dateForWorkTime;
		boolean isDayForWork = isDayForWork(laboralDays, dataCenter.getCurrentDate());
		while (!isDayForWork) {
			dateForWork = dateForWork.plusDays(1);
			dateForWorkTime = LocalDateTime.of(dateForWork, dataCenter.getCurrentDate().toLocalTime());
			isDayForWork = isDayForWork(laboralDays, dateForWorkTime);
		}
		LocalDateTime timeForWork = LocalDateTime.of(dateForWork, time);
		return timeForWork;
	}

	public void setUsersTicketAvailability(Ticket ticket, boolean availability) {
		for (int i = 0; i < dataCenter.getUsers().length; i++) {
			if (dataCenter.getUsers()[i] != null) {
				for (int j = 0; j < dataCenter.getUsers()[i].getTicketHistory().length; j++) {
					if (dataCenter.getUsers()[i].getTicketHistory()[j] != null) {
						if (dataCenter.getUsers()[i].getTicketHistory()[j].equals(ticket)) {
							dataCenter.getUsers()[i].getTicketHistory()[j].setAvailability(availability);
						}
					}
				}
			}
		}
	}

	public DataCenter getDataCenter() {
		return dataCenter;
	}

}