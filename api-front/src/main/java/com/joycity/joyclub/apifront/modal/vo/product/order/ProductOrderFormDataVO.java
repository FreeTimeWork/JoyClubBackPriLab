package com.joycity.joyclub.apifront.modal.vo.product.order;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by CallMeXYZ on 2017/7/20.
 */
public class ProductOrderFormDataVO {
    @NotNull
    private List<Long> attrIds;

    public List<Long> getAttrIds() {
        return attrIds;
    }

    public void setAttrIds(List<Long> attrIds) {
        this.attrIds = attrIds;
    }
}
