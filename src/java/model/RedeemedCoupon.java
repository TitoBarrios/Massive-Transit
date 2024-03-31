package model;

import java.time.LocalDateTime;
import java.util.Arrays;

public class RedeemedCoupon {

    // Con "PUBLIC" no hay necesidad de escribir una cadena de texto para aplicar el
    // descuento del cupón (Aplica el descuento a todos los usuarios
    // automáticamente,
    // en reserved si es necesario
    public static enum Type {
        PUBLIC("Público"), RESERVED("Reservado");

        private String name;

        Type(String name){
            this.name = name;
        }

        public String getName(){
            return name;
        }
    }

    public static enum DiscountType {
        QUANTITY, PERCENTAGE;
    }

    private Type type;
    private int id;
    private DiscountType discountType;

    private String redeemWord;
    private int discount;

    private LocalDateTime[] dates;

    public RedeemedCoupon(Coupon coupon) {
        this.type = coupon.getType();
        this.id = coupon.getId();
        this.discountType = coupon.getDiscountType();
        this.redeemWord = coupon.getRedeemWord();
        this.discount = coupon.getDiscount();
        this.dates = coupon.getDates();
    }

    public RedeemedCoupon(Type type, int id, DiscountType discountType, String redeemWord, int discount,
            LocalDateTime[] dates) {
        this.type = type;
        this.id = id;
        this.discountType = discountType;
        this.redeemWord = redeemWord;
        this.discount = discount;
        this.dates = dates;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType type) {
        this.discountType = type;
    }

    public String getRedeemWord() {
        return redeemWord;
    }

    public void setRedeemWord(String redeemWord) {
        this.redeemWord = redeemWord;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public LocalDateTime[] getDates() {
        return dates;
    }

    public void setDates(LocalDateTime[] dates) {
        this.dates = dates;
    }

    @Override
    public String toString() {
        return "RedeemedCoupon [type=" + type + ", id=" + id + ", discountType=" + discountType
                + ", redeemWord=" + redeemWord + ", discount=" + discount + ", dates=" + Arrays.toString(dates) + "]";
    }
}
