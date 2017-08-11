package com.joycity.joyclub.mallcoo.modal.result.data;

import java.util.Date;

/**
 * Created by CallMeXYZ on 2017/7/28.
 */
public class OffLineShopInfo {
    private Integer id;
    private String name;
    private String crmShopID;
    private String logo;
    private Integer shopType;
    private Integer commercialTypeID;
    private String subCommercialTypeName;
    private Integer floorID;
    private String floorName;
    private String doorNo;
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCrmShopID() {
        return crmShopID;
    }

    public void setCrmShopID(String crmShopID) {
        this.crmShopID = crmShopID;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Integer getShopType() {
        return shopType;
    }

    public void setShopType(Integer shopType) {
        this.shopType = shopType;
    }

    public Integer getCommercialTypeID() {
        return commercialTypeID;
    }

    public void setCommercialTypeID(Integer commercialTypeID) {
        this.commercialTypeID = commercialTypeID;
    }

    public String getSubCommercialTypeName() {
        return subCommercialTypeName;
    }

    public void setSubCommercialTypeName(String subCommercialTypeName) {
        this.subCommercialTypeName = subCommercialTypeName;
    }

    public Integer getFloorID() {
        return floorID;
    }

    public void setFloorID(Integer floorID) {
        this.floorID = floorID;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public String getDoorNo() {
        return doorNo;
    }

    public void setDoorNo(String doorNo) {
        this.doorNo = doorNo;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
