package com.joycity.joyclub.apifront.modal.order.list;

import com.joycity.joyclub.apifront.modal.order.ProductOrder;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/4/24.
 */
public class ProductMainOrderWithStores extends ProductOrder {
    private List<ProductStoreOrderWithDetails> storeOrders;

    public List<ProductStoreOrderWithDetails> getStoreOrders() {
        return storeOrders;
    }

    public void setStoreOrders(List<ProductStoreOrderWithDetails> storeOrders) {
        this.storeOrders = storeOrders;
    }
}
