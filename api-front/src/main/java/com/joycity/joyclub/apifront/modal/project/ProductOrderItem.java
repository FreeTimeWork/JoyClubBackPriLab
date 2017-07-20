package com.joycity.joyclub.apifront.modal.project;

import com.joycity.joyclub.apifront.modal.vo.product.order.ProductOrderItemVO;
import com.joycity.joyclub.product.modal.ProductOrderFormDataItem;

/**
 * Created by Administrator on 2017/4/20.
 */
public class ProductOrderItem {
    private Long attrId;
    private Integer num;
    private Boolean moneyOrPoint;

    private ProductOrderFormDataItem info;

    public ProductOrderItem(ProductOrderItemVO vo) {
        this.attrId = vo.getAttrId();
        this.num = vo.getNum();
        this.moneyOrPoint = vo.getMoneyOrPoint();
    }

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
