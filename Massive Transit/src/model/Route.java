package model;

import java.time.LocalDateTime;

public class Route {
	String name;
	Ticket[] tickets;
	LocalDateTime[] stops;
	boolean disponibility;
	
	public static final int MAX_TICKETS = 80;
	public static final int MAX_STOPS = 2;
	
	public Route(String name, LocalDateTime[] stops) {
		this.name = name;
		this.stops = new LocalDateTime[MAX_STOPS];
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
	
	public LocalDateTime[] getStops() {
		return stops;
	}
	
	public LocalDateTime getStop(int number) {
		return stops[number];
	}
	
	public Ticket[] getTickets() {
		return tickets;
	}
	
	public boolean getDisponibility() {
		return disponibility;
	}
}
