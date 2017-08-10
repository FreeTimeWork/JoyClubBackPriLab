package com.joycity.joyclub.mallcoo.modal.result.data;

import java.util.List;

/**
 * CouponResult
 *
 * @author CallMeXYZ
 * @date 2017/8/8
 */
public class CouponsResult {
    private List<CoupoSimpleInfo> CouponInfoList;
    private int Count;

    public List<CoupoSimpleInfo> getCouponInfoList() {
        return CouponInfoList;
    }

    public void setCouponInfoList(List<CoupoSimpleInfo> couponInfoList) {
        CouponInfoList = couponInfoList;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }
}
