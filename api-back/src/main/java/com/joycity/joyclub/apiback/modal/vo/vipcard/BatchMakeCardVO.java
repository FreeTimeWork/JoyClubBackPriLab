package com.joycity.joyclub.apiback.modal.vo.vipcard;

import javax.validation.constraints.NotNull;

/**
 * Created by CallMeXYZ on 2017/7/19.
 */
public class BatchMakeCardVO {
    @NotNull
    private Long infoId;
    @NotNull
    private String batch;
    @NotNull
    private String type;
    @NotNull
    private Integer num;

    public Long getInfoId() {
        return infoId;
    }

    public void setInfoId(Long infoId) {
        this.infoId = infoId;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
