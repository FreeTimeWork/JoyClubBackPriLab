package com.joycity.joyclub.card_coupon.modal.filter;

/**
 * Created by fangchen.chai on 2017/7/14.
 */
public class ShowCouponCodeFilter {
    private String couponName;
    private String thirdPartyName;
    private Long thirdPartyId;
    private Byte couponType;
    private String couponLaunchName;
    private String code;
    private String tel;

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public Byte getCouponType() {
        return couponType;
    }

    public Long getThirdPartyId() {
        return thirdPartyId;
    }

    public void setThirdPartyId(Long thirdPartyId) {
        this.thirdPartyId = thirdPartyId;
    }

    public String getThirdPartyName() {
        return thirdPartyName;
    }

    public void setThirdPartyName(String thirdPartyName) {
        this.thirdPartyName = thirdPartyName;
    }

    public void setCouponType(Byte couponType) {
        this.couponType = couponType;
    }

    public String getCouponLaunchName() {
        return couponLaunchName;
    }

    public void setCouponLaunchName(String couponLaunchName) {
        this.couponLaunchName = couponLaunchName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
