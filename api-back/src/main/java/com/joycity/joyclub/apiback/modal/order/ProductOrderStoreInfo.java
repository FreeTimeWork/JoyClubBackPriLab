package com.joycity.joyclub.apiback.modal.order;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/4/27.
 */
public class ProductOrderStoreInfo extends ProductOrderStore {
    private ProductOrder mainOrder;
    private String clientName;
    private String clientPhone;
    private List<ProductOrderDetailInfo> details;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public ProductOrder getMainOrder() {
        return mainOrder;
    }

    public void setMainOrder(ProductOrder mainOrder) {

        this.mainOrder = mainOrder;
    }

    public List<ProductOrderDetailInfo> getDetails() {
        return details;
    }

    public void setDetails(List<ProductOrderDetailInfo> details) {
        this.details = details;
    }
}
