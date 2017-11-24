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
    private String ticket;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Long getLaunchId() {
        return launchId;
    }

    public void setLaunchId(Long launchId) {
        this.launchId = launchId;
    }
}
