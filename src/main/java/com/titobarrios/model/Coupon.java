package com.titobarrios.model;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import com.titobarrios.constants.Value;
import com.titobarrios.db.Archive;
import com.titobarrios.db.CurrentDate;
import com.titobarrios.db.DB;
import com.titobarrios.model.interfaces.Id;
import com.titobarrios.utils.ArraysUtil;

public class Coupon implements Id {
	public static enum AppliesTo {
		VEHICLES, ROUTE_SEQS, ROUTES;
	}

	public static enum DiscountType {
		QUANTITY, PERCENTAGE;
	}

	public static enum RedeemType {
		USER, CURRENT, USER_MAXIMUM, MAXIMUM;
	}

	public static enum Type {
		PUBLIC("Público"), RESERVED("Reservado");

		private String name;

		Type(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	public static final int REDEEMS_X = 4;

	private String id;
	private Company owner;
	private String name;
	private String description;
	private boolean isCumulative;

	private Type type;
	private String redeemWord;
	private DiscountType discountType;
	private int discount;

	private AppliesTo applicable;
	private Vehicle[] vehicles;
	private RouteSequence[] routeSeqs;
	private Route[] routes;

	private int[] redeems;
	private DayOfWeek[] redeemingDays;
	private LocalDateTime[] dates;

	private boolean isAvailable;

	public Coupon(Type type, Company owner, String name, String description, String redeemWord,
			DiscountType discountType, int discount, LocalDateTime[] dates, boolean isCumulative, AppliesTo applicable,
			Vehicle[] vehicles, RouteSequence[] routeSeqs, Route[] routes, DayOfWeek[] redeemingDays,
			int userMaxRedeems, int maxRedeems) {
		this.name = name;
		this.description = description;
		this.isCumulative = isCumulative;
		this.type = type;
		this.redeemWord = redeemWord;
		this.discountType = discountType;
		this.discount = discount;
		this.applicable = applicable;
		this.vehicles = vehicles;
		this.routeSeqs = routeSeqs;
		this.routes = routes;
		this.redeems = new int[REDEEMS_X];
		this.redeems[RedeemType.USER_MAXIMUM.ordinal()] = userMaxRedeems;
		this.redeems[RedeemType.MAXIMUM.ordinal()] = maxRedeems;
		this.redeemingDays = redeemingDays;
		initialize();
	}

	public Coupon(Coupon coupon) {
		this.id = coupon.getId();
		this.type = coupon.getType();
		this.name = coupon.getName();
		this.discountType = coupon.getDiscountType();
		this.discount = coupon.getDiscount();
	}

	private void initialize() {
		generateID();
		checkAvailability();
		DB.store(this);
		owner.add(this);
		addToObjects();
	}

	public void checkAvailability() {
		CurrentDate.refresh();
		if (this.getRedeems()[Coupon.RedeemType.CURRENT.ordinal()] < this.getRedeems()[Coupon.RedeemType.MAXIMUM
				.ordinal()] && (this.getDates()[Value.STARTING.value()].isBefore(CurrentDate.get())
						&& this.getDates()[Value.EXPIRATION.value()].isAfter(CurrentDate.get())))
			this.setAvailable(true);
		this.setAvailable(false);
	}

	private void generateID() {
		this.id = "" + (CurrentDate.get().getYear() * 100000 + Archive.getCoupons().length - 1);
	}

	public void remove(Vehicle vehicle) {
		ArraysUtil.deleteSlot(vehicles, vehicle);
	}

	public void remove(RouteSequence routeSeq) {
		ArraysUtil.deleteSlot(routeSeqs, routeSeq);
	}

	private void addToObjects() {
		switch (applicable) {
			case VEHICLES:
				for (Vehicle vehicle : vehicles)
					vehicle.add(this);
				break;
			case ROUTE_SEQS:
				for (RouteSequence routeSeq : routeSeqs)
					routeSeq.add(this);
				break;
			case ROUTES:
				for (Route route : routes)
					route.add(this);
				break;
		}
	}

	private void removeObjects() {
		switch (applicable) {
			case VEHICLES:
				vehicles = null;
				break;
			case ROUTE_SEQS:
				routeSeqs = null;
				break;
			case ROUTES:
				routes = null;
				break;
		}
	}

	private void removeFromObjects() {
		switch (applicable) {
			case VEHICLES:
				for (Vehicle vehicle : vehicles)
					vehicle.remove(this);
				break;
			case ROUTE_SEQS:
				for (RouteSequence routeSeq : routeSeqs)
					routeSeq.remove(this);
				break;
			case ROUTES:
				for (Route route : routes)
					route.remove(this);
				break;
		}
	}

	@Override
	public String getId() {
		return id;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isCumulative() {
		return isCumulative;
	}

	public void setCumulative(boolean isCumulative) {
		this.isCumulative = isCumulative;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
		if (type.equals(Type.PUBLIC))
			redeemWord = null;
	}

	public String getRedeemWord() {
		return redeemWord;
	}

	public void setRedeemWord(String redeemWord) {
		this.redeemWord = redeemWord;
	}

	public DiscountType getDiscountType() {
		return discountType;
	}

	public void setDiscountType(DiscountType discountType) {
		this.discountType = discountType;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public AppliesTo getApplicable() {
		return applicable;
	}

	public void setApplicable(AppliesTo applicable) {
		removeFromObjects();
		removeObjects();
		this.applicable = applicable;
	}

	public Vehicle[] getVehicles() {
		return vehicles;
	}

	public void setVehicles(Vehicle[] vehicles) {
		this.vehicles = vehicles;
		addToObjects();
	}

	public RouteSequence[] getRouteSeqs() {
		return routeSeqs;
	}

	public void setRouteSeqs(RouteSequence[] routeSeqs) {
		this.routeSeqs = routeSeqs;
		addToObjects();
	}

	public Route[] getRoutes() {
		return routes;
	}

	public void setRoutes(Route[] routes) {
		this.routes = routes;
		addToObjects();
	}

	public int[] getRedeems() {
		return redeems;
	}

	public void setRedeems(int[] redeems) {
		this.redeems = redeems;
	}

	public DayOfWeek[] getRedeemingDays() {
		return redeemingDays;
	}

	public void setRedeemingDays(DayOfWeek[] redeemingDays) {
		this.redeemingDays = redeemingDays;
	}

	public LocalDateTime[] getDates() {
		return dates;
	}

	public void setDates(LocalDateTime[] dates) {
		this.dates = dates;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public void delete() {
		DB.remove(this);
		owner.remove(this);
		removeFromObjects();
	}

	public String info() {
		StringBuilder builder = new StringBuilder();
		builder.append("[").append(id).append("] ").append(name).append("\n ").append(description).append("\n")
				.append(type.getName()).append(" ").append(type == Type.RESERVED ? redeemWord + "   " : "")
				.append(discountType).append("   ").append(discount)
				.append(discountType == DiscountType.PERCENTAGE ? "%" : "").append("\n");
		switch (applicable) {
			case VEHICLES:
				builder.append("Vehículos:");
				for (Vehicle vehicle : vehicles)
					builder.append("\n").append(vehicle.getId());
				break;
			case ROUTE_SEQS:
				builder.append("Secuencias de rutas:");
				for (RouteSequence routeSeq : routeSeqs)
					builder.append("\n").append(routeSeq.getId());
				break;
			case ROUTES:
				builder.append("Rutas:");
				for (Route route : routes)
					builder.append("\n").append(route.getName());
				break;
		}
		builder.append("\nRedenciones: ").append(redeems[RedeemType.CURRENT.ordinal()]).append("/")
				.append(redeems[RedeemType.MAXIMUM.ordinal()]).append("\nExpira: ")
				.append(dates[Value.EXPIRATION.value()]).append("\n");
		return builder.toString();
	}
}
