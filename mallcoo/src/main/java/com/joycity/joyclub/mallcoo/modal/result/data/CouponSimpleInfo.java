package com.joycity.joyclub.mallcoo.modal.result.data;

import java.util.Date;

/**
 * CouponInfo
 *
 * @author CallMeXYZ
 * @date 2017/8/8
 */
public class CouponSimpleInfo {
    /* 注意应该是券号id 而不是卡券id */
    private Long couponId;
    private String vCode;
    private String name;
    private String subtitle;
    private Integer couponType;
    private Integer useState;
    private Double reduceMoney;
    private Double deductible;
    private Double discountAmount;
    private Double insteadMoney;
    private Double insteadTime;
    private String exchangeInfo;
    private String youHuiInfo;
    private Date enableTime;
    private Date overdueTime;
    private String codeFrom;

    public String getCodeFrom() {
        return codeFrom;
    }

    public void setCodeFrom(String codeFrom) {
        this.codeFrom = codeFrom;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public String getvCode() {
        return vCode;
    }

    public void setvCode(String vCode) {
        this.vCode = vCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public Integer getCouponType() {
        return couponType;
    }

    public void setCouponType(Integer couponType) {
        this.couponType = couponType;
    }

    public Integer getUseState() {
        return useState;
    }

    public void setUseState(Integer useState) {
        this.useState = useState;
    }

    public Double getReduceMoney() {
        return reduceMoney;
    }

    public void setReduceMoney(Double reduceMoney) {
        this.reduceMoney = reduceMoney;
    }

    public Double getDeductible() {
        return deductible;
    }

    public void setDeductible(Double deductible) {
        this.deductible = deductible;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Double getInsteadMoney() {
        return insteadMoney;
    }

    public void setInsteadMoney(Double insteadMoney) {
        this.insteadMoney = insteadMoney;
    }

    public Double getInsteadTime() {
        return insteadTime;
    }

    public void setInsteadTime(Double insteadTime) {
        this.insteadTime = insteadTime;
    }

    public String getExchangeInfo() {
        return exchangeInfo;
    }

    public void setExchangeInfo(String exchangeInfo) {
        this.exchangeInfo = exchangeInfo;
    }

    public String getYouHuiInfo() {
        return youHuiInfo;
    }

    public void setYouHuiInfo(String youHuiInfo) {
        this.youHuiInfo = youHuiInfo;
    }

    public Date getEnableTime() {
        return enableTime;
    }

    public void setEnableTime(Date enableTime) {
        this.enableTime = enableTime;
    }

    public Date getOverdueTime() {
        return overdueTime;
    }

    public void setOverdueTime(Date overdueTime) {
        this.overdueTime = overdueTime;
    }
}
