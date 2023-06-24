package model;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.DayOfWeek;

public class Calculator {
	private LocalDateTime currentDate;
	Bus[] buses;
	public static final int MAX_BUSES = 30;
	
	public Calculator() {
		currentDate = LocalDateTime.now();
		buses = new Bus[MAX_BUSES];
	}
	
	public Route[] LaboralRoutesCreator(int timeLapse) {
		Stop[] stop12 = new Stop[1];
		LocalTime stop1t = LocalTime.parse("6:00:00");
		LocalTime stop2t = stop1t.plusMinutes(timeLapse);
		stop12[0] = new Stop("1", LaboralDaySetter(stop1t));
		stop12[1] = new Stop("2", LaboralDaySetter(stop2t));
		
		Stop[] stop23 = new Stop[1];
		stop23[0] = stop12[1];
		LocalTime stop3t = stop2t.plusMinutes(timeLapse);
		stop23[1] = new Stop("3", LaboralDaySetter(stop3t));
		
		Stop[] stop34 = new Stop[1];
		stop34[0] = stop23[1];
		LocalTime stop4t = stop3t.plusMinutes(timeLapse);
		stop34[1] = new Stop("4", LaboralDaySetter(stop4t));
		
		Stop[] stop45 = new Stop[1];
		stop45[0] = stop34[1];
		LocalTime stop5t = stop4t.plusMinutes(timeLapse);
		stop45[1] = new Stop("5", LaboralDaySetter(stop5t));
		
		Stop[] stop56 = new Stop[1];
		stop56[0] = stop45[1];
		LocalTime stop6t = stop5t.plusMinutes(timeLapse);
		stop56[1] = new Stop("6", LaboralDaySetter(stop6t));
		
		Stop[] stop67 = new Stop[1];
		stop67[0] = stop56[1];
		LocalTime stop7t = stop6t.plusMinutes(timeLapse);
		stop67[1] = new Stop("7", LaboralDaySetter(stop7t));
		
		Stop[] stop78 = new Stop[1];
		stop78[0] = stop67[1];
		LocalTime stop8t = stop7t.plusMinutes(timeLapse);
		stop78[1] = new Stop("8", LaboralDaySetter(stop8t));
		
		Stop[] stop89 = new Stop[1];
		stop89[0] = stop78[1];
		LocalTime stop9t = stop8t.plusMinutes(timeLapse);
		stop89[1] = new Stop("9", LaboralDaySetter(stop9t));
		
		Stop[] stop910 = new Stop[1];
		stop910[0] = stop89[1];
		LocalTime stop10t = stop9t.plusMinutes(timeLapse);
		stop910[1] = new Stop("10", LaboralDaySetter(stop10t));
		
		Stop[] stop1011 = new Stop[1];
		stop1011[0] = stop910[1];
		LocalTime stop11t = stop10t.plusMinutes(timeLapse);
		stop1011[1] = new Stop("11", LaboralDaySetter(stop11t));
		
		Stop[] stop1112 = new Stop[1];
		stop1112[0] = stop1011[1];
		LocalTime stop12t = stop11t.plusMinutes(timeLapse);
		stop1112[1] = new Stop("12", LaboralDaySetter(stop12t));
		
		Stop[] stop1213 = new Stop[1];
		stop1213[0] = stop1112[1];
		LocalTime stop13t = stop12t.plusMinutes(timeLapse);
		stop1213[1] = new Stop("13", LaboralDaySetter(stop13t));
		
		Stop[] stop1314 = new Stop[1];
		stop1314[0] = stop1213[1];
		LocalTime stop14t = stop13t.plusMinutes(timeLapse);
		stop1314[1] = new Stop("14", LaboralDaySetter(stop14t));
		
		Stop[] stop1415 = new Stop[1];
		stop1415[0] = stop1314[1];
		LocalTime stop15t = stop14t.plusMinutes(timeLapse);
		stop1415[1] = new Stop("15", LaboralDaySetter(stop15t));
		
		Stop[] stop1516 = new Stop[1];
		stop1516[0] = stop1415[1];
		LocalTime stop16t = stop15t.plusMinutes(timeLapse);
		stop1516[1] = new Stop("16", LaboralDaySetter(stop16t));
		
		Stop[] stop1617 = new Stop[1];
		stop1617[0] = stop1516[1];
		LocalTime stop17t = stop16t.plusMinutes(timeLapse);
		stop1617[1] = new Stop("17", LaboralDaySetter(stop17t));
		
		Stop[] stop1718 = new Stop[1];
		stop1718[0] = stop1617[1];
		LocalTime stop18t = stop17t.plusMinutes(timeLapse);
		stop1718[1] = new Stop("18", LaboralDaySetter(stop18t));
		
		Stop[] stop1819 = new Stop[1];
		stop1819[0] = stop1718[1];
		LocalTime stop19t = stop18t.plusMinutes(timeLapse);
		stop1819[1] = new Stop("19", LaboralDaySetter(stop19t));
		
		Stop[] stop1920 = new Stop[1];
		stop1920[0] = stop1819[1];
		LocalTime stop20t = stop19t.plusMinutes(timeLapse);
		stop1920[1] = new Stop("20", LaboralDaySetter(stop20t));
		
		Stop[] stop2021 = new Stop[1];
		stop2021[0] = stop1920[1];
		LocalTime stop21t = stop20t.plusMinutes(timeLapse);
		stop2021[1] = new Stop("21", LaboralDaySetter(stop21t));
		
		Stop[] stop2122 = new Stop[1];
		stop2122[0] = stop2021[1];
		LocalTime stop22t = stop21t.plusMinutes(timeLapse);
		stop2122[1] = new Stop("22", LaboralDaySetter(stop22t));
		
		Stop[] stop2223 = new Stop[1];
		stop2223[0] = stop2122[1];
		LocalTime stop23t = stop22t.plusMinutes(timeLapse);
		stop2223[1] = new Stop("23", LaboralDaySetter(stop23t));
		
		Stop[] stop2324 = new Stop[1];
		stop2324[0] = stop2223[1];
		LocalTime stop24t = stop23t.plusMinutes(timeLapse);
		stop2324[1] = new Stop("24", LaboralDaySetter(stop24t));
		
		Stop[] stop2425 = new Stop[1];
		stop2425[0] = stop2324[1];
		LocalTime stop25t = stop24t.plusMinutes(timeLapse);
		stop2425[1] = new Stop("25", LaboralDaySetter(stop25t));
		
		Stop[] stop2526 = new Stop[1];
		stop2526[0] = stop2425[1];
		LocalTime stop26t = stop25t.plusMinutes(timeLapse);
		stop2526[1] = new Stop("26", LaboralDaySetter(stop26t));
		
		Stop[] stop2627 = new Stop[1];
		stop2627[0] = stop2526[1];
		LocalTime stop27t = stop26t.plusMinutes(timeLapse);
		stop2627[1] = new Stop("27", LaboralDaySetter(stop27t));
		
		Stop[] stop2728 = new Stop[1];
		stop2728[0] = stop2627[1];
		LocalTime stop28t = stop27t.plusMinutes(timeLapse);
		stop2728[1] = new Stop("28", LaboralDaySetter(stop28t));
		
		Stop[] stop2829 = new Stop[1];
		stop2829[0] = stop2728[1];
		LocalTime stop29t = stop28t.plusMinutes(timeLapse);
		stop2829[1] = new Stop("29", LaboralDaySetter(stop29t));
		
		Stop[] stop2930 = new Stop[1];
		stop2930[0] = stop2829[1];
		LocalTime stop30t = stop29t.plusMinutes(timeLapse);
		stop2930[1] = new Stop("30", LaboralDaySetter(stop30t));
		
		Stop[] stop3031 = new Stop[1];
		stop3031[0] = stop2930[1];
		LocalTime stop31t = stop30t.plusMinutes(timeLapse);
		stop3031[1] = new Stop("31", LaboralDaySetter(stop31t));
		
		
		Route[] routes = new Route[30];
		for(int i = routes.length - 1; i <= 0; i--) {
			routes[0] = new Route("1 a 2", stop12);
			routes[1] = new Route("2 a 3", stop23);
			routes[2] = new Route("3 a 4", stop34);
			routes[3] = new Route("4 a 5", stop45);
			routes[4] = new Route("5 a 6", stop56);
			routes[5] = new Route("6 a 7", stop67);
			routes[6] = new Route("7 a 8", stop78);
			routes[7] = new Route("8 a 9", stop89);
			routes[8] = new Route("9 a 10", stop910);
			routes[9] = new Route("10 a 11", stop1011);
			routes[10] = new Route("11 a 12", stop1112);
			routes[11] = new Route("12 a 13", stop1213);
			routes[12] = new Route("13 a 14", stop1314);
			routes[13] = new Route("14 a 15", stop1415);
			routes[i = 14] = new Route("15 a 16", stop1516);
			routes[15] = new Route("16 a 17", stop1617);
			routes[16] = new Route("17 a 18", stop1718);
			routes[17] = new Route("18 a 19", stop1819);
			routes[18] = new Route("19 a 20", stop1920);
			routes[19] = new Route("20 a 21", stop2021);
			routes[20] = new Route("21 a 22", stop2122);
			routes[21] = new Route("22 a 23", stop2223);
			routes[22] = new Route("23 a 24", stop2324);
			routes[23] = new Route("24 a 25", stop2425);
			routes[24] = new Route("25 a 26", stop2526);
			routes[25] = new Route("26 a 27", stop2627);
			routes[26] = new Route("27 a 28", stop2728);
			routes[27] = new Route("28 a 29", stop2829);
			routes[28] = new Route("29 a 30", stop2930);
			routes[29] = new Route("30 a 31", stop3031);
		}

		
		return routes;
	}

