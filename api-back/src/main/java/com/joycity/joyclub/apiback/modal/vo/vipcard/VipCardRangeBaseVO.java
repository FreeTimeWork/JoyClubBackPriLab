package com.joycity.joyclub.apiback.modal.vo.vipcard;

import javax.validation.constraints.NotNull;

/**
 * Created by CallMeXYZ on 2017/7/19.
 */
public class VipCardRangeBaseVO {
    @NotNull
    private Long min;
    @NotNull
    private Long max;

    public Long getMin() {
        return min;
    }

    public void setMin(Long min) {
        this.min = min;
    }

    public Long getMax() {
        return max;
    }

    public void setMax(Long max) {
        this.max = max;
    }
}
