package com.joycity.joyclub.card_coupon.constant;

/**
 * @auther fangchen.chai ON 2018/3/12
 */
public enum CouponCalculateType {
    INCREMENT_SINGLE(1,"单笔累加"),
    NONINCREMENT_SINGLE(2,"单笔不累加"),
    INCREMENT_MANY(3,"多笔累加"),
    ;

    private Integer id;
    private String name;

    CouponCalculateType() {
    }

    CouponCalculateType(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
