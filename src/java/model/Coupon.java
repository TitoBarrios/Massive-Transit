package model;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Arrays;

public class Coupon extends RedeemedCoupon {
	public static enum AppliesTo {
		VEHICLES, ROUTE_SEQS, ROUTES;
	}

	public static enum RedeemType {
		USER, CURRENT, USER_MAXIMUM, MAXIMUM;
	}

	public static final int REDEEMS_X = 4;

	Company owner;
	String name;
	String description;
	private boolean isCumulative;

	private AppliesTo applicable;
	private Vehicle[] applicableVehicles;
	private RouteSequence[] applicableRouteSeqs;
	private Route[] applicableRoutes;

	private int[] redeems;
	private DayOfWeek[] redeemingDays;

	private boolean isAvailable;

	public Coupon(Type type, int id, Company owner, String name, String description, DiscountType discountType, String redeemWord, int discount,
			LocalDateTime[] dates, boolean isCumulative, AppliesTo applicable, Vehicle[] applicableVehicles,
			RouteSequence[] applicableRouteSeqs, Route[] applicableRoutes, DayOfWeek[] redeemingDays, int userMaxRedeems,
			int maxRedeems) {
		super(type, id, discountType, redeemWord, discount, dates);
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

	public DayOfWeek[] getRedeemingDays() {
		return redeemingDays;
	}

	public void setRedeemingDays(DayOfWeek[] redeemingDays) {
		this.redeemingDays = redeemingDays;
	}

	public int[] getRedeems() {
		return redeems;
	}

	public void setRedeems(int[] redeems) {
		this.redeems = redeems;
	}

	public boolean getIsAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	@Override
	public String toString() {
		return "Coupon [owner=" + owner + ", name=" + name + ", description=" + description + ", isCumulative="
				+ isCumulative + ", applicable=" + applicable + ", applicableVehicles="
				+ Arrays.toString(applicableVehicles) + ", applicableRouteSeqs=" + Arrays.toString(applicableRouteSeqs)
				+ ", applicableRoutes=" + Arrays.toString(applicableRoutes) + ", redeems=" + Arrays.toString(redeems)
				+ ", redeemingDays=" + Arrays.toString(redeemingDays) + ", isAvailable=" + isAvailable + "]";
	}

}
