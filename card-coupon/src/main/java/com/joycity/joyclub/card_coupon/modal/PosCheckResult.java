package com.joycity.joyclub.card_coupon.modal;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by fangchen.chai on 2017/7/24
 *  pos核销结果
 */
public class PosCheckResult {

    /**
     * 优惠后应付款
     */
    private BigDecimal payment;
    private Boolean checkResult;
    private String resultInfo;

    public PosCheckResult() {
    }

    public PosCheckResult(BigDecimal payment, Boolean checkResult, String resultInfo) {
        this.payment = payment;
        this.checkResult = checkResult;
        this.resultInfo = resultInfo;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public Boolean getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(Boolean checkResult) {
        this.checkResult = checkResult;
    }

    public String getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(String resultInfo) {
        this.resultInfo = resultInfo;
    }
}
