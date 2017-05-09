package com.joycity.joyclub.product.modal;

import com.joycity.joyclub.product.modal.generated.SaleProductOrder;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/4/24.
 */
public class ProductMainOrderWithStores extends SaleProductOrder {
    private List<ProductStoreOrderWithDetails> storeOrders;

    public List<ProductStoreOrderWithDetails> getStoreOrders() {
        return storeOrders;
    }

    public void setStoreOrders(List<ProductStoreOrderWithDetails> storeOrders) {
        this.storeOrders = storeOrders;
    }
}
