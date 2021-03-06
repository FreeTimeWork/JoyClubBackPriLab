package com.joycity.joyclub.product.modal;

import com.joycity.joyclub.product.modal.generated.SaleProductOrderStore;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/4/24.
 */
public class ProductStoreOrderWithDetails extends SaleProductOrderStore {
    private String name;
    private String pickupAddress;
    private String servicePhone;
    private List<ProductOrderDetailInfo> orderDetails;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServicePhone() {
        return servicePhone;
    }

    public void setServicePhone(String servicePhone) {
        this.servicePhone = servicePhone;
    }

    public String getPickupAddress() {
        return pickupAddress;
    }

    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public List<ProductOrderDetailInfo> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<ProductOrderDetailInfo> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
