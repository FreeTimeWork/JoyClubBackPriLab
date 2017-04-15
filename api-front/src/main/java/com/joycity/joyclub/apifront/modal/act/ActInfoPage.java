package com.joycity.joyclub.apifront.modal.act;

import com.joycity.joyclub.apifront.modal.base.IdNamePortrait;

/**
 * Created by CallMeXYZ on 2017/4/13.
 * 前端 actInfo活动详情需要的数据
 */
public class ActInfoPage extends Act {
    /**
     * 真实的价格
     */
    private Integer price;

    private IdNamePortrait store;

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
}
