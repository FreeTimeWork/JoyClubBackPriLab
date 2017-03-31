package com.joycity.joyclub.apiback.modal.product.attr;

import com.joycity.joyclub.apiback.modal.generated.SaleProductAttr;

/**
 * Created by CallMeXYZ on 2017/3/30.
 */
public class ProductAttrWithProductName extends SaleProductAttr {
    private String productName;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
