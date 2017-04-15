package com.joycity.joyclub.coupon.exception;

/**
 * Created by CallMeXYZ on 2017/4/12.
 */
public class CouponException extends RuntimeException {
    private String message;

    public CouponException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
