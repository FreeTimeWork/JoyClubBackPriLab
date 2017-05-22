package com.joycity.joyclub.act.modal;

import com.joycity.joyclub.act.modal.generated.SaleActPrice;

/**
 * Created by CallMeXYZ on 2017/5/18.
 */
public class ActPriceWithActInfo extends SaleActPrice {
    private String storeName;
    private String actName;
    private Float basePrice;

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public Float getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Float basePrice) {
        this.basePrice = basePrice;
    }
}
