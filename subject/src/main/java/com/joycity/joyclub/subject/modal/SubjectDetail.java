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
    private String actsName;
    private List<ShowCouponLaunchInfo> coupons;
    private String couponsName;
    private List<ProductSimple> products;
    private String productsName;

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

    public String getActsName() {
        return actsName;
    }

    public void setActsName(String actsName) {
        this.actsName = actsName;
    }

    public String getCouponsName() {
        return couponsName;
    }

    public void setCouponsName(String couponsName) {
        this.couponsName = couponsName;
    }

    public String getProductsName() {
        return productsName;
    }

    public void setProductsName(String productsName) {
        this.productsName = productsName;
    }
}
