package com.titobarrios.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import com.titobarrios.db.CurrentDate;
import com.titobarrios.db.DB;
import com.titobarrios.services.LaboralDays;

public class RouteSequence {
	private DayOfWeek[] laboralDays;
	private Route[] routes;
	private ArrayList<Coupon> coupons;
	private Company owner;
	private String name;
	private boolean isAvailable;

	public RouteSequence(String name, Company owner, DayOfWeek[] laboralDays, Route[] routes) {
		this.name = name;
		this.owner = owner;
		this.laboralDays = laboralDays;
		this.routes = routes;
		coupons = new ArrayList<Coupon>();
	}

	public RouteSequence(Company owner, String name, String initialTime, DayOfWeek[] laboralDays, int stopsNumber,
	int[] timeLapse) {
		this.name = name;
		this.owner = owner;
		this.laboralDays = laboralDays;
		coupons = new ArrayList<Coupon>();
		initialize(initialTime, stopsNumber, timeLapse);
	}

	private void initialize(String initialTime, int stopsNumber, int[] timeLapse) {
		routes = new Route[stopsNumber - 1];
		LocalDateTime[][] stops = new LocalDateTime[stopsNumber][2];
		stops[0][Route.StopType.ENTRY.ordinal()] = LaboralDays.adjustTime(laboralDays,
				LocalDateTime.of(CurrentDate.get().toLocalDate(), LocalTime.parse(initialTime)));
		stops[0][Route.StopType.EXIT.ordinal()] = LaboralDays.adjustTime(laboralDays,
				stops[0][Route.StopType.ENTRY.ordinal()].plusMinutes(timeLapse[0]));
		routes[0] = new Route("0 a 1", new LocalDateTime[] { stops[0][Route.StopType.ENTRY.ordinal()],
				stops[0][Route.StopType.EXIT.ordinal()] }, new String[] { "0", "1" });
		for (int i = 1; i < routes.length; i++) {
			stops[i][Route.StopType.ENTRY.ordinal()] = stops[i - 1][Route.StopType.EXIT.ordinal()];
			stops[i][Route.StopType.EXIT.ordinal()] = LaboralDays.adjustTime(laboralDays,
					stops[i][Route.StopType.ENTRY.ordinal()].plusMinutes(timeLapse[i]));
			routes[i] = new Route(i + " a " + (i + 1), new LocalDateTime[] { stops[i][Route.StopType.ENTRY.ordinal()],
					stops[i][Route.StopType.EXIT.ordinal()] }, new String[] { i + "", (i + 1) + "" });
		}
		DB.store(this);
		owner.add(this);
	}

	public void refresh() {
		LocalDate currentDate = CurrentDate.get().toLocalDate();
		if (this.getRoutes()[0].getStops()[Route.StopType.ENTRY.ordinal()].getDayOfMonth() != currentDate
				.getDayOfMonth()) {
			for (Route route : this.getRoutes()) {
				route.setStops(new LocalDateTime[] {LocalDateTime.of(currentDate,
					route.getStops()[Route.StopType.ENTRY.ordinal()].toLocalTime()), LocalDateTime.of(currentDate,
					route.getStops()[Route.StopType.EXIT.ordinal()].toLocalTime())});
			}
		}
		checkAvailability();
	}

    public void checkAvailability() {
		this.setAvailable(isLaboralDay() && isAnyRouteAvailable() ? true : false);
    }

	private boolean isLaboralDay(){
		for (DayOfWeek laboralDay : this.getLaboralDays()) 
			if (laboralDay.equals(CurrentDate.get().getDayOfWeek())) 
				return true;
		return false;
	}

	private boolean isAnyRouteAvailable() {
		for(Route route : this.getRoutes())
			if(route.getIsAvailable()) return true;
		return false;
	}

	public void add(Coupon coupon) {
		coupons.add(coupon);
	}

	public void add(Route newRoute, int routeNumber) {
		routes[routeNumber] = newRoute;
	}

	public DayOfWeek[] getLaboralDays() {
		return laboralDays;
	}

	public void setLaboralDays(DayOfWeek[] laboralDays) {
		this.laboralDays = laboralDays;
	}

	public Route[] getRoutes() {
		return routes;
	}

	public void setRoutes(Route[] routes) {
		this.routes = routes;
	}

	public Coupon[] getApplicableCoupons() {
		return coupons.toArray(Coupon[]::new);
	}

	public Company getOwner() {
		return owner;
	}

	public void setOwner(Company owner) {
		this.owner = owner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getIsAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
}
