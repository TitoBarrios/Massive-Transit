package model;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.DayOfWeek;

public class Calculator {
	private LocalDateTime currentDate;
	Bus[] buses;
	Route[][] routes;
	User[] users;
	public static final int MAX_BUSES = 30;
	public static final int MAX_ROUTES = 30;
	public static final int MAX_USERS = 30;

	public Calculator() {
		currentDate = LocalDateTime.now();
		buses = new Bus[MAX_BUSES];
		routes = new Route[MAX_ROUTES][];
		users = new User[MAX_USERS];
	}

	public DayOfWeek[] LaboralDaysCreator(int[] laboralDaysNumber) {
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

	public boolean isDayForWork(DayOfWeek[] laboralDays) {
		boolean dayForWork = false;
		for (int i = 0; i < laboralDays.length; i++) {
			if (currentDate.getDayOfWeek() == laboralDays[i]) {
				dayForWork = true;
				break;
			}
		}
		return dayForWork;
	}

	public LocalDateTime LaboralDaySetter(DayOfWeek[] laboralDays, LocalTime time) {
		LocalDate dateForWork = currentDate.toLocalDate();
		while (!isDayForWork(laboralDays)) {
			dateForWork.plusDays(1);
		}
		LocalDateTime timeForWork = LocalDateTime.of(dateForWork, time);
		return timeForWork;
	}

	public void RoutesCreator(int stopsNumber, String initialTime, int[] timeLapse, int[] daysActive) {
		Route[] routes = new Route[stopsNumber];
		LocalTime[] stopTime = new LocalTime[stopsNumber];
		LocalDateTime[][] stops = new LocalDateTime[stopsNumber][2];
		DayOfWeek[] laboralDays = LaboralDaysCreator(daysActive);

		stopTime[0] = LocalTime.parse(initialTime);
		stops[0][0] = LaboralDaySetter(laboralDays, stopTime[0]);
		stopTime[1] = stopTime[0].plusMinutes(timeLapse[0]);
		stops[0][1] = LaboralDaySetter(laboralDays, stopTime[1]);
		routes[0] = new Route("0 a 1", new LocalDateTime[] { stops[0][0], stops[0][1] });

		for (int i = 1; i < stops.length; i++) {
			stopTime[i + 1] = stopTime[i].plusMinutes(timeLapse[i]);
			stops[i][0] = stops[i - 1][1];
			stops[i][1] = LaboralDaySetter(laboralDays, stopTime[i + 1]);
			routes[i] = new Route(i + "a" + i + 1, new LocalDateTime[] { stops[i][0], stops[i][1] });
		}

		for (int i = 0; i < this.routes.length; i++) {
			if (this.routes[i] == null) {
				this.routes[i] = routes;
				break;
			}
		}
	}

	public void busCreator(String plate, int price, int routeNumber) {
		for (int i = 0; i < buses.length; i++) {
			if (buses[i] == null) {
				buses[i] = new Bus(plate, price, routes[routeNumber]);
			}
		}
	}

	public Bus[] busesCreator(Route[][] routesArray, int[] price, int[] routeNumber, int busQuantity) {
		Bus[] buses = new Bus[busQuantity];
		for (int i = 0; i < buses.length; i++) {
			if (routesArray[routeNumber[i]] != null) {
				buses[i] = new Bus("Bus " + i, price[i], routesArray[routeNumber[i]]);
			}
		}
		return buses;
	}

	public void ticketCreator(User owner, int chairNumber, int busPosition, int routePosition) {
		Ticket ticket = new Ticket(owner, chairNumber, buses[busPosition].getRoutes()[routePosition].getStop(1));
		for (int i = 0; i < buses[busPosition].getRoutes()[routePosition].getTickets().length; i++) {
			if (buses[busPosition].getRoutes()[routePosition].getTickets()[i] == null) {
				buses[busPosition].getRoutes()[routePosition].setTicket(ticket, i);
			}
		}
	}

	public void ticketDisponibilityChecker(Bus bus) {
		for (int i = 0; i < bus.getRoutes().length; i++) {
			if (bus.getRoutes()[i] != null) {
				for (int a = 0; a < bus.getRoutes()[i].getTickets().length; a++) {
					if (bus.getRoutes()[i].getTickets()[a] != null) {
						if (currentDate.isAfter(bus.getRoutes()[i].getTickets()[a].getExpirationDate())
								|| currentDate.isEqual(bus.getRoutes()[i].getTickets()[0].getExpirationDate())) {
							bus.getRoutes()[i].setTicket(null, a);
						}
					}
				}
			}
		}
	}

	public void routeAndBusDisponibilityChecker(int busNumber) {
		buses[busNumber].setDisponibility(false);
		for (int o = 0; o < buses[busNumber].getTickets().length; o++) {
			if (buses[busNumber].getTickets()[o] == null) {
				buses[busNumber].setDisponibility(true);
				for (int i = 0; i < buses[busNumber].getRoutes().length; i++) {
					if (buses[busNumber].getRoutes()[i] != null) {
						buses[busNumber].getRoutes()[i].setDisponibility(false);
						for (int a = 0; i < buses[busNumber].getRoutes()[i].getTickets().length; a++) {
							if (buses[busNumber].getRoutes()[i].getTickets()[a] == null) {
								if ((currentDate.isBefore(buses[busNumber].getRoutes()[i].getStop(0))
										|| currentDate.isEqual(buses[busNumber].getRoutes()[i].getStop(0)))) {
									buses[busNumber].getRoutes()[i].setDisponibility(true);
									a = buses[busNumber].getRoutes()[i].getTickets().length;
								}
							}
						}
					}
				}
			}
		}
	}

	public void UserCreator(String name, String password) {
		boolean canRegister = true;
		for (int i = 0; i < users.length; i++) {
			if (users[i] != null) {
				if (users[i].getName() == name) {
					canRegister = false;
					break;
				}
			}
		}
		if (canRegister) {
			for (int i = 0; i < users.length; i++) {
				if (users[i] == null) {
					users[i] = new User(name, password);
					break;
				}
			}
		}
	}

	public boolean LogIn(String name, String password) {
		boolean logIn = false;
		for (int i = 0; i < users.length; i++) {
			if (users[i] != null) {
				if (users[i].getName() == name && users[i].getPassword() == password) {
					logIn = true;
					break;
				}
			}
		}
		return logIn;
	}
	
	public int searchUserArrayNumber(String name, String password) {
		int arrayNumber = 0;
		for (int i = 0; i < users.length; i++) {
			if (users[i] != null) {
				if (users[i].getName() == name && users[i].getPassword() == password) {
					arrayNumber = i;
					break;
				}
			}
		}
		return arrayNumber;
	}

	public boolean AdminLogIn(String name, String password) {
		boolean adminLogIn = false;
		if (users[0].getName() == name && users[0].getPassword() == password) {
			adminLogIn = true;
		}
		return adminLogIn;
	}

	public Bus[] getBuses() {
		return buses;
	}

	public void setBuses(Bus[] buses) {
		this.buses = buses;
	}

	public Route[][] getRoutes() {
		return routes;
	}
}