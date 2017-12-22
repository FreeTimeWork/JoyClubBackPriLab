package com.joycity.joyclub.apiback.modal.vo.card_coupon;

/**
 * Created by fangchen.chai on 2017/7/20.
 */
public class updateCardCouponVO {
    private String name;
    private String info;
    private Integer displayWeight;
    private String portrait;
    private String targetAddress;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getDisplayWeight() {
        return displayWeight;
    }

    public void setDisplayWeight(Integer displayWeight) {
        this.displayWeight = displayWeight;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getTargetAddress() {
        return targetAddress;
    }

    public void setTargetAddress(String targetAddress) {
        this.targetAddress = targetAddress;
    }
}
