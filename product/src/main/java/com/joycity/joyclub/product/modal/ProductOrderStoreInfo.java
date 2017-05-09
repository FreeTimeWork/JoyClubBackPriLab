package com.joycity.joyclub.product.modal;

import com.joycity.joyclub.product.modal.generated.SaleProductOrder;
import com.joycity.joyclub.product.modal.generated.SaleProductOrderStore;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/4/27.
 */
public class ProductOrderStoreInfo extends SaleProductOrderStore {
    private SaleProductOrder mainOrder;
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

    public SaleProductOrder getMainOrder() {
        return mainOrder;
    }

    public void setMainOrder(SaleProductOrder mainOrder) {

        this.mainOrder = mainOrder;
    }

    public List<ProductOrderDetailInfo> getDetails() {
        return details;
    }

    public void setDetails(List<ProductOrderDetailInfo> details) {
        this.details = details;
    }
}
