package com.joycity.joyclub.apifront.modal.client;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/17.
 */
public class VipCardInfo {
    private String cardType;
    private Integer vipPoint;
    private String phone;
    private List<Map<String, Object>> pointRecords;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public Integer getVipPoint() {
        return vipPoint;
    }

    public void setVipPoint(Integer vipPoint) {
        this.vipPoint = vipPoint;
    }

    public List<Map<String, Object>> getPointRecords() {
        return pointRecords;
    }

    public void setPointRecords(List<Map<String, Object>> pointRecords) {
        this.pointRecords = pointRecords;
    }
}
