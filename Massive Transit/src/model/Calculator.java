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

	public LocalDateTime LaboralDaySetter(DayOfWeek[] laboralDays, LocalTime time) {
		LocalDate dateForWork = currentDate.toLocalDate();
		LocalDateTime dateForWorkTime;
		boolean isDayForWork = isDayForWork(laboralDays, currentDate);
		while (!isDayForWork) {
			dateForWork = dateForWork.plusDays(1);
			dateForWorkTime = LocalDateTime.of(dateForWork, currentDate.toLocalTime());
			isDayForWork = isDayForWork(laboralDays, dateForWorkTime);
		}
		LocalDateTime timeForWork = LocalDateTime.of(dateForWork, time);
		return timeForWork;
	}

	public void RoutesCreator(int stopsNumber, String initialTime, int[] timeLapse, int[] daysActive) {
		Route[] routes = new Route[stopsNumber - 1];
		LocalTime[] stopTime = new LocalTime[stopsNumber];
		LocalDateTime[][] stops = new LocalDateTime[stopsNumber][2];
		DayOfWeek[] laboralDays = LaboralDaysCreator(daysActive);
		stopTime[0] = LocalTime.parse(initialTime);
		stops[0][0] = LaboralDaySetter(laboralDays, stopTime[0]);
		stopTime[1] = stopTime[0].plusMinutes(timeLapse[0]);
		stops[0][1] = LaboralDaySetter(laboralDays, stopTime[1]);
		routes[0] = new Route("0 a 1", new LocalDateTime[] { stops[0][0], stops[0][1] });
		for (int i = 1; i < routes.length; i++) {
			stopTime[i + 1] = stopTime[i].plusMinutes(timeLapse[i]);
			stops[i][0] = stops[i - 1][1];
			stops[i][1] = LaboralDaySetter(laboralDays, stopTime[i + 1]);
			routes[i] = new Route(i + " a " + (i + 1), new LocalDateTime[] { stops[i][0], stops[i][1] });
		}

		for (int i = 0; i < this.routes.length; i++) {
			if (this.routes[i] == null) {
				this.routes[i] = new Route[routes.length];
				for (int j = 0; j < this.routes[i].length; j++) {
					if (this.routes[i][j] == null) {
						this.routes[i][j] = routes[j];
					}
				}
				break;
			}
		}
	}

	public void busCreator(String plate, int price, int routeNumber) {
		for (int i = 0; i < buses.length; i++) {
			if (buses[i] == null) {
				buses[i] = new Bus(plate, price, routes[routeNumber]);
				break;
			}
		}
	}

//Crear una clase heredada, para que funcione con aviones, con ello se pregunta el número de asiento
	public void ticketCreator(int userNumber, int busPosition, int routePositionEntry, int routePositionExit) {
		if (enoughMoney(userNumber, busPosition)) {
			users[userNumber].setWallet(users[userNumber].getWallet() - buses[busPosition].getPrice());
			Ticket ticket = new Ticket(users[userNumber], buses[busPosition].getRoutes()[routePositionExit].getStop(1));
			for (int i = 0; i < buses[busPosition].getTickets().length; i++) {
				if (buses[busPosition].getTickets()[i] == null) {
					buses[busPosition].setTicket(ticket);
				}
			}
		}
	}

	public boolean enoughMoney(int userNumber, int busNumber) {
		boolean enoughMoney = false;
		if (users[userNumber].getWallet() >= buses[busNumber].getPrice()) {
			enoughMoney = true;
		}
		return enoughMoney;
	}

	public void busDisponibilityChecker() {
		boolean isTicketAvailable = false;
		boolean isRouteAvailable = false;
		for (int i = 0; i < buses.length; i++) {
			if (buses[i] != null) {

				for (int j = 0; j < buses[i].getRoutes().length; j++) {
					if (buses[i].getRoutes()[j] != null) {
						if (currentDate.isAfter(buses[i].getRoutes()[j].getStops()[1])
								|| currentDate.isEqual(buses[i].getRoutes()[j].getStops()[1])) {
							buses[i].getRoutes()[j].setDisponibility(false);
						} else {
							buses[i].getRoutes()[j].setDisponibility(true);
							isRouteAvailable = true;
						}
					}
				}

				for (int j = 0; j < buses[i].getRoutes().length; j++) {
					if (buses[i].getRoutes()[j] != null) {
						if (buses[i].getRoutes()[j].getDisponibility() == true) {
							isRouteAvailable = true;
						}
					}
				}

				for (int j = 0; j < buses[i].getTickets().length; j++) {
					if (buses[i].getTickets()[j] != null) {
						if (currentDate.isAfter(buses[i].getTickets()[j].getExpirationDate())
								|| currentDate.isEqual(buses[i].getTickets()[j].getExpirationDate())) {
							buses[i].eliminateTicket(j);
							isTicketAvailable = true;
						}
					} else {
						isTicketAvailable = true;
					}
				}

				if (isTicketAvailable && isRouteAvailable) {
					buses[i].setDisponibility(true);
				} else {
					buses[i].setDisponibility(false);
				}
			}
		}

	}

	public boolean UserAvailability(String name) {
		boolean availability = true;
		for (int i = 0; i < users.length; i++) {
			if (users[i] != null) {
				if (users[i].getName().equals(name)) {
					availability = false;
					break;
				}
			}
		}
		return availability;
	}

	public void UserCreator(String name, String password) {
		if (UserAvailability(name)) {
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
				if (users[i].getName().equals(name) && users[i].getPassword().equals(password)) {
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
				if (users[i].getName().equals(name) && users[i].getPassword().equals(password)) {
					arrayNumber = i;
					break;
				}
			}
		}
		return arrayNumber;
	}

	public boolean AdminLogIn(String name, String password) {
		boolean adminLogIn = false;
		if (users[0].getName().equals(name) && users[0].getPassword().equals(password)) {
			adminLogIn = true;
		}
		return adminLogIn;
	}

	public void addWallet(int userNumber, int money) {
		users[userNumber].setWallet(money + users[userNumber].getWallet());
	}

	public Bus[] getBuses() {
		return buses;
	}

	public User[] getUsers() {
		return users;
	}

	public void setBuses(Bus[] buses) {
		this.buses = buses;
	}

	public Route[][] getRoutes() {
		return routes;
	}
}