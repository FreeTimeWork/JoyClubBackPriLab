package com.joycity.joyclub.mallcoo.modal.result.data;

import java.util.Date;

/**
 * Created by CallMeXYZ on 2017/7/28.
 */
public class OffLineShopInfo {
    private Integer Id;
    private String Name;
    private String CrmShopID;
    private String Logo;
    private Integer ShopType;
    private Integer CommercialTypeID;
    private String SubCommercialTypeName;
    private Integer FloorID;
    private String FloorName;
    private String DoorNo;
    private Date UpdateTime;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCrmShopID() {
        return CrmShopID;
    }

    public void setCrmShopID(String crmShopID) {
        CrmShopID = crmShopID;
    }

    public String getLogo() {
        return Logo;
    }

    public void setLogo(String logo) {
        Logo = logo;
    }

    public Integer getShopType() {
        return ShopType;
    }

    public void setShopType(Integer shopType) {
        ShopType = shopType;
    }

    public Integer getCommercialTypeID() {
        return CommercialTypeID;
    }

    public void setCommercialTypeID(Integer commercialTypeID) {
        CommercialTypeID = commercialTypeID;
    }

    public String getSubCommercialTypeName() {
        return SubCommercialTypeName;
    }

    public void setSubCommercialTypeName(String subCommercialTypeName) {
        SubCommercialTypeName = subCommercialTypeName;
    }

    public Integer getFloorID() {
        return FloorID;
    }

    public void setFloorID(Integer floorID) {
        FloorID = floorID;
    }

    public String getFloorName() {
        return FloorName;
    }

    public void setFloorName(String floorName) {
        FloorName = floorName;
    }

    public String getDoorNo() {
        return DoorNo;
    }

    public void setDoorNo(String doorNo) {
        DoorNo = doorNo;
    }

    public Date getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(Date updateTime) {
        UpdateTime = updateTime;
    }
}
