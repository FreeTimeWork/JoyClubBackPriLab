package com.joycity.joyclub.product.modal;


import com.joycity.joyclub.product.modal.generated.SaleProductWithBLOBs;

/**
 * Created by CallMeXYZ on 2017/3/29.
 */
public class ProductWithStoreName extends SaleProductWithBLOBs {
    private String storeName;

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
