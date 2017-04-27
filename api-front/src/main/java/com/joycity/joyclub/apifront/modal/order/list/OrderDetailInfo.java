package com.joycity.joyclub.apifront.modal.order.list;

import com.joycity.joyclub.apifront.modal.order.ProductOrderDetail;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/4/24.
 */
public class OrderDetailInfo extends ProductOrderDetail {
    private String portrait;
    private String productName;
    private String attrName;
    private List<StoreOrderWithDetails> details;

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

    public List<StoreOrderWithDetails> getDetails() {
        return details;
    }

    public void setDetails(List<StoreOrderWithDetails> details) {
        this.details = details;
    }
}
