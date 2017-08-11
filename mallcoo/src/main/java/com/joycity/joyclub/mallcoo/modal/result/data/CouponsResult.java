package com.joycity.joyclub.mallcoo.modal.result.data;

import java.util.List;

/**
 * CouponResult
 *
 * @author CallMeXYZ
 * @date 2017/8/8
 */
public class CouponsResult {
    private List<CouponSimpleInfo> couponInfoList;
    private int count;

    public List<CouponSimpleInfo> getCouponInfoList() {
        return couponInfoList;
    }

    public void setCouponInfoList(List<CouponSimpleInfo> couponInfoList) {
        this.couponInfoList = couponInfoList;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
