package com.joycity.joyclub.mallcoo.modal.result.data;

import java.util.Date;

/**
 * CouponInfo
 *
 * @author CallMeXYZ
 * @date 2017/8/8
 */
public class CouponInfo {
    private Long CouponID;
    private String VCode;
    private String Name;
    private String Subtitle;
    private Integer CouponType;
    private Integer UseState;
    private Double ReduceMoney;
    private Double Deductible;
    private Double DiscountAmount;
    private Double InsteadMoney;
    private Double InsteadTime;
    private Double ExchangeInfo;
    private Double YouHuiInfo;
    private Date EnableTime;
    private Date OverdueTime;

    public Long getCouponID() {
        return CouponID;
    }

    public void setCouponID(Long couponID) {
        CouponID = couponID;
    }

    public String getVCode() {
        return VCode;
    }

    public void setVCode(String VCode) {
        this.VCode = VCode;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSubtitle() {
        return Subtitle;
    }

    public void setSubtitle(String subtitle) {
        Subtitle = subtitle;
    }

    public Integer getCouponType() {
        return CouponType;
    }

    public void setCouponType(Integer couponType) {
        CouponType = couponType;
    }

    public Integer getUseState() {
        return UseState;
    }

    public void setUseState(Integer useState) {
        UseState = useState;
    }

    public Double getReduceMoney() {
        return ReduceMoney;
    }

    public void setReduceMoney(Double reduceMoney) {
        ReduceMoney = reduceMoney;
    }

    public Double getDeductible() {
        return Deductible;
    }

    public void setDeductible(Double deductible) {
        Deductible = deductible;
    }

    public Double getDiscountAmount() {
        return DiscountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        DiscountAmount = discountAmount;
    }

    public Double getInsteadMoney() {
        return InsteadMoney;
    }

    public void setInsteadMoney(Double insteadMoney) {
        InsteadMoney = insteadMoney;
    }

    public Double getInsteadTime() {
        return InsteadTime;
    }

    public void setInsteadTime(Double insteadTime) {
        InsteadTime = insteadTime;
    }

    public Double getExchangeInfo() {
        return ExchangeInfo;
    }

    public void setExchangeInfo(Double exchangeInfo) {
        ExchangeInfo = exchangeInfo;
    }

    public Double getYouHuiInfo() {
        return YouHuiInfo;
    }

    public void setYouHuiInfo(Double youHuiInfo) {
        YouHuiInfo = youHuiInfo;
    }

    public Date getEnableTime() {
        return EnableTime;
    }

    public void setEnableTime(Date enableTime) {
        EnableTime = enableTime;
    }

    public Date getOverdueTime() {
        return OverdueTime;
    }

    public void setOverdueTime(Date overdueTime) {
        OverdueTime = overdueTime;
    }
}
