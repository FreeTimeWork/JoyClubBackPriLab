package com.joycity.joyclub.act.modal;

/**
 * Created by CallMeXYZ on 2017/4/13.
 */
public class ActSimple {
    /**
     * actId
     */
    private Long id;
    private String name;
    private String portrait;
    private Float basePrice;
    private Float price;
    private Float basePointRate;
    private Float pointRate;
    private String address;
    private Long storeId;
    private String storeName;
    private String servicePhone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public Float getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Float basePrice) {
        this.basePrice = basePrice;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getBasePointRate() {
        return basePointRate;
    }

    public void setBasePointRate(Float basePointRate) {
        this.basePointRate = basePointRate;
    }

    public Float getPointRate() {
        return pointRate;
    }

    public void setPointRate(Float pointRate) {
        this.pointRate = pointRate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getServicePhone() {
        return servicePhone;
    }

    public void setServicePhone(String servicePhone) {
        this.servicePhone = servicePhone;
    }
}
