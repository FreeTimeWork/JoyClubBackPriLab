package com.joycity.joyclub.card_coupon.modal;

/**
 * Created by fangchen.chai on 2017/7/28
 */
public class PosRefundVerifyResult {
    private Byte refundType;

    public PosRefundVerifyResult() {
    }

    public PosRefundVerifyResult(Byte refundType) {

        this.refundType = refundType;
    }

    public Byte getRefundType() {
        return refundType;
    }

    public void setRefundType(Byte refundType) {
        this.refundType = refundType;
    }
}
