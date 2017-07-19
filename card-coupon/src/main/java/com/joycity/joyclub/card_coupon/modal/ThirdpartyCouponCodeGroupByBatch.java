package com.joycity.joyclub.card_coupon.modal;

import java.util.List;

/**
 * Created by fangchen.chai on 2017/7/18.
 */
public class ThirdpartyCouponCodeGroupByBatch {

    private String batch;
    private List<String> codes;

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public List<String> getCodes() {
        return codes;
    }

    public void setCodes(List<String> codes) {
        this.codes = codes;
    }
}
