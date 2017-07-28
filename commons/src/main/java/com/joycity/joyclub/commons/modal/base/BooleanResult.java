package com.joycity.joyclub.commons.modal.base;

/**
 * Created by fangchen.chai on 2017/7/24
 * 验证等返回Boolean类型结果
 */
public class BooleanResult {
    private Boolean resultFlag;

    public Boolean getResultFlag() {
        return resultFlag;
    }

    public void setResultFlag(Boolean resultFlag) {
        this.resultFlag = resultFlag;
    }

    public BooleanResult() {

    }

    public BooleanResult(Boolean resultFlag) {

        this.resultFlag = resultFlag;
    }
}
