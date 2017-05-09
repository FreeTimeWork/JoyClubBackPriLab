package com.joycity.joyclub.product.modal;


import com.joycity.joyclub.product.modal.generated.SaleProductPrice;

/**
 * Created by CallMeXYZ on 2017/3/30.
 */
public class ProductPriceWithProductInfo extends SaleProductPrice {
    private String storeName;
    private String productName;
    private Float basePrice;

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

    public Float getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Float basePrice) {
        this.basePrice = basePrice;
    }
}
