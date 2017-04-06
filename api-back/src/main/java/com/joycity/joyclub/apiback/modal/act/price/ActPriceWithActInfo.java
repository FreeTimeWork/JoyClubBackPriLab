package com.joycity.joyclub.apiback.modal.act.price;

import com.joycity.joyclub.apiback.modal.generated.SaleActPrice;

/**
 * Created by CallMeXYZ on 2017/3/30.
 */
public class ActPriceWithActInfo extends SaleActPrice {
    private String storeName;
    private String actName;
    private String basePrice;

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

    public String getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(String basePrice) {
        this.basePrice = basePrice;
    }
}
