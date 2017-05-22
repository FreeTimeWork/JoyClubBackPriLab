package com.joycity.joyclub.act.modal;

/**
 * 活动订单需要的商品属性信息
 * price需要二次sql查询
 * Created by CallMeXYZ on 2017/5/19.
 */
public class ActInfoForOrder {
    private Long storeId;
    private Long actId;
    private Long attrId;
private String portrait;
    private String actName;
    private String attrName;
    private Integer num;
    private String categoryName;
    private Float basePrice;
    private Float basePointRate;

    //需要从price领取
    private Float price;
    private Float pointRate;
private Byte buyType;

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getActId() {
        return actId;
    }

    public void setActId(Long actId) {
        this.actId = actId;
    }

    public Long getAttrId() {
        return attrId;
    }

    public void setAttrId(Long attrId) {
        this.attrId = attrId;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }




    public Float getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Float basePrice) {
        this.basePrice = basePrice;
    }

    public Float getBasePointRate() {
        return basePointRate;
    }

    public void setBasePointRate(Float basePointRate) {
        this.basePointRate = basePointRate;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }



    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public Float getPointRate() {
        return pointRate;
    }

    public void setPointRate(Float pointRate) {
        this.pointRate = pointRate;
    }

    public Byte getBuyType() {
        return buyType;
    }

    public void setBuyType(Byte buyType) {
        this.buyType = buyType;
    }
}
