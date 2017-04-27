package com.joycity.joyclub.apifront.modal.order.list;

import com.joycity.joyclub.apifront.modal.order.ProductOrder;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/4/24.
 */
public class MainOrderWithStores extends ProductOrder {
    private List<StoreOrderWithDetails> storeOrders;

    public List<StoreOrderWithDetails> getStoreOrders() {
        return storeOrders;
    }

    public void setStoreOrders(List<StoreOrderWithDetails> storeOrders) {
        this.storeOrders = storeOrders;
    }
}
