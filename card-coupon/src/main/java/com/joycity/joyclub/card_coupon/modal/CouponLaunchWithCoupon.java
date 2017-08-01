package com.joycity.joyclub.card_coupon.modal;

import com.joycity.joyclub.card_coupon.modal.generated.CardCouponLaunch;

/**
 * Created by fangchen.chai on 2017/7/12.
 */
public class CouponLaunchWithCoupon extends CardCouponLaunch {

    private String couponName;
    private Long projectId;
    private Byte couponType;
    private String batch;
    private String thirdpartyShopId;
    private Boolean sysgenFlag;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Boolean getSysgenFlag() {
        return sysgenFlag;
    }

    public void setSysgenFlag(Boolean sysgenFlag) {
        this.sysgenFlag = sysgenFlag;
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

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getThirdpartyShopId() {
        return thirdpartyShopId;
    }

    public void setThirdpartyShopId(String thirdpartyShopId) {
        this.thirdpartyShopId = thirdpartyShopId;
    }
}
