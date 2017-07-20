package com.joycity.joyclub.apifront.modal.vo.product.order;

import com.joycity.joyclub.product.modal.ProductOrderFormDataItem;

import javax.validation.constraints.NotNull;

/**
 * Created by CallMeXYZ on 2017/7/20.
 */
public class ProductOrderItemVO {
    @NotNull
    private Long attrId;
    @NotNull
    private Integer num;
    @NotNull
    private Boolean moneyOrPoint;

    private ProductOrderFormDataItem info;

    public ProductOrderFormDataItem getInfo() {
        return info;
    }

    public void setInfo(ProductOrderFormDataItem info) {
        this.info = info;
    }

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

    public Boolean getMoneyOrPoint() {
        return moneyOrPoint;
    }

    public void setMoneyOrPoint(Boolean moneyOrPoint) {
        this.moneyOrPoint = moneyOrPoint;
    }
}
