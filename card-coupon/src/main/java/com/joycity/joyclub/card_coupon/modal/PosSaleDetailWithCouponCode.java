package com.joycity.joyclub.card_coupon.modal;

import com.joycity.joyclub.card_coupon.modal.generated.PosSaleDetail;

import java.util.List;

/**
 * Created by fangchen.chai on 2017/7/26
 */
public class PosSaleDetailWithCouponCode extends PosSaleDetail {
    private List<Long> couponCodeIds;

    public List<Long> getCouponCodeIds() {
        return couponCodeIds;
    }

    public void setCouponCodeIds(List<Long> couponCodeIds) {
        this.couponCodeIds = couponCodeIds;
    }
}
