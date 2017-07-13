package com.joycity.joyclub.card_coupon.modal;

import java.util.Date;

import com.joycity.joyclub.card_coupon.constant.CouponLaunchStatus;
import com.joycity.joyclub.card_coupon.modal.generated.CardCouponLaunch;

/**
 * Created by fangchen.chai on 2017/7/12.
 */
public class ShowCouponLaunchInfo extends CardCouponLaunch {

    private String couponName;
    private Byte couponType;
    private Integer status;

    public static void setStatus(ShowCouponLaunchInfo info, Date now) {
        if (info.getReviewStatus().intValue() == 0) {
            info.setStatus(CouponLaunchStatus.NOT_REVIEW);
        } else if (info.getReviewStatus().intValue() == 1 && !info.getConfirmFlag()) {
            info.setStatus(CouponLaunchStatus.NOT_LAUNCH);
        } else if (info.getReviewStatus().intValue() == 1 && info.getConfirmFlag()
                && now.before(info.getLaunchStartTime())) {
            info.setStatus(CouponLaunchStatus.NOT_START);
        } else if (info.getReviewStatus().intValue() == 1 && info.getConfirmFlag()
                && !info.getForbidFlag() && now.before(info.getLaunchEndTime())
                && now.after(info.getLaunchStartTime())) {
            info.setStatus(CouponLaunchStatus.LAUNCHING);
        } else if (info.getReviewStatus().intValue() == 1 && info.getConfirmFlag()
                && !info.getForbidFlag() && now.after(info.getLaunchEndTime())) {
            info.setStatus(CouponLaunchStatus.LAUNCHED);
        } else if (info.getForbidFlag()) {
            info.setStatus(CouponLaunchStatus.STOP_LAUNCH);
        }
    }

    public static void main(String[] args) {
        System.out.println(new Byte("0").intValue() == 0);
    }
    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public Byte getCouponType() {
        return couponType;
    }

    public void setCouponType(Byte couponType) {
        this.couponType = couponType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
