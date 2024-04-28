package com.titobarrios.model;

import java.time.DayOfWeek;
import java.util.ArrayList;

public class Subscription {
	private Route[] routes;
	private DayOfWeek dayOfWeek;
	private RouteSequence routeSeq;
	private ArrayList<Ticket> bought;

	public Subscription(User user, DayOfWeek dayOfWeek, RouteSequence routeSeq, Route[] routes) {
		bought = new ArrayList<Ticket>();
		this.dayOfWeek = dayOfWeek;
		this.routeSeq = routeSeq;
		this.routes = routes;
		user.add(this);
	}

	public void add(Ticket ticket) {
		bought.add(ticket);
	}

	public DayOfWeek getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(DayOfWeek dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public RouteSequence getRouteSeq() {
		return routeSeq;
	}

	public void setRouteSeq(RouteSequence routeSeq) {
		this.routeSeq = routeSeq;
	}

	public Route[] getRoutes() {
		return routes;
	}

	public void setRoutes(Route[] routes) {
		this.routes = routes;
	}

	public Ticket[] getTickets() {
		return bought.toArray(Ticket[]::new);
	}

	public String info() {
		StringBuilder builder = new StringBuilder();
		builder.append("Secuencia de Ruta: ").append(routeSeq.getId())
				.append("\nEmpresa: ").append(routeSeq.getOwner().getId()).append("\nEntrada: ")
				.append(routes[Route.StopType.ENTRY.ordinal()].getStopsName()[Route.StopType.ENTRY.ordinal()])
				.append(' ').append(routes[Route.StopType.ENTRY.ordinal()].getStops()[Route.StopType.ENTRY.ordinal()])
				.append("\nSalida: ")
				.append(routes[Route.StopType.EXIT.ordinal()].getStopsName()[Route.StopType.EXIT.ordinal()]).append(' ')
				.append(routes[Route.StopType.EXIT.ordinal()].getStops()[Route.StopType.EXIT.ordinal()]);
		return builder.toString();
	}
}
