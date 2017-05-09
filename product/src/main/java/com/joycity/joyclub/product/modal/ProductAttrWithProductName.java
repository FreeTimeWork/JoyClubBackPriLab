package com.joycity.joyclub.product.modal;

import com.joycity.joyclub.product.modal.generated.SaleProductAttr;

/**
 * Created by CallMeXYZ on 2017/5/8.
 */
public class ProductAttrWithProductName extends SaleProductAttr{
    private String productName;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
