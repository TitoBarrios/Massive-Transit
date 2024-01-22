package model;

import java.time.LocalDateTime;
import java.time.LocalTime;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class Calculator {
	private DataCenter dataCenter;

	public Calculator() {
		dataCenter = new DataCenter();
	}

	public void addWallet(User user, int money) {
		user.setWallet(money + user.getWallet());
	}

	public void deleteRelationship(User user, int relationshipArrayNumber) {
		user.deleteRelationship(relationshipArrayNumber);
	}

	public void deleteSubscription(User user, int subscriptionArrayNumber) {
		user.deleteSubscription(subscriptionArrayNumber);
	}

	public boolean isDayForWork(DayOfWeek[] laboralDays, LocalDateTime timeToCheck) {
		for (int i = 0; i < laboralDays.length; i++) {
			if (timeToCheck.getDayOfWeek().equals(laboralDays[i])) {
				return true;
			}
		}
		return false;
	}

	public boolean isEnoughMoney(User user, Vehicle vehicle) {
		if (user.getWallet() >= vehicle.getPrice()) {
			return true;
		}
		return false;
	}

	public boolean isUserAvailable(String name) {
		for (User[] users : dataCenter.getUsers()) {
			for (User user : users) {
				if (user != null) {
					if (user.getName().equals(name)) {
						return false;
					}
				}

			}
		}
		return true;
	}

	public void editCompanyDescription(Company company, String description) {
		company.setDescription(description);
	}

	public void editRoute(RouteSequence routeSeq, String[] routesName) {
		if (routeSeq != null) {
			if (routesName.length == routeSeq.getRoutes().length) {
				for (int i = 0; i < routeSeq.getRoutes().length; i++) {
					routeSeq.getRoutes()[i].setName(routesName[i]);
				}
			}
		}
	}

	public void editVehicle(Vehicle vehicle, RouteSequence route, int ticketPrice) {
		vehicle.setRouteSequence(route);
		vehicle.setPrice(ticketPrice);
	}

	public void checkRouteSequenceAvailability(RouteSequence routeSeq) {
		boolean isLaboralDay = false;
		for (int i = 0; i < routeSeq.getLaboralDays().length; i++) {
			DayOfWeek laboralDay = routeSeq.getLaboralDays()[i];
			if (laboralDay != null) {
				if (laboralDay.equals(dataCenter.getCurrentDate().getDayOfWeek())) {
					routeSeq.setAvailability(true);
					isLaboralDay = true;
					break;
				}
			}
		}

		if (!isLaboralDay) {
			routeSeq.setAvailability(false);
		}

		boolean isAnyRouteAvailable = false;
		for (Route route : routeSeq.getRoutes()) {
			checkRouteAvailability(route);
			if (route.getAvailability()) {
				isAnyRouteAvailable = true;
			}
		}

		if (!isAnyRouteAvailable) {
			routeSeq.setAvailability(false);
		}
	}

	public void checkRouteAvailability(Route route) {
		LocalDateTime currentDate = dataCenter.getCurrentDate();
		if (currentDate.isAfter(route.getStops()[Value.ENTRY.getValue()])
				|| currentDate.isEqual(route.getStops()[Value.ENTRY.getValue()])) {
			route.setAvailability(false);
			return;
		}
		route.setAvailability(true);
	}

	public void checkSubscriptionsPayment(User user) {
		for (Subscription subscription : user.getSubscriptions()) {
			if (subscription != null) {
				if (subscription.getDayOfWeek().equals(dataCenter.getCurrentDate().getDayOfWeek())
						&& subscription.getRoutes()[Value.ENTRY.getValue()]
								// Tomo solo 5 minutos pero se puede escribir para que sea 15 minutos antes, etc
								.getStops()[0].plusMinutes(5).isAfter(dataCenter.getCurrentDate())
						&& isEnoughMoney(user, subscription.getVehicle())) {
					createTicket(user, user, subscription.getVehicle(), subscription.getRoutes());
				}
			}
		}
	}

	public void checkVehicleRevenue(Vehicle vehicle, Value statisticsType) {
		LocalDateTime currentDate = dataCenter.getCurrentDate();
		int[][] revenue = vehicle.getRevenue();
		int startingTicket = revenue[Value.GENERAL.getValue()][Value.CURRENT_TICKET.getValue()] + 1;
		boolean[] firstTime = new boolean[revenue.length];

		for (int i = 0; i < revenue.length; i++) {
			int currentTicketNumber = revenue[i][Value.CURRENT_TICKET.getValue()] + 1;
			if (currentTicketNumber < startingTicket) {
				startingTicket = currentTicketNumber;
			}
			if (revenue[i][Value.REVENUE.getValue()] == 0) {
				firstTime[i] = true;
				startingTicket = 0;
			}
		}

		if (vehicle.getTickets()[startingTicket] != null) {
			for (int i = startingTicket; i < vehicle.getTickets().length; i++) {
				if (vehicle.getTickets()[i] != null) {
					int price = vehicle.getTickets()[i].getPrice();
					switch (statisticsType) {
					case GENERAL:
						int generalLastTicketNumber = revenue[Value.GENERAL.getValue()][Value.CURRENT_TICKET
								.getValue()];
						if (i > generalLastTicketNumber
								|| (firstTime[Value.GENERAL.getValue()] && generalLastTicketNumber == i)) {
							revenue[Value.GENERAL.getValue()][Value.REVENUE.getValue()] += price;
							revenue[Value.GENERAL.getValue()][Value.CURRENT_TICKET.getValue()] = i;
						}
					case YEARLY:
						int yearlyLastTicketNumber = revenue[Value.YEARLY.getValue()][Value.CURRENT_TICKET.getValue()];
						if (i > yearlyLastTicketNumber
								|| (firstTime[Value.YEARLY.getValue()] && startingTicket == yearlyLastTicketNumber)) {
							if (vehicle.getTickets()[yearlyLastTicketNumber].getRoutes()[Value.EXIT.getValue()]
									.getStops()[Value.EXIT.getValue()].getYear() != currentDate.getYear()) {
								revenue[Value.YEARLY.getValue()][Value.REVENUE.getValue()] = 0;
							}
							revenue[Value.YEARLY.getValue()][Value.REVENUE.getValue()] += price;
							revenue[Value.YEARLY.getValue()][Value.CURRENT_TICKET.getValue()] = i;
						}
						if (statisticsType != Value.GENERAL) {
							break;
						}
					case MONTHLY:
						int monthlyLastTicketNumber = revenue[Value.MONTHLY.getValue()][Value.CURRENT_TICKET
								.getValue()];
						if (i > monthlyLastTicketNumber
								|| (firstTime[Value.MONTHLY.getValue()] && monthlyLastTicketNumber == i)) {
							if (vehicle.getTickets()[monthlyLastTicketNumber].getRoutes()[Value.EXIT.getValue()]
									.getStops()[Value.EXIT.getValue()].getMonthValue() != currentDate.getMonthValue()
									|| vehicle.getTickets()[monthlyLastTicketNumber].getRoutes()[Value.EXIT.getValue()]
											.getStops()[Value.EXIT.getValue()].getYear() != currentDate.getYear()) {

								revenue[Value.MONTHLY.getValue()][Value.REVENUE.getValue()] = 0;
							}
							revenue[Value.MONTHLY.getValue()][Value.REVENUE.getValue()] += price;
							revenue[Value.MONTHLY.getValue()][Value.CURRENT_TICKET.getValue()] = i;
						}
						if (statisticsType != Value.GENERAL) {
							break;
						}
					case DAILY:
						int dailyLastTicketNumber = revenue[Value.DAILY.getValue()][Value.CURRENT_TICKET.getValue()];
						if (i > dailyLastTicketNumber
								|| (firstTime[Value.DAILY.getValue()] && dailyLastTicketNumber == i)) {
							if (vehicle.getTickets()[dailyLastTicketNumber].getRoutes()[Value.EXIT.getValue()]
									.getStops()[Value.EXIT.getValue()].getDayOfMonth() != currentDate.getDayOfMonth()
									|| vehicle.getTickets()[dailyLastTicketNumber].getRoutes()[Value.EXIT.getValue()]
											.getStops()[Value.EXIT.getValue()]
											.getMonthValue() != currentDate.getMonthValue()
									|| vehicle.getTickets()[dailyLastTicketNumber].getRoutes()[Value.EXIT.getValue()]
											.getStops()[Value.EXIT.getValue()].getYear() != currentDate.getYear()) {
								revenue[Value.DAILY.getValue()][Value.REVENUE.getValue()] = 0;
							}
							revenue[Value.DAILY.getValue()][Value.REVENUE.getValue()] += price;
							revenue[Value.DAILY.getValue()][Value.CURRENT_TICKET.getValue()] = i;
						}
						break;
					default:
						break;
					}
				} else {
					break;
				}
			}
		}
	}

	public void checkVehicleAvailability(Vehicle vehicle) {
		LocalDateTime currentDate = dataCenter.getCurrentDate();
		boolean isCapacityAvailable = false;
		refreshRouteSeq(vehicle.getRouteSeq(), currentDate.toLocalDate());

		for (Ticket ticket : vehicle.getTickets()) {
			if (ticket != null) {
				if (currentDate.isAfter(ticket.getRoutes()[Value.ENTRY.getValue()].getStops()[Value.ENTRY.getValue()])
						|| currentDate
								.isAfter(ticket.getRoutes()[Value.EXIT.getValue()].getStops()[Value.EXIT.getValue()])) {
					setUsersTicketAvailability(ticket, false);
					vehicle.setCurrentCapacity(vehicle.getCapacity()[Value.CURRENT.getValue()] - 1);
				} else if (!ticket.getAvailability()
						&& (ticket.getRoutes()[Value.ENTRY.getValue()].getStops()[Value.ENTRY.getValue()]
								.isAfter(currentDate)
								|| ticket.getRoutes()[Value.ENTRY.getValue()].getStops()[Value.ENTRY.getValue()]
										.isAfter(currentDate))) {
					ticket.setAvailability(true);
					setUsersTicketAvailability(ticket, true);
					vehicle.setCurrentCapacity(vehicle.getCapacity()[Value.CURRENT.getValue()] + 1);
				}
			}
		}

		if (vehicle.getCapacity()[Value.MAXIMUM.getValue()] <= vehicle.getCapacity()[Value.CURRENT.getValue()]) {
			isCapacityAvailable = false;
		} else {
			isCapacityAvailable = true;
		}

		if (isCapacityAvailable && vehicle.getRouteSeq().getAvailability()) {
			vehicle.setAvailability(true);
			return;
		}
		vehicle.setAvailability(false);

	}

	public void checkVehiclesAvailability(Vehicle[] vehicles) {
		for (Vehicle vehicle : vehicles) {
			if (vehicle != null) {
				checkVehicleAvailability(vehicle);
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

	public void createRelationship(User user, User relationship) {
		user.addRelationship(relationship);
	}

	public void createRouteSeq(Company owner, String name, String initialTime, DayOfWeek[] laboralDays, int stopsNumber,
			int[] timeLapse) {
		Route[] routes = new Route[stopsNumber - 1];
		LocalDateTime[][] stops = new LocalDateTime[stopsNumber][2];
		stops[0][Value.ENTRY.getValue()] = setLaboralDays(laboralDays,
				LocalDateTime.of(dataCenter.getCurrentDate().toLocalDate(), LocalTime.parse(initialTime)));
		stops[0][Value.EXIT.getValue()] = setLaboralDays(laboralDays,
				stops[0][Value.ENTRY.getValue()].plusMinutes(timeLapse[0]));
		routes[0] = new Route("0 a 1",
				new LocalDateTime[] { stops[0][Value.ENTRY.getValue()], stops[0][Value.EXIT.getValue()] },
				new String[] { "0", "1" });
		for (int i = 1; i < routes.length; i++) {
			stops[i][Value.ENTRY.getValue()] = stops[i - 1][Value.EXIT.getValue()];
			stops[i][Value.EXIT.getValue()] = setLaboralDays(laboralDays,
					stops[i][Value.ENTRY.getValue()].plusMinutes(timeLapse[i]));
			routes[i] = new Route(i + " a " + (i + 1),
					new LocalDateTime[] { stops[i][Value.ENTRY.getValue()], stops[i][Value.EXIT.getValue()] },
					new String[] { i + "", (i + 1) + "" });
		}
		RouteSequence routeSeq = new RouteSequence(name, owner, laboralDays, routes);
		dataCenter.addRouteSeq(routeSeq);
		owner.addRouteSeq(routeSeq);
	}

	public void createSubscription(User user, DayOfWeek dayOfWeek, Vehicle vehicle, Route[] routes) {
		user.addSubscription(new Subscription(dayOfWeek, vehicle, routes));
	}

	public void createTicket(User owner, User buyer, Vehicle vehicle, Route[] routes) {
		if (isEnoughMoney(buyer, vehicle)) {
			buyer.setWallet(owner.getWallet() - vehicle.getPrice());
			Ticket ticket = new Ticket(owner, buyer, vehicle,
					new Route[] { new Route(routes[Value.ENTRY.getValue()]), new Route(routes[Value.EXIT.getValue()]) },
					vehicle.getPrice());
			String name = dataCenter.getCurrentDate().getYear() + "_" + "_" + vehicle.getVehicleType().getValue()
					+ ticket.getRoutes()[Value.ENTRY.getValue()].getStops()[Value.ENTRY.getValue()].getDayOfMonth()
					+ ticket.getRoutes()[Value.ENTRY.getValue()].getStops()[Value.ENTRY.getValue()].getMonth()
					+ ticket.getRoutes()[Value.ENTRY.getValue()].getStops()[Value.ENTRY.getValue()].toLocalTime()
					+ ticket.getRoutes()[Value.EXIT.getValue()].getStops()[Value.EXIT.getValue()].toLocalTime();
			ticket.setName(name);
			vehicle.addTicket(ticket);
			owner.addTicket(ticket);
			buyer.addTicket(ticket);
		}
	}

	public void createUser(Value type, String name, String password) {
		if (isUserAvailable(name)) {
			switch (type) {
			case USER:
				dataCenter.addUser(type, new User(name, password));
				break;
			case COMPANY:
				dataCenter.addUser(type, new Company(name, password));
				break;
			default:
				return;
			}
		}
	}

	public void createVehicle(VehicleType vehicleType, Company company, String plate, RouteSequence routeSeq, int price,
			int capacity) {
		switch (vehicleType) {
		case AIRPLANE:
			Airplane airplane = new Airplane(company, plate, routeSeq, price, capacity);
			dataCenter.addVehicle(vehicleType, airplane);
			company.addVehicle(vehicleType, airplane);
			break;
		case BUS:
			Bus bus = new Bus(company, plate, routeSeq, price, capacity);
			dataCenter.addVehicle(vehicleType, bus);
			company.addVehicle(vehicleType, bus);
			break;
		case SHIP:
			Ship ship = new Ship(company, plate, routeSeq, price, capacity);
			dataCenter.addVehicle(vehicleType, ship);
			company.addVehicle(vehicleType, ship);
			break;
		case TRAVEL_BUS:
			TravelBus travelBus = new TravelBus(company, plate, routeSeq, price, capacity);
			dataCenter.addVehicle(vehicleType, travelBus);
			company.addVehicle(vehicleType, travelBus);
			break;
		}
	}

	public void deleteVehicle(Vehicle vehicle) {
		vehicle = null;
	}

	public boolean logIn(Value type, String name, String password) {
		for (User user : dataCenter.getUsers()[type.getValue()]) {
			if (user != null) {
				if (user.getName().equals(name) && user.getPassword().equals(password)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean logInAdmin(String name, String password) {
		User admin = dataCenter.getUsers()[Value.USER.getValue()][0];
		if (admin.getName().equals(name) && admin.getPassword().equals(password)) {
			return true;
		}
		return false;
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

	public void refreshRouteSeq(RouteSequence routeSeq, LocalDate currentDate) {
		if (routeSeq.getRoutes()[0].getStops()[Value.ENTRY.getValue()].getDayOfMonth() != currentDate.getDayOfMonth()) {
			for (Route route : routeSeq.getRoutes()) {
				route.getStops()[Value.ENTRY.getValue()] = LocalDateTime.of(currentDate,
						route.getStops()[Value.ENTRY.getValue()].toLocalTime());
				route.getStops()[Value.EXIT.getValue()] = LocalDateTime.of(currentDate,
						route.getStops()[Value.EXIT.getValue()].toLocalTime());
			}
		}
		checkRouteSequenceAvailability(routeSeq);
	}

	public int searchUserArrayNumber(Value type, String name) {
		for (int i = 0; i < dataCenter.getUsers()[type.getValue()].length; i++) {
			if (dataCenter.getUsers()[type.getValue()][i] != null) {
				if (dataCenter.getUsers()[type.getValue()][i].getName().equals(name)) {
					return i;
				}
			}
		}
		return -1;
	}

	public LocalDateTime setLaboralDays(DayOfWeek[] laboralDays, LocalDateTime time) {
		boolean isDayForWork = isDayForWork(laboralDays, time);
		while (!isDayForWork) {
			time = time.plusDays(1);
			isDayForWork = isDayForWork(laboralDays, time);
		}
		return time;
	}

	public void setUsersTicketAvailability(Ticket ticket, boolean availability) {
		for (User user : dataCenter.getUsers()[Value.USER.getValue()]) {
			if (user != null) {
				for (Ticket currentTicket : user.getTicketHistory()) {
					if (currentTicket != null) {
						if (currentTicket.equals(ticket)) {
							currentTicket.setAvailability(availability);
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