package com.joycity.joyclub.commons.modal.base;

/**
 * 数据创建相关，返回的data内容
 * Created by CallMeXYZ on 2017/3/28.
 */
public class CreateResult {
    private Long id;

    public CreateResult(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
