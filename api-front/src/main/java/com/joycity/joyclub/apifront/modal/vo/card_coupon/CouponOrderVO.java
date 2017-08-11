package com.joycity.joyclub.apifront.modal.vo.card_coupon;

/**
 * Created by fangchen.chai on 2017/8/2
 */
public class CouponOrderVO {
    private Long launchId;
    private Boolean moneyOrPoint;

    public Long getLaunchId() {
        return launchId;
    }

    public void setLaunchId(Long launchId) {
        this.launchId = launchId;
    }

    public Boolean getMoneyOrPoint() {
        return moneyOrPoint;
    }

    public void setMoneyOrPoint(Boolean moneyOrPoint) {
        this.moneyOrPoint = moneyOrPoint;
    }
}
