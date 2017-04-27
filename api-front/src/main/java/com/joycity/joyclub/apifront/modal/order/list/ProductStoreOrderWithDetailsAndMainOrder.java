package com.joycity.joyclub.apifront.modal.order.list;

import com.joycity.joyclub.apifront.modal.order.ProductOrder;

/**
 * Created by CallMeXYZ on 2017/4/24.
 */
public class ProductStoreOrderWithDetailsAndMainOrder extends ProductStoreOrderWithDetails {

    private ProductOrder mainOrder;

    public ProductOrder getMainOrder() {
        return mainOrder;
    }

    public void setMainOrder(ProductOrder mainOrder) {
        this.mainOrder = mainOrder;
    }
}
