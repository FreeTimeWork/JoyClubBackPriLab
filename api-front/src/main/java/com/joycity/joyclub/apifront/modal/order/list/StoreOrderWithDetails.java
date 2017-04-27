package com.joycity.joyclub.apifront.modal.order.list;

import com.joycity.joyclub.apifront.modal.order.ProductOrderStore;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/4/24.
 */
public class StoreOrderWithDetails extends ProductOrderStore {
    private String name;
    private String pickupAddress;
    private String servicePhone;
    private List<OrderDetailInfo> orderDetails;



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

    public List<OrderDetailInfo> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetailInfo> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
