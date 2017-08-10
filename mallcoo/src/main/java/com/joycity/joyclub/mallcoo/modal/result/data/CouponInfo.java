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
    private String CouponRuleNo;
    private String Desc;
    private Double ReduceMoney;
    private Double Deductible;
    private Double DiscountAmount;
    private Double InsteadMoney;
    private Double InsteadTime;
    private List<Integer> PartTimeList;
    private Date UseTime;
    private Integer Verification;
    private String ConsumerHotline;
    private Boolean IsPwdForVerify;
    private Integer ShowType;
    private String OperationTips;
    private String UseDesc;
    private List<JoinShop> JoinShopList;

    public String getCouponRuleNo() {
        return CouponRuleNo;
    }

    public void setCouponRuleNo(String couponRuleNo) {
        CouponRuleNo = couponRuleNo;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    @Override
    public Double getReduceMoney() {
        return ReduceMoney;
    }

    @Override
    public void setReduceMoney(Double reduceMoney) {
        ReduceMoney = reduceMoney;
    }

    @Override
    public Double getDeductible() {
        return Deductible;
    }

    @Override
    public void setDeductible(Double deductible) {
        Deductible = deductible;
    }

    @Override
    public Double getDiscountAmount() {
        return DiscountAmount;
    }

    @Override
    public void setDiscountAmount(Double discountAmount) {
        DiscountAmount = discountAmount;
    }

    @Override
    public Double getInsteadMoney() {
        return InsteadMoney;
    }

    @Override
    public void setInsteadMoney(Double insteadMoney) {
        InsteadMoney = insteadMoney;
    }

    @Override
    public Double getInsteadTime() {
        return InsteadTime;
    }

    @Override
    public void setInsteadTime(Double insteadTime) {
        InsteadTime = insteadTime;
    }

    public List<Integer> getPartTimeList() {
        return PartTimeList;
    }

    public void setPartTimeList(List<Integer> partTimeList) {
        PartTimeList = partTimeList;
    }

    public Date getUseTime() {
        return UseTime;
    }

    public void setUseTime(Date useTime) {
        UseTime = useTime;
    }

    public Integer getVerification() {
        return Verification;
    }

    public void setVerification(Integer verification) {
        Verification = verification;
    }

    public String getConsumerHotline() {
        return ConsumerHotline;
    }

    public void setConsumerHotline(String consumerHotline) {
        ConsumerHotline = consumerHotline;
    }

    public Boolean getPwdForVerify() {
        return IsPwdForVerify;
    }

    public void setPwdForVerify(Boolean pwdForVerify) {
        IsPwdForVerify = pwdForVerify;
    }

    public Integer getShowType() {
        return ShowType;
    }

    public void setShowType(Integer showType) {
        ShowType = showType;
    }

    public String getOperationTips() {
        return OperationTips;
    }

    public void setOperationTips(String operationTips) {
        OperationTips = operationTips;
    }

    public String getUseDesc() {
        return UseDesc;
    }

    public void setUseDesc(String useDesc) {
        UseDesc = useDesc;
    }

    public List<JoinShop> getJoinShopList() {
        return JoinShopList;
    }

    public void setJoinShopList(List<JoinShop> joinShopList) {
        JoinShopList = joinShopList;
    }
}
