package com.joycity.joyclub.act.modal;


import com.joycity.joyclub.act.modal.generated.SaleActWithBLOBs;

/**
 * Created by CallMeXYZ on 2017/3/29.
 */
public class ActWithStoreName extends SaleActWithBLOBs {
    private String storeName;

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
