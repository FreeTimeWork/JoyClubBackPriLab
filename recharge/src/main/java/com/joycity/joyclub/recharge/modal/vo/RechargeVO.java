package com.joycity.joyclub.recharge.modal.vo;

import java.math.BigDecimal;

/**
 * @auther fangchen.chai ON 2017/11/21
 */
public class RechargeVO {
    private String tel;
    private Integer point;
    private String type;
    private BigDecimal amount;

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
