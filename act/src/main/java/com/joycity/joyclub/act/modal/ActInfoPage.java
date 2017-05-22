package com.joycity.joyclub.act.modal;

import com.joycity.joyclub.act.modal.generated.SaleAct;
import com.joycity.joyclub.act.modal.generated.SaleActPrice;
import com.joycity.joyclub.commons.modal.base.IdNamePortrait;
import com.joycity.joyclub.commons.modal.generated.SysActCategory;

/**
 * Created by CallMeXYZ on 2017/4/13.
 * 前端 actInfo活动详情需要的数据
 */
public class ActInfoPage  {

    private SaleAct act;
    private SaleActPrice price;
    private SysActCategory category;

    private IdNamePortrait store;

    public SaleAct getAct() {
        return act;
    }

    public void setAct(SaleAct act) {
        this.act = act;
    }

    public SaleActPrice getPrice() {
        return price;
    }

    public void setPrice(SaleActPrice price) {
        this.price = price;
    }

    public IdNamePortrait getStore() {
        return store;
    }

    public void setStore(IdNamePortrait store) {

        this.store = store;
    }

    public SysActCategory getCategory() {
        return category;
    }

    public void setCategory(SysActCategory category) {
        this.category = category;
    }
}
