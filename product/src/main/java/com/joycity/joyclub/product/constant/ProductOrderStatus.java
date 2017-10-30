package com.joycity.joyclub.product.constant;

import java.util.HashMap;
import java.util.Map;

public enum ProductOrderStatus {

    UNPAID(new Byte("0"),"待支付"),
    CANCEL(new Byte("1"),"已取消"),
    PAID(new Byte("2"),"已支付"),
    SHIPMENT(new Byte("3"),"已发货"),
    CHECK(new Byte("4"),"已核销"),
    REFUNDING(new Byte("5"),"退款中"),
    REFUNDED(new Byte("6"),"已退款"),

    ;

    private Byte id;
    private String name;

    public static Map<Byte, String> productOrderStatusMap;

    static {
        productOrderStatusMap = new HashMap<>();
        productOrderStatusMap.put(UNPAID.id, UNPAID.name);
        productOrderStatusMap.put(CANCEL.id, CANCEL.name);
        productOrderStatusMap.put(PAID.id, PAID.name);
        productOrderStatusMap.put(SHIPMENT.id, SHIPMENT.name);
        productOrderStatusMap.put(CHECK.id, CHECK.name);
        productOrderStatusMap.put(REFUNDING.id, REFUNDING.name);
        productOrderStatusMap.put(REFUNDED.id, REFUNDED.name);
    }

    ProductOrderStatus() {
    }

    ProductOrderStatus(Byte id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName(Byte id) {
        return productOrderStatusMap.get(id);
    }

    public Byte getId() {
        return id;
    }

    public void setId(Byte id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
