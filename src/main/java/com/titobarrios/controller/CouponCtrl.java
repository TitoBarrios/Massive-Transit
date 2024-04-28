package com.titobarrios.controller;

import java.util.ArrayList;

import com.titobarrios.db.DB;
import com.titobarrios.model.Coupon;
import com.titobarrios.model.Route;
import com.titobarrios.model.RouteSequence;
import com.titobarrios.model.Vehicle;
import com.titobarrios.utils.ArraysUtil;
import com.titobarrios.utils.Maths;

public class CouponCtrl {
    private Coupon[] coupons;
    private Coupon coupon;

    public CouponCtrl(Coupon[] coupons, Coupon coupon) {
        this.coupons = coupons;
        this.coupon = coupon;
    }

    public CouponCtrl(Coupon[] coupons) {
        this.coupons = coupons;
    }

    public CouponCtrl(Coupon coupon) {
        this.coupon = coupon;
    }
}
