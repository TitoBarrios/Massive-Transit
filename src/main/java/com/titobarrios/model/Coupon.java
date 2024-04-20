package com.titobarrios.model;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Arrays;

import com.titobarrios.constants.Value;
import com.titobarrios.db.Archive;
import com.titobarrios.db.CurrentDate;
import com.titobarrios.db.DB;

public class Coupon {
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

	private int id;
	private Company owner;
	private String name;
	private String description;
	private boolean isCumulative;

	private Type type;
	private String redeemWord;
	private DiscountType discountType;
	private int discount;

	private AppliesTo applicable;
	private Vehicle[] applicableVehicles;
	private RouteSequence[] applicableRouteSeqs;
	private Route[] applicableRoutes;

	private int[] redeems;
	private DayOfWeek[] redeemingDays;
	private LocalDateTime[] dates;

	private boolean isAvailable;

	public Coupon(Type type, int id, Company owner, String name, String description, DiscountType discountType,
			String redeemWord, int discount,
			LocalDateTime[] dates, boolean isCumulative, AppliesTo applicable, Vehicle[] applicableVehicles,
			RouteSequence[] applicableRouteSeqs, Route[] applicableRoutes, DayOfWeek[] redeemingDays,
			int userMaxRedeems,
			int maxRedeems) {
		this.owner = owner;
		this.name = name;
		this.description = description;
		this.isCumulative = isCumulative;
		this.applicable = applicable;
		this.applicableVehicles = applicableVehicles;
		this.applicableRouteSeqs = applicableRouteSeqs;
		this.applicableRoutes = applicableRoutes;
		this.redeems = new int[REDEEMS_X];
		this.redeems[RedeemType.USER_MAXIMUM.ordinal()] = userMaxRedeems;
		this.redeems[RedeemType.MAXIMUM.ordinal()] = maxRedeems;
		this.redeemingDays = redeemingDays;
		DB.store(this);
		owner.add(this);
		this.checkAvailability();
		setApplicableCouponObjects(this);
	}

	public Coupon(Coupon coupon) {
		this.id = coupon.getId();
		this.type = coupon.getType();
		this.name = coupon.getName();
		this.discountType = coupon.getDiscountType();
		this.discount = coupon.getDiscount();
	}

	public void checkAvailability() {
		CurrentDate.refresh();
		if (this.getRedeems()[Coupon.RedeemType.CURRENT.ordinal()] < this.getRedeems()[Coupon.RedeemType.MAXIMUM
				.ordinal()] && (this.getDates()[Value.STARTING.value()].isBefore(CurrentDate.get())
						&& this.getDates()[Value.EXPIRATION.value()].isAfter(CurrentDate.get())))
			this.setAvailable(true);
		this.setAvailable(false);
	}

	public void generateID() {
		this.id = CurrentDate.get().getYear() * 100000 + Archive.getCoupons().length - 1;
	}

	private void initialize() {
		
	}

	public int getId() {
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
		this.applicable = applicable;
	}

	public Vehicle[] getApplicableVehicles() {
		return applicableVehicles;
	}

	public void setApplicableVehicles(Vehicle[] applicableVehicles) {
		this.applicableVehicles = applicableVehicles;
	}

	public RouteSequence[] getApplicableRouteSeqs() {
		return applicableRouteSeqs;
	}

	public void setApplicableRouteSeqs(RouteSequence[] applicableRouteSeqs) {
		this.applicableRouteSeqs = applicableRouteSeqs;
	}

	public Route[] getApplicableRoutes() {
		return applicableRoutes;
	}

	public void setApplicableRoutes(Route[] applicableRoutes) {
		this.applicableRoutes = applicableRoutes;
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

	@Override
	public String toString() {
		return "Coupon [id=" + id + ", owner=" + owner + ", name=" + name + ", description=" + description
				+ ", isCumulative=" + isCumulative + ", type=" + type + ", redeemWord=" + redeemWord + ", discountType="
				+ discountType + ", discount=" + discount + ", applicable=" + applicable + ", applicableVehicles="
				+ Arrays.toString(applicableVehicles) + ", applicableRouteSeqs=" + Arrays.toString(applicableRouteSeqs)
				+ ", applicableRoutes=" + Arrays.toString(applicableRoutes) + ", redeems=" + Arrays.toString(redeems)
				+ ", redeemingDays=" + Arrays.toString(redeemingDays) + ", dates=" + Arrays.toString(dates)
				+ ", isAvailable=" + isAvailable + "]";
	}

}
