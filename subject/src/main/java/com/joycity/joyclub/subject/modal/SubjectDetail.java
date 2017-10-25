package com.joycity.joyclub.subject.modal;

import com.joycity.joyclub.act.modal.ActSimple;
import com.joycity.joyclub.card_coupon.modal.ShowCouponLaunchInfo;
import com.joycity.joyclub.product.modal.ProductSimple;

import java.util.List;

/**
 * @auther fangchen.chai ON 2017/10/25
 */
public class SubjectDetail extends SubjectWithType {

    private List<ActSimple> acts;
    private List<ShowCouponLaunchInfo> coupons;
    private List<ProductSimple> products;

    public List<ActSimple> getActs() {
        return acts;
    }

    public void setActs(List<ActSimple> acts) {
        this.acts = acts;
    }

    public List<ShowCouponLaunchInfo> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<ShowCouponLaunchInfo> coupons) {
        this.coupons = coupons;
    }

    public List<ProductSimple> getProducts() {
        return products;
    }

    public void setProducts(List<ProductSimple> products) {
        this.products = products;
    }
}
