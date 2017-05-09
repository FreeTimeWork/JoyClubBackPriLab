package com.joycity.joyclub.product.modal;

import com.joycity.joyclub.product.modal.generated.SaleProductOrder;

/**
 * Created by CallMeXYZ on 2017/4/24.
 */
public class ProductStoreOrderWithDetailsAndMainOrder extends ProductStoreOrderWithDetails {

    private SaleProductOrder mainOrder;

    public SaleProductOrder getMainOrder() {
        return mainOrder;
    }

    public void setMainOrder(SaleProductOrder mainOrder) {
        this.mainOrder = mainOrder;
    }
}
