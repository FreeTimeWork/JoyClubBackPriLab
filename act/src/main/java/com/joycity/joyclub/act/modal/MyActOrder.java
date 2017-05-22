package com.joycity.joyclub.act.modal;

import java.util.Date;

/**
 *
 * Created by CallMeXYZ on 2017/5/22.
 */
public class MyActOrder {
    private Long id;
    private Long actId;
    private Long attrId;
    private String code;
    private Byte status;
    private Float moneySum;
    private Integer pointSum;
    private Byte payType;
    private Integer num;
    private Date payTime;
    private Date cancelTime;
    private Date checkTime;
    private String attrName;
    private String actName;
    private String address;
    private String portrait;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getActId() {
        return actId;
    }

    public void setActId(Long actId) {
        this.actId = actId;
    }

    public Long getAttrId() {
        return attrId;
    }

    public void setAttrId(Long attrId) {
        this.attrId = attrId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Float getMoneySum() {
        return moneySum;
    }

    public void setMoneySum(Float moneySum) {
        this.moneySum = moneySum;
    }

    public Integer getPointSum() {
        return pointSum;
    }

    public void setPointSum(Integer pointSum) {
        this.pointSum = pointSum;
    }

    public Byte getPayType() {
        return payType;
    }

    public void setPayType(Byte payType) {
        this.payType = payType;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }
}
