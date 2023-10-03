package model;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.DayOfWeek;

public class Calculator {
	DataCenter dataCenter;

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

	public boolean isEnoughMoney(int userNumber, VehicleType vehicleType, int vehicleNumber) {
		boolean enoughMoney = false;
		if (dataCenter.getUsers()[userNumber].getWallet() >= catchVehicle(vehicleType, vehicleNumber).getPrice()) {
			enoughMoney = true;
		}
		return enoughMoney;
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

	public void checkSubscriptionPayment(int userArrayNumber) {
		for (int i = 0; i < dataCenter.getUsers()[userArrayNumber].getSubscription().length; i++) {
			if (dataCenter.getUsers()[userArrayNumber].getSubscription()[i] != null) {
				Vehicle vehicle = catchVehicle(
						dataCenter.getUsers()[userArrayNumber].getSubscription()[i].getVehicleType(),
						dataCenter.getUsers()[userArrayNumber].getSubscription()[i].getVehicleArrayNumber());
				if (dataCenter.getUsers()[userArrayNumber].getSubscription()[i].getDayOfWeek()
						.equals(dataCenter.getCurrentDate().getDayOfWeek())
						&& vehicle.getRoutes()[dataCenter.getUsers()[userArrayNumber].getSubscription()[i]
								.getRouteEntryArrayNumber()].getStops()[0].plusMinutes(5) // Tomo solo 5 minutos pero se
																							// puede escribir para que
																							// sea 15 minutos antes, etc
								.isAfter(dataCenter.getCurrentDate())
						&& isEnoughMoney(userArrayNumber, vehicle.getVehicleType(),
								dataCenter.getUsers()[userArrayNumber].getSubscription()[i].getVehicleArrayNumber())) {
					createTicket(userArrayNumber, vehicle.getVehicleType(),
							dataCenter.getUsers()[userArrayNumber].getSubscription()[i].getVehicleArrayNumber(),
							dataCenter.getUsers()[userArrayNumber].getSubscription()[i].getRouteEntryArrayNumber(),
							dataCenter.getUsers()[userArrayNumber].getSubscription()[i].getRouteExitArrayNumber());
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
						if (dataCenter.getCurrentDate().isAfter(vehicles[i].getTickets()[j].getDates()[1])
								|| dataCenter.getCurrentDate().isAfter(vehicles[i].getTickets()[j].getDates()[1])) {
							setUsersTicketAvailability(vehicles[i].getTickets()[j], false);
							vehicles[i].deleteTicket(j);
							vehicles[i].setCurrentCapacity(vehicles[i].getCurrentCapacity() - 1);
						} else if (!vehicles[i].getTickets()[j].getAvailability()
								&& (vehicles[i].getTickets()[j].getDates()[0].isAfter(dataCenter.getCurrentDate())
										|| vehicles[i].getTickets()[j].getDates()[0]
												.isAfter(dataCenter.getCurrentDate()))) {
							vehicles[i].getTickets()[j].setAvailability(true);
							setUsersTicketAvailability(vehicles[i].getTickets()[j], true);
							vehicles[i].setCurrentCapacity(vehicles[i].getCurrentCapacity() + 1);
						}
					}
				}

				if (vehicles[i].getCapacity() <= vehicles[i].getCurrentCapacity()) {
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

	public void createRoutes(int stopsNumber, String initialTime, int[] timeLapse, int[] daysActive) {
		Route[] routes = new Route[stopsNumber - 1];
		LocalTime[] stopTime = new LocalTime[stopsNumber];
		LocalDateTime[][] stops = new LocalDateTime[stopsNumber][2];
		DayOfWeek[] laboralDays = readLaboralDays(daysActive);
		stopTime[0] = LocalTime.parse(initialTime);
		stops[0][0] = setLaboralDays(laboralDays, stopTime[0]);
		stopTime[1] = stopTime[0].plusMinutes(timeLapse[0]);
		stops[0][1] = setLaboralDays(laboralDays, stopTime[1]);
		routes[0] = new Route("0 a 1", new LocalDateTime[] { stops[0][0], stops[0][1] });
		for (int i = 1; i < routes.length; i++) {
			stopTime[i + 1] = stopTime[i].plusMinutes(timeLapse[i]);
			stops[i][0] = stops[i - 1][1];
			stops[i][1] = setLaboralDays(laboralDays, stopTime[i + 1]);
			routes[i] = new Route(i + " a " + (i + 1), new LocalDateTime[] { stops[i][0], stops[i][1] });
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

	public void createSubscription(int userArrayNumber, int dayOfWeekNumber, VehicleType vehicleType,
			int vehicleArrayNumber, int routeEntryArrayNumber, int routeExitArrayNumber) {
		int[] dayOfWeekArray = new int[1]; // Hecho para que createLaboralDays(...) Detecte el primer parámetro como
											// array
		dayOfWeekArray[0] = dayOfWeekNumber;
		Subscription subscription = new Subscription(readLaboralDays(dayOfWeekArray)[0], vehicleType,
				vehicleArrayNumber, routeEntryArrayNumber, routeExitArrayNumber);
		dataCenter.getUsers()[userArrayNumber].setNewSubscription(subscription);
	}

	public void createTicket(int userNumber, VehicleType vehicleType, int vehicleNumber, int routePositionEntry,
			int routePositionExit) {
		Vehicle vehicle = catchVehicle(vehicleType, vehicleNumber);
		if (isEnoughMoney(userNumber, vehicleType, vehicleNumber)) {
			dataCenter.getUsers()[userNumber]
					.setWallet(dataCenter.getUsers()[userNumber].getWallet() - vehicle.getPrice());
			Ticket ticket = new Ticket(dataCenter.getUsers()[userNumber], vehicle,
					vehicle.getRoutes()[routePositionEntry].getStops()[0],
					vehicle.getRoutes()[routePositionExit].getStops()[1], routePositionEntry, routePositionExit);
			String name = dataCenter.getCurrentDate().getYear() + "_" + userNumber + "_" + vehicleType.getValue()
					+ vehicleNumber + routePositionEntry + routePositionExit;
			ticket.setName(name);
			vehicle.setTicket(ticket);
			dataCenter.getUsers()[userNumber].setTicket(ticket);
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

	public void createVehicle(String plate, VehicleType vehicleType, String company, int price, int capacity,
			int routeNumber) {
		Vehicle[] vehicles = catchVehicles(vehicleType);
		for (int i = 0; i < vehicles.length; i++) {
			if (vehicles[i] == null) {
				switch (vehicleType) {
				case AIRPLANE:
					vehicles[i] = new Airplane(plate, company, price, capacity, dataCenter.getRoutes()[routeNumber]);
					break;
				case BUS:
					vehicles[i] = new Bus(plate, company, price, capacity, dataCenter.getRoutes()[routeNumber]);
					break;
				case SHIP:
					vehicles[i] = new Ship(plate, company, price, capacity, dataCenter.getRoutes()[routeNumber]);
					break;
				case TRAVEL_BUS:
					vehicles[i] = new TravelBus(plate, company, price, capacity, dataCenter.getRoutes()[routeNumber]);
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

	public int getLastUserTicketNumber(int userNumber) {
		int lastUserTicketArrayNumber = 0;
		for (int i = 0; i < dataCenter.getUsers()[userNumber].getTicketHistory().length; i++) {
			if (dataCenter.getUsers()[userNumber].getTicketHistory()[i] == null) {
				lastUserTicketArrayNumber = i - 1;
				break;
			}
		}
		return lastUserTicketArrayNumber;
	}

	public DataCenter getDataCenter() {
		return dataCenter;
	}

}