package com.joycity.joyclub.product.modal;


import com.joycity.joyclub.commons.modal.base.IdNamePortrait;

/**
 * Created by CallMeXYZ on 2017/4/13.
 * 前端 商品详情需要的数据
 */
public class ProductInfoPage extends ProductWithPointRate {
    /**
     * 真实的价格
     */
    private Float price;
    private IdNamePortrait designer;
    private Float basePointRate;
    private IdNamePortrait store;
    private Boolean specialPriceFlag;

    public Float getBasePointRate() {
        return basePointRate;
    }

    public void setBasePointRate(Float basePointRate) {
        this.basePointRate = basePointRate;
    }

    public IdNamePortrait getStore() {
        return store;
    }

    public void setStore(IdNamePortrait store) {
        this.store = store;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public IdNamePortrait getDesigner() {
        return designer;
    }

    public void setDesigner(IdNamePortrait designer) {
        this.designer = designer;
    }

    public Boolean getSpecialPriceFlag() {
        return specialPriceFlag;
    }

    public void setSpecialPriceFlag(Boolean specialPriceFlag) {
        this.specialPriceFlag = specialPriceFlag;
    }
}
