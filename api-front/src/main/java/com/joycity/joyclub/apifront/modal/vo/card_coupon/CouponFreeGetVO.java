package com.joycity.joyclub.apifront.modal.vo.card_coupon;

import javax.validation.constraints.NotNull;

/**
 * CouponFreeGetVO
 *
 * @author CallMeXYZ
 * @date 2017/8/9
 */
public class CouponFreeGetVO {
    @NotNull
    private Long launchId;

    public Long getLaunchId() {
        return launchId;
    }

    public void setLaunchId(Long launchId) {
        this.launchId = launchId;
    }
}
