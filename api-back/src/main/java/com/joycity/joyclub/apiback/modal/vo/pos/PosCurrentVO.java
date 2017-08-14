package com.joycity.joyclub.apiback.modal.vo.pos;

import javax.validation.constraints.NotNull;

/**
 * Created by fangchen.chai on 2017/8/14
 */
public class PosCurrentVO {

    @NotNull
    private String vipCode;
    @NotNull
    private String shopCode;

    public String getVipCode() {
        return vipCode;
    }

    public void setVipCode(String vipCode) {
        this.vipCode = vipCode;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }
}
