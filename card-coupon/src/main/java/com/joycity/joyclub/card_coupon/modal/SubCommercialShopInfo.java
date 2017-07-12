package com.joycity.joyclub.card_coupon.modal;

import java.util.List;

import com.joycity.joyclub.card_coupon.modal.generated.SysShop;

/**
 * Created by fangchen.chai on 2017/7/12.
 */
public class SubCommercialShopInfo {

    private String subCommercialTypeName;
    private List<SysShop> sysShops;

    public String getSubCommercialTypeName() {
        return subCommercialTypeName;
    }

    public void setSubCommercialTypeName(String subCommercialTypeName) {
        this.subCommercialTypeName = subCommercialTypeName;
    }

    public List<SysShop> getSysShops() {
        return sysShops;
    }

    public void setSysShops(List<SysShop> sysShops) {
        this.sysShops = sysShops;
    }
}
