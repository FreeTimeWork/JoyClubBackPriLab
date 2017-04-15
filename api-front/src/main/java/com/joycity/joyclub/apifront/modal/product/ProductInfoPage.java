package com.joycity.joyclub.apifront.modal.product;

import com.joycity.joyclub.apifront.modal.act.Act;
import com.joycity.joyclub.apifront.modal.base.IdNamePortrait;

/**
 * Created by CallMeXYZ on 2017/4/13.
 * 前端 商品详情需要的数据
 */
public class ProductInfoPage extends ProductWithPointRate {
    /**
     * 真实的价格
     */
    private Integer price;
    private IdNamePortrait designer;
    private Float basePointRate;
    private IdNamePortrait store;

    public Float getBasePointRate() {
        return basePointRate;
    }

    public void setBasePointRate(Float basePointRate) {
        this.basePointRate = basePointRate;
    }

    public IdNamePortrait getStore() {
        return store;
    }

    public void setStore(IdNamePortrait store) {
        this.store = store;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public IdNamePortrait getDesigner() {
        return designer;
    }

    public void setDesigner(IdNamePortrait designer) {
        this.designer = designer;
    }
}
