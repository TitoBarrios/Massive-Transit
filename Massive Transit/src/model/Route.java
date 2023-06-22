package model;

public class Route {
	String name;
	Ticket[] tickets;
	Stop[] stops;
	boolean disponibility;
	
	public static final int MAX_TICKETS = 80;
	public static final int MAX_STOPS = 1;
	
	public Route(String name, Stop[] stops) {
		this.name = name;
		this.stops = stops;
		tickets = new Ticket[MAX_TICKETS];
	}
	
	public void setTickets(Ticket[] tickets) {
		this.tickets = tickets;
	}
	
	public void setTicket(Ticket ticket, int position) {
		this.tickets[position] = ticket;
	}
	
	public void setDisponibility(boolean disponibility) {
		this.disponibility = disponibility;
	}
	
	public String getName() {
		return name;
	}
	
	public Stop[] getStops() {
		return stops;
	}
	
	public Ticket[] getTickets() {
		return tickets;
	}
	
	public boolean getDisponibility() {
		return disponibility;
	}
}
