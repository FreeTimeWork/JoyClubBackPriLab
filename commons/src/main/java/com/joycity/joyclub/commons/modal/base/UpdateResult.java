package com.joycity.joyclub.commons.modal.base;

/**
 * 数据更新相关，返回的data内容
 * Created by CallMeXYZ on 2017/3/28.
 */
public class UpdateResult {
    private Integer affectNum;

    public UpdateResult(Integer affectNum) {
        this.affectNum = affectNum;
    }

    public Integer getAffectNum() {
        return affectNum;
    }

    public void setAffectNum(Integer affectNum) {
        this.affectNum = affectNum;
    }
}
