package com.joycity.joyclub.apiback.modal.act.attr;

import com.joycity.joyclub.apiback.modal.generated.SaleActAttr;

/**
 * Created by CallMeXYZ on 2017/3/30.
 */
public class ActAttrWithActName extends SaleActAttr {
    private String actName;

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }
}
