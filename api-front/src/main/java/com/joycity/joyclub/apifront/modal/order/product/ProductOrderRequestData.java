package com.joycity.joyclub.apifront.modal.order.product;

/**
 * 生成订单时，前端需要传递的数据
 * Created by CallMeXYZ on 2017/4/5.
 */
public class ProductOrderRequestData {

    private Long clientId;

    private Byte receiveType;

    private Byte payType;

    //如果是快递，则需提供下面几项内容
    private String receiverName;

    private String receiverPhone;


    private String receiverAddress;


    private String receivePostcode;

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Byte getReceiveType() {
        return receiveType;
    }

    public void setReceiveType(Byte receiveType) {
        this.receiveType = receiveType;
    }

    public Byte getPayType() {
        return payType;
    }

    public void setPayType(Byte payType) {
        this.payType = payType;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getReceivePostcode() {
        return receivePostcode;
    }

    public void setReceivePostcode(String receivePostcode) {
        this.receivePostcode = receivePostcode;
    }
}
