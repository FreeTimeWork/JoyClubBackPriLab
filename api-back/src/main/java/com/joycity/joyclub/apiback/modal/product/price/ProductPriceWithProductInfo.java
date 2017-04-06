package com.joycity.joyclub.apiback.modal.product.price;

import com.joycity.joyclub.apiback.modal.generated.SaleProductPrice;

/**
 * Created by CallMeXYZ on 2017/3/30.
 */
public class ProductPriceWithProductInfo extends SaleProductPrice {
    private String storeName;
    private String productName;
    private String basePrice;

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(String basePrice) {
        this.basePrice = basePrice;
    }
}
