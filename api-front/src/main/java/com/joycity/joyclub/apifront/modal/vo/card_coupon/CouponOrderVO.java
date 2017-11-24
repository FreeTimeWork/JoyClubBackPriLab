package com.joycity.joyclub.apifront.modal.vo.card_coupon;

/**
 * Created by fangchen.chai on 2017/8/2
 */
public class CouponOrderVO {
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