	public boolean isLaboralDay(DayOfWeek dayOfWeek) {
		boolean laboralDay = false;
		if (dayOfWeek == DayOfWeek.MONDAY || dayOfWeek == DayOfWeek.TUESDAY || dayOfWeek == DayOfWeek.WEDNESDAY
				|| dayOfWeek == DayOfWeek.THURSDAY || dayOfWeek == DayOfWeek.FRIDAY) {
			laboralDay = true;
		}
		return laboralDay;
	}

	public LocalDateTime LaboralDaySetter(LocalTime time) {
		LocalDate currentLocalDate = LocalDate.now();
		DayOfWeek dayOfWeek = currentLocalDate.getDayOfWeek();
		while(!isLaboralDay(dayOfWeek)) {
			currentLocalDate = currentLocalDate.plusDays(1);
			dayOfWeek = currentLocalDate.getDayOfWeek();
		}
		for (int i = 0; isLaboralDay(dayOfWeek); i++) {

		}
		LocalDateTime timeResult = LocalDateTime.of(currentLocalDate, time);
		return timeResult;
	}

	public void ticketCreator(User owner, int chairNumber, int busPosition, int routePosition) {
		Ticket ticket = new Ticket(owner, chairNumber,
				buses[busPosition].getRoutes()[routePosition].getStops()[1].getTime());
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

	public void routeAndBusDisponibilityChecker(Bus bus) {
		bus.setDisponibility(false);
		for (int o = 0; o < bus.getTickets().length; o++) {
			if (bus.getTickets()[o] == null) {
				bus.setDisponibility(true);
				for (int i = 0; i < bus.getRoutes().length; i++) {
					if (bus.getRoutes()[i] != null) {
						bus.getRoutes()[i].setDisponibility(false);
						for (int a = 0; i < bus.getRoutes()[i].getTickets().length; a++) {
							if (bus.getRoutes()[i].getTickets()[a] == null) {
								if ((currentDate.isBefore(bus.getRoutes()[i].getStops()[0].getTime())
										|| currentDate.isEqual(bus.getRoutes()[i].getStops()[0].getTime()))) {
									bus.getRoutes()[i].setDisponibility(true);
									a = bus.getRoutes()[i].getTickets().length;
								}
							}
						}
					}
				}
			}
		}
	}

	public Bus[] getBuses() {
		return buses;
	}

	public void setBuses(Bus[] buses) {
		this.buses = buses;
	}
}