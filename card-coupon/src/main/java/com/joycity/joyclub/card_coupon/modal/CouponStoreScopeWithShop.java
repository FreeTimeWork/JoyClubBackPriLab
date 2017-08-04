package com.joycity.joyclub.card_coupon.modal;

import com.joycity.joyclub.card_coupon.modal.generated.CardCouponStoreScope;

/**
 * Created by fangchen.chai on 2017/7/14.
 */
public class CouponStoreScopeWithShop extends CardCouponStoreScope {
    private String shopName;
    private String subCommercialTypeName;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getSubCommercialTypeName() {
        return subCommercialTypeName;
    }

    public void setSubCommercialTypeName(String subCommercialTypeName) {
        this.subCommercialTypeName = subCommercialTypeName;
    }
}
