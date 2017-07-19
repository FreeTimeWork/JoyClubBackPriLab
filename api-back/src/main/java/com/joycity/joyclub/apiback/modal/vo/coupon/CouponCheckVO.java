package com.joycity.joyclub.apiback.modal.vo.coupon;

import javax.validation.constraints.NotNull;

/**
 * Created by CallMeXYZ on 2017/7/19.
 */
public class CouponCheckVO {
    @NotNull
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
