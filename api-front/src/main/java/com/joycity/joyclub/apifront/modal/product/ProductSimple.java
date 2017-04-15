package com.joycity.joyclub.apifront.modal.product;

/**
 * Created by CallMeXYZ on 2017/4/14.
 */
public class ProductSimple {
    private Long id;
    private String name;
    private String portrait;
    private Integer price;
    private Float pointRate;
    /**
     * 基于分类的积分比例
     */
    private Float basePointRate;

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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Float getPointRate() {
        return pointRate;
    }

    public void setPointRate(Float pointRate) {
        this.pointRate = pointRate;
    }

    public Float getBasePointRate() {
        return basePointRate;
    }

    public void setBasePointRate(Float basePointRate) {
        this.basePointRate = basePointRate;
    }
}
