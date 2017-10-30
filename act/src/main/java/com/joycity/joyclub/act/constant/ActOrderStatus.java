package com.joycity.joyclub.act.constant;

import java.util.HashMap;
import java.util.Map;

public enum ActOrderStatus {

    UNPAID(new Byte("0"),"待支付"),
    CANCEL(new Byte("1"),"已取消"),
    PAID(new Byte("2"),"已支付"),
    CHECK(new Byte("4"),"已核销"),
    REFUNDING(new Byte("5"),"退款中"),
    REFUNDED(new Byte("6"),"已退款"),

    ;

    private Byte id;
    private String name;

    public static Map<Byte, String> actOrderStatusMap;

    static {
        actOrderStatusMap = new HashMap<>();
        actOrderStatusMap.put(UNPAID.id, UNPAID.name);
        actOrderStatusMap.put(CANCEL.id, CANCEL.name);
        actOrderStatusMap.put(PAID.id, PAID.name);
        actOrderStatusMap.put(CHECK.id, CHECK.name);
        actOrderStatusMap.put(REFUNDING.id, REFUNDING.name);
        actOrderStatusMap.put(REFUNDED.id, REFUNDED.name);
    }

    ActOrderStatus() {
    }

    ActOrderStatus(Byte id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName(Byte id) {
        return actOrderStatusMap.get(id);
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
