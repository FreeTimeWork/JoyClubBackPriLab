package com.joycity.joyclub.mallcoo.modal.result.data;

import java.util.Date;
import java.util.List;

/**
 * CouponInfo
 *
 * @author CallMeXYZ
 * @date 2017/8/10
 */
public class CouponInfo extends CouponSimpleInfo {
    private String couponRuleNo;
    private String desc;
    private Double reduceMoney;
    private Double deductible;
    private Double discountAmount;
    private Double insteadMoney;
    private Double insteadTime;
    private List<Integer> partTimeList;
    private Date useTime;
    private Integer verification;
    private String consumerHotline;
    private Boolean isPwdForVerify;
    private Integer showType;
    private String operationTips;
    private String useDesc;
    private List<JoinShop> joinShopList;

    public String getCouponRuleNo() {
        return couponRuleNo;
    }

    public void setCouponRuleNo(String couponRuleNo) {
        this.couponRuleNo = couponRuleNo;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public Double getReduceMoney() {
        return reduceMoney;
    }

    @Override
    public void setReduceMoney(Double reduceMoney) {
        this.reduceMoney = reduceMoney;
    }

    @Override
    public Double getDeductible() {
        return deductible;
    }

    @Override
    public void setDeductible(Double deductible) {
        this.deductible = deductible;
    }

    @Override
    public Double getDiscountAmount() {
        return discountAmount;
    }

    @Override
    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    @Override
    public Double getInsteadMoney() {
        return insteadMoney;
    }

    @Override
    public void setInsteadMoney(Double insteadMoney) {
        this.insteadMoney = insteadMoney;
    }

    @Override
    public Double getInsteadTime() {
        return insteadTime;
    }

    @Override
    public void setInsteadTime(Double insteadTime) {
        this.insteadTime = insteadTime;
    }

    public List<Integer> getPartTimeList() {
        return partTimeList;
    }

    public void setPartTimeList(List<Integer> partTimeList) {
        this.partTimeList = partTimeList;
    }

    public Date getUseTime() {
        return useTime;
    }

    public void setUseTime(Date useTime) {
        this.useTime = useTime;
    }

    public Integer getVerification() {
        return verification;
    }

    public void setVerification(Integer verification) {
        this.verification = verification;
    }

    public String getConsumerHotline() {
        return consumerHotline;
    }

    public void setConsumerHotline(String consumerHotline) {
        this.consumerHotline = consumerHotline;
    }

    public Boolean getPwdForVerify() {
        return isPwdForVerify;
    }

    public void setPwdForVerify(Boolean pwdForVerify) {
        isPwdForVerify = pwdForVerify;
    }

    public Integer getShowType() {
        return showType;
    }

    public void setShowType(Integer showType) {
        this.showType = showType;
    }

    public String getOperationTips() {
        return operationTips;
    }

    public void setOperationTips(String operationTips) {
        this.operationTips = operationTips;
    }

    public String getUseDesc() {
        return useDesc;
    }

    public void setUseDesc(String useDesc) {
        this.useDesc = useDesc;
    }

    public List<JoinShop> getJoinShopList() {
        return joinShopList;
    }

    public void setJoinShopList(List<JoinShop> joinShopList) {
        this.joinShopList = joinShopList;
    }
}
