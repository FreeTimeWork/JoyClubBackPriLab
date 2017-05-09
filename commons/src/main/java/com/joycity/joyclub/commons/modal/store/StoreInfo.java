package com.joycity.joyclub.commons.modal.store;


import com.joycity.joyclub.commons.modal.base.IdNamePortrait;

import java.util.List;

/**
 * 店铺信息
 * Created by CallMeXYZ on 2017/4/5.
 */
public class StoreInfo {
    //    各项参数参考数据库设计,或者api-back里的 SysStore对象
    private Long id;
    private Long projectId;
    private String name;
    private String headImg;
    private String portrait;
    private String intro;
    private String servicePhone;
    private String pickupAddress;

    /**
     * 设计师列表
     */
    private List<IdNamePortrait> designers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getServicePhone() {
        return servicePhone;
    }

    public void setServicePhone(String servicePhone) {
        this.servicePhone = servicePhone;
    }

    public String getPickupAddress() {
        return pickupAddress;
    }

    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public List<IdNamePortrait> getDesigners() {
        return designers;
    }

    public void setDesigners(List<IdNamePortrait> designers) {
        this.designers = designers;
    }
}
