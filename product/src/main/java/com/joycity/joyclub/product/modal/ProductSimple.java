package com.joycity.joyclub.product.modal;

import java.util.Date;

/**
 * Created by CallMeXYZ on 2017/4/14.
 */
public class ProductSimple {
    private Long id;
    private String name;
    private String portrait;
    private Float price;
    private Float basePrice;
    private Float pointRate;
    /**
     * 特价标志
     */
    private Boolean specialPriceFlag;
    /**
     * 基于分类的积分比例
     */
    private Float basePointRate;

    private Date priceStartTime;
    private Date priceEndTime;
    private String designerPortrait;
    private String designerName;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Float basePrice) {
        this.basePrice = basePrice;
    }

    public Float getPointRate() {
        return pointRate;
    }

    public void setPointRate(Float pointRate) {
        this.pointRate = pointRate;
    }

    public Float getBasePointRate() {
        return basePointRate;
    }

    public void setBasePointRate(Float basePointRate) {

        this.basePointRate = basePointRate;
    }

    public Boolean getSpecialPriceFlag() {
        return specialPriceFlag;
    }

    public void setSpecialPriceFlag(Boolean specialPriceFlag) {

        this.specialPriceFlag = specialPriceFlag;
    }

    public String getDesignerPortrait() {
        return designerPortrait;
    }

    public void setDesignerPortrait(String designerPortrait) {
        this.designerPortrait = designerPortrait;
    }

    public String getDesignerName() {
        return designerName;
    }

    public void setDesignerName(String designerName) {
        this.designerName = designerName;
    }

    public Date getPriceStartTime() {
        return priceStartTime;
    }

    public void setPriceStartTime(Date priceStartTime) {
        this.priceStartTime = priceStartTime;
    }

    public Date getPriceEndTime() {
        return priceEndTime;
    }

    public void setPriceEndTime(Date priceEndTime) {
        this.priceEndTime = priceEndTime;
    }
}
