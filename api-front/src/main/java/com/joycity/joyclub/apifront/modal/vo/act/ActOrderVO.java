package com.joycity.joyclub.apifront.modal.vo.act;

import com.joycity.joyclub.apifront.modal.vo.BaseSubProjectVO;

import javax.validation.constraints.NotNull;

/**
 * Created by CallMeXYZ on 2017/7/20.
 */
public class ActOrderVO extends BaseSubProjectVO {
    @NotNull
    private Long attrId;
    @NotNull
    private Boolean moneyOrPoint;

    public Long getAttrId() {
        return attrId;
    }

    public void setAttrId(Long attrId) {
        this.attrId = attrId;
    }

    public Boolean getMoneyOrPoint() {
        return moneyOrPoint;
    }

    public void setMoneyOrPoint(Boolean moneyOrPoint) {
        this.moneyOrPoint = moneyOrPoint;
    }
}
