package model;

import java.time.LocalDateTime;

public class Calculator {
	private LocalDateTime currentDate;
	Bus[] buses;
	public static final int MAX_BUSES = 30;

	public Calculator() {
		currentDate = LocalDateTime.now();
		buses = new Bus[MAX_BUSES];
	}

	public void ticketCreator(User owner, int chairNumber, int busPosition, int routePosition) {
		Ticket ticket = new Ticket(owner, chairNumber, buses[busPosition].getRoutes()[routePosition].getStops()[1].getTime());
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
	
	public Bus[] getBuses(){
		return buses;
	}
	
	public void setBuses(Bus[] buses) {
		this.buses = buses;
	}
}