package com.joycity.joyclub.apifront.modal.order;

/**
 * Created by Administrator on 2017/4/20.
 */
public class FormDataWithInfo {
    //下面三个属性是
    Long attrId;
    Integer num;
    Boolean moneyOrPoint;

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
