package com.joycity.joyclub.card_coupon.modal;

/**
 * Created by fangchen.chai on 2017/7/13.
 */
public class BatchAndSum {
    private String batch;
    private Integer sum;

    public BatchAndSum() {

    }

    public BatchAndSum(String batch, Integer sum) {
        this.batch = batch;
        this.sum = sum;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }
}
