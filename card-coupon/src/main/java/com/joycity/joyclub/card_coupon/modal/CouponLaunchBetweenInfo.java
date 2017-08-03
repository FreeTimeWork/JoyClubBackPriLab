package com.joycity.joyclub.card_coupon.modal;

import java.math.BigDecimal;

/**
 * Created by fangchen.chai on 2017/7/25
 */
public class CouponLaunchBetweenInfo {

    private Long launchId;
    private Integer maxReceive;
    private Integer notUsedNum;     //未使用数量
    private Integer usedNum;        //已使用数量
    private BigDecimal sumPaid;     //付款总额
    private BigDecimal conditionAmount; // 触发条件投放的总额
    private BigDecimal subtractAmount;   //卡券折扣金额
    private PosSaleDetailWithCouponCode detail;
    private Byte refundType;

    public Long getLaunchId() {
        return launchId;
    }

    public void setLaunchId(Long launchId) {
        this.launchId = launchId;
    }

    public Integer getMaxReceive() {
        return maxReceive;
    }

    public void setMaxReceive(Integer maxReceive) {
        this.maxReceive = maxReceive;
    }

    public Integer getNotUsedNum() {
        return notUsedNum;
    }

    public void setNotUsedNum(Integer notUsedNum) {
        this.notUsedNum = notUsedNum;
    }

    public Integer getUsedNum() {
        return usedNum;
    }

    public void setUsedNum(Integer usedNum) {
        this.usedNum = usedNum;
    }

    public BigDecimal getSumPaid() {
        return sumPaid;
    }

    public void setSumPaid(BigDecimal sumPaid) {
        this.sumPaid = sumPaid;
    }

    public BigDecimal getConditionAmount() {
        return conditionAmount;
    }

    public void setConditionAmount(BigDecimal conditionAmount) {
        this.conditionAmount = conditionAmount;
    }

    public BigDecimal getSubtractAmount() {
        return subtractAmount;
    }

    public void setSubtractAmount(BigDecimal subtractAmount) {
        this.subtractAmount = subtractAmount;
    }

    public PosSaleDetailWithCouponCode getDetail() {
        return detail;
    }

    public void setDetail(PosSaleDetailWithCouponCode detail) {
        this.detail = detail;
    }

    public Byte getRefundType() {
        return refundType;
    }

    public void setRefundType(Byte refundType) {
        this.refundType = refundType;
    }
}
