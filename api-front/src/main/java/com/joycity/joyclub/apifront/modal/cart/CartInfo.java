package com.joycity.joyclub.apifront.modal.cart;

import java.util.Date;

/**
 * Created by Administrator on 2017/4/18.
 */
public class CartInfo extends Cart {
    private Integer maxNum;
    private String portrait;
    private Float pointRate;
    private Float price;
    private Float basePointRate;
    private Float basePrice;
    private String attrName;
    private Long storeId;
    private String storeName;
    private Long productId;
    private String productName;
    private Boolean specialPriceFlag;
    private Date priceStartTime;
    private Date priceEndtTime;

    public Integer getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(Integer maxNum) {
        this.maxNum = maxNum;
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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getBasePointRate() {
        return basePointRate;
    }

    public void setBasePointRate(Float basePointRate) {
        this.basePointRate = basePointRate;
    }

    public Float getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Float basePrice) {
        this.basePrice = basePrice;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Boolean getSpecialPriceFlag() {
        return specialPriceFlag;
    }

    public void setSpecialPriceFlag(Boolean specialPriceFlag) {
        this.specialPriceFlag = specialPriceFlag;
    }

    public Date getPriceStartTime() {
        return priceStartTime;
    }

    public void setPriceStartTime(Date priceStartTime) {
        this.priceStartTime = priceStartTime;
    }

    public Date getPriceEndtTime() {
        return priceEndtTime;
    }

    public void setPriceEndtTime(Date priceEndtTime) {
        this.priceEndtTime = priceEndtTime;
    }
}
