package model;

import java.util.Arrays;

public abstract class Vehicle {

	public static enum Type {
		AIRPLANE("avión", "aviones", "Avión"), BUS("bus", "buses", "Bus"), SHIP("barco", "barcos", "Barco"),
		TRAVEL_BUS("bus de viaje", "buses de viaje", "Bus de Viaje");

		private Type(String name, String pluralName, String upperCaseName) {
			this.name = name;
			this.pluralName = pluralName;
			this.upperCaseName = upperCaseName;
		}

		private String name;
		private String pluralName;
		private String upperCaseName;

		public String getName() {
			return name;
		}

		public String getPluralName() {
			return pluralName;
		}

		public String getUpperCaseName() {
			return upperCaseName;
		}
	}

	private final static int REVENUE_Y = 2;
	private final static int STATISTICS_TYPES = 4;
	private final static int MAX_COUPONS = 20;
	private final static int MAX_TICKETS = 200;
	private final static int MAX_CAPACITY_X = 2;

	private Type type;
	private int[][] revenue;
	private Ticket[] tickets;
	private Coupon[] applicableCoupons;
	private int[] capacity;
	private Company company;
	private RouteSequence routeSeq;
	private String plate;
	private int price;
	private boolean isAvailable;

	public Vehicle(Type type, Company company, String plate, RouteSequence routeSeq, int price, int capacity) {
		this.type = type;
		revenue = new int[STATISTICS_TYPES][REVENUE_Y];
		tickets = new Ticket[MAX_TICKETS];
		applicableCoupons = new Coupon[MAX_COUPONS];
		this.capacity = new int[MAX_CAPACITY_X];
		this.capacity[Value.MAXIMUM.getValue()] = capacity;
		this.company = company;
		this.routeSeq = routeSeq;
		this.plate = plate;
		this.price = price;
	}

	public void addTicket(Ticket ticket) {
		for (int i = 0; i < tickets.length; i++) {
			if (tickets[i] == null) {
				tickets[i] = ticket;
				break;
			}
		}
	}

	public void addCoupon(Coupon coupon) {
		for(int i = 0; i < applicableCoupons.length; i++){
			if(applicableCoupons[i] == null){
				applicableCoupons[i] = coupon;
				break;
			}
		}
	}

	public void deleteTicket(int ticketPosition) {
		tickets[ticketPosition] = null;
	}

	public void changeCurrentCapacity(int currentCapacity) {
		this.capacity[Value.CURRENT.getValue()] = currentCapacity;
	}

	public Type getType() {
		return type;
	}

	public int[][] getRevenue() {
		return revenue;
	}

	public void setRevenue(int[][] revenue) {
		this.revenue = revenue;
	}

	public Ticket[] getTickets() {
		return tickets;
	}

	public void setTickets(Ticket[] tickets) {
		this.tickets = tickets;
	}

	public Coupon[] getApplicableCoupons() {
		return applicableCoupons;
	}

	public void setApplicableCoupons(Coupon[] applicableCoupons) {
		this.applicableCoupons = applicableCoupons;
	}

	public int[] getCapacity() {
		return capacity;
	}

	public void setCapacity(int[] capacity) {
		this.capacity = capacity;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public RouteSequence getRouteSeq() {
		return routeSeq;
	}

	public void setRouteSequence(RouteSequence routeSeq) {
		this.routeSeq = routeSeq;
	}

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public boolean getIsAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	@Override
	public String toString() {
		return "Vehicle [type=" + type + ", revenue=" + Arrays.toString(revenue) + ", tickets="
				+ Arrays.toString(tickets) + ", applicableCoupons=" + Arrays.toString(applicableCoupons) + ", capacity="
				+ Arrays.toString(capacity) + ", company=" + company + ", routeSeq=" + routeSeq + ", plate=" + plate
				+ ", price=" + price + ", isAvailable=" + isAvailable + "]";
	}
}
