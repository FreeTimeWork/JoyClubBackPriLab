package com.joycity.joyclub.product.modal;

import com.joycity.joyclub.product.modal.generated.SaleProductOrderDetail;

/**
 * Created by CallMeXYZ on 2017/4/24.
 */
public class ProductOrderDetailInfo extends SaleProductOrderDetail {
    private String portrait;
    private String productName;
    private String attrName;

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

}
