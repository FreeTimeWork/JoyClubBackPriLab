package com.joycity.joyclub.apifront.modal.vo.cart;

import com.joycity.joyclub.apifront.modal.vo.BaseProjectVO;

import javax.validation.constraints.NotNull;

/**
 * Created by CallMeXYZ on 2017/7/20.
 */
public class CartVO extends BaseProjectVO {
    @NotNull
    private Long attrId;
    @NotNull
    private Integer num;

    public Long getAttrId() {
        return attrId;
    }

    public void setAttrId(Long attrId) {
        this.attrId = attrId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
