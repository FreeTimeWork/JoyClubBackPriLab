package com.joycity.joyclub.apifront.modal.order.list;

import com.joycity.joyclub.apifront.modal.order.ProductOrder;
import com.joycity.joyclub.apifront.modal.order.ProductOrderStore;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/4/24.
 */
public class StoreOrderWithDetailsAndMainOrder extends StoreOrderWithDetails {

    private ProductOrder mainOrder;

    public ProductOrder getMainOrder() {
        return mainOrder;
    }

    public void setMainOrder(ProductOrder mainOrder) {
        this.mainOrder = mainOrder;
    }
}
