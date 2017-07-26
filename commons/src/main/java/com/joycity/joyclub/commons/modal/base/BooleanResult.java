package com.joycity.joyclub.commons.modal.base;

/**
 * Created by fangchen.chai on 2017/7/24
 * 验证等返回Boolean类型结果
 */
public class BooleanResult {
    private Boolean result;

    public BooleanResult(Boolean result) {
        this.result = result;
    }

    public BooleanResult() {
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }
}
