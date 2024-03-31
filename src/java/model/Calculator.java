package model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

import db.DataCenter;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class Calculator {
	private DataCenter dataCenter;

	public Calculator() {
		dataCenter = new DataCenter();
	}

	// Ordenado
	public void addWallet(User user, int money) {
		user.setWallet(money + user.getWallet());
	}

	// Ordenado
	public int calculateGreatestOfThree(int a, int b, int c) {
		if (a >= b && a >= c) {
			return a;
		}
		if (b >= a && b >= c) {
			return b;
		}
		return c;
	}

	public int calculatePriceAfterDiscount(Coupon coupon, int price) {
		if (coupon != null) {
			switch (coupon.getDiscountType()) {
				case QUANTITY:
					int discount = coupon.getDiscount();
					if(discount > price) discount = price;
					return price - discount;
				case PERCENTAGE:
					if (price != 0) return (price % (coupon.getDiscount() / 100));
			}
		}
		return price;
	}

	public void checkCompanyRevenue(Company company) {
		for (Vehicle[] vehicles : company.getVehicles()) {
			for (Vehicle vehicle : vehicles) {
				if (vehicle != null) {
					checkVehicleRevenue(vehicle, Value.GENERAL);
					company.getRevenue()[Value.GENERAL
							.getValue()] += vehicle.getRevenue()[Value.GENERAL.getValue()][Value.REVENUE.getValue()];
					company.getRevenue()[Value.YEARLY
							.getValue()] += vehicle.getRevenue()[Value.YEARLY.getValue()][Value.REVENUE.getValue()];
					company.getRevenue()[Value.MONTHLY
							.getValue()] += vehicle.getRevenue()[Value.MONTHLY.getValue()][Value.REVENUE.getValue()];
					company.getRevenue()[Value.DAILY
							.getValue()] += vehicle.getRevenue()[Value.DAILY.getValue()][Value.REVENUE.getValue()];
				}
			}
		}
	}

	public void checkCouponAvailability(Coupon coupon) {
		boolean redeemAvailability = false, dateAvailability = false;
		if (coupon.getRedeems()[Coupon.RedeemType.CURRENT.ordinal()] < coupon.getRedeems()[Coupon.RedeemType.MAXIMUM
				.ordinal()]) {
			redeemAvailability = true;
		}
		if (coupon.getDates()[Value.STARTING.getValue()].isBefore(dataCenter.getCurrentDate())
				&& coupon.getDates()[Value.EXPIRATION.getValue()].isAfter(dataCenter.getCurrentDate())) {
			dateAvailability = true;
		}

		if (redeemAvailability && dateAvailability) {
			coupon.setAvailable(true);
		} else {
			coupon.setAvailable(false);
		}
	}

	public void checkRouteSequenceAvailability(RouteSequence routeSeq) {
		boolean isLaboralDay = false;
		for (int i = 0; i < routeSeq.getLaboralDays().length; i++) {
			DayOfWeek laboralDay = routeSeq.getLaboralDays()[i];
			if (laboralDay != null) {
				if (laboralDay.equals(dataCenter.getCurrentDate().getDayOfWeek())) {
					routeSeq.setAvailable(true);
					isLaboralDay = true;
					break;
				}
			}
		}

		if (!isLaboralDay) {
			routeSeq.setAvailable(false);
		}

		boolean isAnyRouteAvailable = false;
		for (Route route : routeSeq.getRoutes()) {
			checkRouteAvailability(route);
			if (route.getIsAvailable()) {
				isAnyRouteAvailable = true;
			}
		}

		if (!isAnyRouteAvailable) {
			routeSeq.setAvailable(false);
		}
	}

	public void checkRouteAvailability(Route route) {
		LocalDateTime currentDate = dataCenter.getCurrentDate();
		if (currentDate.isAfter(route.getStops()[Route.StopType.ENTRY.ordinal()])
				|| currentDate.isEqual(route.getStops()[Route.StopType.ENTRY.ordinal()])) {
			route.setIsAvailable(false);
			return;
		}
		route.setIsAvailable(true);
	}

	public void checkSubscriptionsPayment(User user) {
		for (Subscription subscription : user.getSubscriptions()) {
			if (subscription != null) {
				if (subscription.getDayOfWeek().equals(dataCenter.getCurrentDate().getDayOfWeek())
						&& subscription.getRoutes()[Route.StopType.ENTRY.ordinal()]
								// Tomo solo 5 minutos pero se puede escribir para que sea 15 minutos antes, etc
								.getStops()[0].plusMinutes(5).isAfter(dataCenter.getCurrentDate())
						&& isEnoughMoney(user, null, subscription.getVehicle())) {
					createTicket(user, user, null, subscription.getVehicle(), subscription.getRoutes());
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
					int price = vehicle.getTickets()[i].getPrice()[Ticket.PriceType.PAID.ordinal()];
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
							int yearlyLastTicketNumber = revenue[Value.YEARLY.getValue()][Value.CURRENT_TICKET
									.getValue()];
							if (i > yearlyLastTicketNumber
									|| (firstTime[Value.YEARLY.getValue()]
											&& startingTicket == yearlyLastTicketNumber)) {
								if (vehicle.getTickets()[yearlyLastTicketNumber].getRoutes()[Route.StopType.EXIT
										.ordinal()]
										.getStops()[Route.StopType.EXIT.ordinal()].getYear() != currentDate.getYear()) {
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
								if (vehicle.getTickets()[monthlyLastTicketNumber].getRoutes()[Route.StopType.EXIT
										.ordinal()]
										.getStops()[Route.StopType.EXIT.ordinal()]
										.getMonthValue() != currentDate.getMonthValue()
										|| vehicle.getTickets()[monthlyLastTicketNumber].getRoutes()[Route.StopType.EXIT
												.ordinal()].getStops()[Route.StopType.EXIT.ordinal()]
												.getYear() != currentDate.getYear()) {

									revenue[Value.MONTHLY.getValue()][Value.REVENUE.getValue()] = 0;
								}
								revenue[Value.MONTHLY.getValue()][Value.REVENUE.getValue()] += price;
								revenue[Value.MONTHLY.getValue()][Value.CURRENT_TICKET.getValue()] = i;
							}
							if (statisticsType != Value.GENERAL) {
								break;
							}
						case DAILY:
							int dailyLastTicketNumber = revenue[Value.DAILY.getValue()][Value.CURRENT_TICKET
									.getValue()];
							if (i > dailyLastTicketNumber
									|| (firstTime[Value.DAILY.getValue()] && dailyLastTicketNumber == i)) {
								if (vehicle.getTickets()[dailyLastTicketNumber].getRoutes()[Route.StopType.EXIT
										.ordinal()]
										.getStops()[Route.StopType.EXIT.ordinal()]
										.getDayOfMonth() != currentDate.getDayOfMonth()
										|| vehicle.getTickets()[dailyLastTicketNumber].getRoutes()[Route.StopType.EXIT
												.ordinal()].getStops()[Route.StopType.EXIT.ordinal()]
												.getMonthValue() != currentDate.getMonthValue()
										|| vehicle.getTickets()[dailyLastTicketNumber].getRoutes()[Route.StopType.EXIT
												.ordinal()].getStops()[Route.StopType.EXIT.ordinal()]
												.getYear() != currentDate.getYear()) {
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
				if (currentDate.isAfter(
						ticket.getRoutes()[Route.StopType.ENTRY.ordinal()].getStops()[Route.StopType.ENTRY.ordinal()])
						|| currentDate.isAfter(ticket.getRoutes()[Route.StopType.EXIT.ordinal()]
								.getStops()[Route.StopType.EXIT.ordinal()])) {
					setUsersTicketAvailability(ticket, false);
					vehicle.changeCurrentCapacity(vehicle.getCapacity()[Value.CURRENT.getValue()] - 1);
				} else if (!ticket.getIsAvailable() && (ticket.getRoutes()[Route.StopType.ENTRY.ordinal()]
						.getStops()[Route.StopType.ENTRY.ordinal()].isAfter(currentDate)
						|| ticket.getRoutes()[Route.StopType.ENTRY.ordinal()].getStops()[Route.StopType.ENTRY.ordinal()]
								.isAfter(currentDate))) {
					ticket.setAvailable(true);
					setUsersTicketAvailability(ticket, true);
					vehicle.changeCurrentCapacity(vehicle.getCapacity()[Value.CURRENT.getValue()] + 1);
				}
			}
		}

		if (vehicle.getCapacity()[Value.MAXIMUM.getValue()] <= vehicle.getCapacity()[Value.CURRENT.getValue()]) {
			isCapacityAvailable = false;
		} else {
			isCapacityAvailable = true;
		}

		if (isCapacityAvailable && vehicle.getRouteSeq().getIsAvailable()) {
			vehicle.setAvailable(true);
			return;
		}
		vehicle.setAvailable(false);

	}

	public void checkVehiclesAvailability(Vehicle[] vehicles) {
		for (Vehicle vehicle : vehicles) {
			if (vehicle != null) {
				checkVehicleAvailability(vehicle);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T[] combineArrays(T[] array1, T[] array2){
		ArrayList<T> array = new ArrayList<T>();
		for(int i = 0; i < array1.length + array2.length; i++){
			if(i < array1.length){
				if(array1[i] != null) array.add(array1[i]);
			} else{
				if(array2[i - array1.length] != null) array.add(array2[i - array1.length]);
			}
		}
		return Arrays.copyOf(array.toArray(), array.size(), (Class<? extends T[]>) array1.getClass());
	}

	public Vehicle.Type convertIntToVehicleType(int vehicleTypeInt) {
		switch (vehicleTypeInt) {
			case 0:
				return Vehicle.Type.AIRPLANE;
			case 1:
				return Vehicle.Type.BUS;
			case 2:
				return Vehicle.Type.SHIP;
			case 3:
				return Vehicle.Type.TRAVEL_BUS;
			default:
				return null;
		}
	}

	public void createCoupon(RedeemedCoupon.Type type, int id, Company owner, String name, String description,
			RedeemedCoupon.DiscountType discountType,
			String redeemWord, int discount, LocalDateTime[] dates, boolean isCumulative, Coupon.AppliesTo applicable,
			Vehicle[] vehicles, RouteSequence[] routeSeqs, Route[] routes, DayOfWeek[] redeemingDays,
			int userMaxRedeems, int maxRedeems) {
		Coupon coupon = new Coupon(type, id, owner, name, description, discountType, redeemWord, discount, dates,
				isCumulative, applicable, vehicles, routeSeqs, routes, redeemingDays, userMaxRedeems, maxRedeems);
		dataCenter.addCoupon(coupon);
		owner.addCoupon(coupon);
		setApplicableCouponObjects(coupon, vehicles, routeSeqs, routes);
		checkCouponAvailability(coupon);
	}

	public int createCouponID(Coupon coupon) {
		int id = 2023000;
		id += dataCenter.getCoupons().length - 1;
		return id;
	}

	public void createRelationship(User user, User relationship) {
		user.addRelationship(relationship);
	}

	public void createRouteSeq(Company owner, String name, String initialTime, DayOfWeek[] laboralDays, int stopsNumber,
			int[] timeLapse) {
		Route[] routes = new Route[stopsNumber - 1];
		LocalDateTime[][] stops = new LocalDateTime[stopsNumber][2];
		stops[0][Route.StopType.ENTRY.ordinal()] = setLaboralDays(laboralDays,
				LocalDateTime.of(dataCenter.getCurrentDate().toLocalDate(), LocalTime.parse(initialTime)));
		stops[0][Route.StopType.EXIT.ordinal()] = setLaboralDays(laboralDays,
				stops[0][Route.StopType.ENTRY.ordinal()].plusMinutes(timeLapse[0]));
		routes[0] = new Route("0 a 1", new LocalDateTime[] { stops[0][Route.StopType.ENTRY.ordinal()],
				stops[0][Route.StopType.EXIT.ordinal()] }, new String[] { "0", "1" });
		for (int i = 1; i < routes.length; i++) {
			stops[i][Route.StopType.ENTRY.ordinal()] = stops[i - 1][Route.StopType.EXIT.ordinal()];
			stops[i][Route.StopType.EXIT.ordinal()] = setLaboralDays(laboralDays,
					stops[i][Route.StopType.ENTRY.ordinal()].plusMinutes(timeLapse[i]));
			routes[i] = new Route(i + " a " + (i + 1), new LocalDateTime[] { stops[i][Route.StopType.ENTRY.ordinal()],
					stops[i][Route.StopType.EXIT.ordinal()] }, new String[] { i + "", (i + 1) + "" });
		}
		RouteSequence routeSeq = new RouteSequence(name, owner, laboralDays, routes);
		dataCenter.addRouteSeq(routeSeq);
		owner.addRouteSeq(routeSeq);
	}

	public void createSubscription(User user, DayOfWeek dayOfWeek, Vehicle vehicle, Route[] routes) {
		user.addSubscription(new Subscription(dayOfWeek, vehicle, routes));
	}

	public void createTicket(User owner, User buyer, Coupon coupon, Vehicle vehicle, Route[] routes) {
		if (isEnoughMoney(buyer, coupon, vehicle)) {
			int paidPrice = vehicle.getPrice(), realPrice = vehicle.getPrice();
			RedeemedCoupon redeemedCoupon = null;
			if (coupon != null) {
				paidPrice = calculatePriceAfterDiscount(coupon, realPrice);
				redeemedCoupon = redeemCoupon(buyer, coupon);
			}
			buyer.setWallet(owner.getWallet() - paidPrice);
			Ticket ticket = new Ticket(owner, buyer, redeemedCoupon, vehicle,
					new Route[] { new Route(routes[Route.StopType.ENTRY.ordinal()]),
							new Route(routes[Route.StopType.EXIT.ordinal()]) },
					new int[] { paidPrice, realPrice });
			String name = dataCenter.getCurrentDate().getYear() + "_" + "_" + vehicle.getType().ordinal()
					+ ticket.getRoutes()[Route.StopType.ENTRY.ordinal()].getStops()[Route.StopType.ENTRY.ordinal()]
							.getDayOfMonth()
					+ ticket.getRoutes()[Route.StopType.ENTRY.ordinal()].getStops()[Route.StopType.ENTRY.ordinal()]
							.getMonth()
					+ ticket.getRoutes()[Route.StopType.ENTRY.ordinal()].getStops()[Route.StopType.ENTRY.ordinal()]
							.toLocalTime()
					+ ticket.getRoutes()[Route.StopType.EXIT.ordinal()].getStops()[Route.StopType.EXIT.ordinal()]
							.toLocalTime();
			ticket.setName(name);
			vehicle.addTicket(ticket);
			owner.addTicket(ticket);
			buyer.addTicket(ticket);
		}
	}

	public void createUser(User.Type type, String name, String password) {
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

	public void createVehicle(Vehicle.Type vehicleType, Company company, String plate, RouteSequence routeSeq,
			int price, int capacity) {
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

	public void deleteObjectFromAList(Object[] objectList, Object object) {
		if (objectList != null) {
			Object[] newList = new Object[objectList.length];
			for (int i = 0; i < objectList.length; i++) {
				if (objectList[i] == null) {
					newList[i] = null;
					continue;
				}
				if (!objectList[i].equals(object)) {
					newList[i] = objectList[i];
				}
			}
			objectList = newList;
		}
	}

	public void deleteRelationship(User user, int relationshipArrayNumber) {
		user.deleteRelationship(relationshipArrayNumber);
	}

	public void deleteSubscription(User user, int subscriptionArrayNumber) {
		user.deleteSubscription(subscriptionArrayNumber);
	}

	public void deleteVehicle(Vehicle vehicle) {
		vehicle = null;
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

	public Coupon[] findAvailableCoupons(User user, Vehicle vehicle, RouteSequence routeSeq, Route[] routes) {
		ArrayList<Coupon> coupons = new ArrayList<Coupon>();

		if (vehicle.getApplicableCoupons() != null)
			for (Coupon coupon : vehicle.getApplicableCoupons()) {
				if (coupon != null) {
					checkCouponAvailability(coupon);
					if (coupon.getIsAvailable()) {
						coupons.add(coupon);
					}
				}
			}

		if (routeSeq.getApplicableCoupons() != null)
			for (Coupon coupon : routeSeq.getApplicableCoupons()) {
				if (coupon != null) {
					checkCouponAvailability(coupon);
					if (coupon.getIsAvailable()) {
						coupons.add(coupon);
					}
				}
			}

		for (Route route : routes) {
			if (route != null)
				if (route.getApplicableCoupons() != null)
					for (Coupon coupon : route.getApplicableCoupons()) {
						if (coupon != null) {
							checkCouponAvailability(coupon);
							if (coupon.getIsAvailable()) {
								coupons.add(coupon);
							}
						}
					}
		}
		return coupons.toArray(new Coupon[coupons.size()]);
	}

	public Coupon findBestCoupon(Coupon[] coupons, int price) {
		Coupon bestCoupon = null;
		int bestPrice = Integer.MAX_VALUE;
		for (Coupon coupon : coupons) {
			if (coupon.getType() == RedeemedCoupon.Type.PUBLIC) {
				int currentCouponPrice = calculatePriceAfterDiscount(coupon, price);
				if (currentCouponPrice < bestPrice) {
					bestCoupon = coupon;
					bestPrice = currentCouponPrice;
				}
			}
		}
		return bestCoupon;
	}

	public Coupon[] findPublicCoupons(Coupon[] coupons) {
		ArrayList<Coupon> publicCoupons = new ArrayList<Coupon>();
		for(Coupon coupon : coupons){
			if(coupon != null)
			if(coupon.getType().equals(RedeemedCoupon.Type.PUBLIC))
			publicCoupons.add(coupon);
		}
		return publicCoupons.toArray(new Coupon[publicCoupons.size()]);
	}

	public boolean isDayForWork(DayOfWeek[] laboralDays, LocalDateTime timeToCheck) {
		for (int i = 0; i < laboralDays.length; i++) {
			if (timeToCheck.getDayOfWeek().equals(laboralDays[i])) {
				return true;
			}
		}
		return false;
	}

	public boolean isEnoughMoney(User user, Coupon coupon, Vehicle vehicle) {
		if (user.getWallet() >= calculatePriceAfterDiscount(coupon, vehicle.getPrice())) {
			return true;
		}
		return false;
	}

	public boolean isRedeemWordAvailable(String redeemWord) {
		for (Coupon coupon : dataCenter.getCoupons()) {
			if (coupon != null) {
				if (coupon.getRedeemWord().equals(redeemWord)) {
					return false;
				}
			}
		}
		return true;
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

	public boolean logIn(User.Type type, String name, String password) {
		for (User user : dataCenter.getUsers()[type.ordinal()]) {
			if (user != null) {
				if (user.getName().equals(name) && user.getPassword().equals(password)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean logInAdmin(String name, String password) {
		User admin = dataCenter.getUsers()[User.Type.USER.ordinal()][0];
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
					return daysOfWeek;
			}
		}
		return daysOfWeek;
	}

	public RedeemedCoupon redeemCoupon(User user, Coupon coupon) {
		RedeemedCoupon redeemed = new RedeemedCoupon(coupon);
		coupon.getRedeems()[Coupon.RedeemType.CURRENT.ordinal()] += 1;
		checkCouponAvailability(coupon);
		return redeemed;
	}

	public void refreshRouteSeq(RouteSequence routeSeq, LocalDate currentDate) {
		if (routeSeq.getRoutes()[0].getStops()[Route.StopType.ENTRY.ordinal()].getDayOfMonth() != currentDate
				.getDayOfMonth()) {
			for (Route route : routeSeq.getRoutes()) {
				route.getStops()[Route.StopType.ENTRY.ordinal()] = LocalDateTime.of(currentDate,
						route.getStops()[Route.StopType.ENTRY.ordinal()].toLocalTime());
				route.getStops()[Route.StopType.EXIT.ordinal()] = LocalDateTime.of(currentDate,
						route.getStops()[Route.StopType.EXIT.ordinal()].toLocalTime());
			}
		}
		checkRouteSequenceAvailability(routeSeq);
	}

	public Coupon searchCouponByWord(String redeemWord) {
		for (Coupon coupon : dataCenter.getCoupons()) {
			if (coupon != null) {
				if (coupon.getRedeemWord().equals(redeemWord)) {
					return coupon;
				}
			}
		}
		return null;
	}

	public int searchUserArrayNumber(User.Type type, String name) {
		for (int i = 0; i < dataCenter.getUsers()[type.ordinal()].length; i++) {
			if (dataCenter.getUsers()[type.ordinal()][i] != null) {
				if (dataCenter.getUsers()[type.ordinal()][i].getName().equals(name)) {
					return i;
				}
			}
		}
		return -1;
	}

	public void setApplicableCouponObjects(Coupon coupon, Vehicle[] vehicles, RouteSequence[] routeSeqs,
			Route[] routes) {
		if (coupon.getApplicableVehicles() != null) {
			for (Vehicle vehicle : coupon.getApplicableVehicles()) {
				if (vehicle != null)
					deleteObjectFromAList(vehicle.getApplicableCoupons(), coupon);
			}
		}

		if (coupon.getApplicableRouteSeqs() != null) {
			for (RouteSequence routeSeq : coupon.getApplicableRouteSeqs()) {
				if (routeSeq != null)
					deleteObjectFromAList(routeSeq.getApplicableCoupons(), coupon);
			}
		}

		if (coupon.getApplicableRoutes() != null) {
			for (Route route : coupon.getApplicableRoutes()) {
				if (route != null)
					deleteObjectFromAList(route.getApplicableCoupons(), coupon);
			}
		}

		switch (coupon.getApplicable()) {
			case VEHICLES:
				for (Vehicle vehicle : vehicles) {
					vehicle.addCoupon(coupon);
				}
				break;
			case ROUTE_SEQS:
				for (RouteSequence routeSeq : routeSeqs) {
					routeSeq.addCoupon(coupon);
				}
				break;
			case ROUTES:
				for (Route route : routes) {
					route.addCoupon(coupon);
				}
				break;
		}
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
		for (User user : dataCenter.getUsers()[User.Type.USER.ordinal()]) {
			if (user != null) {
				for (Ticket currentTicket : user.getTicketHistory()) {
					if (currentTicket != null) {
						if (currentTicket.equals(ticket)) {
							currentTicket.setAvailable(availability);
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