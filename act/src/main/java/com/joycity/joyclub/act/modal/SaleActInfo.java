package com.joycity.joyclub.act.modal;

import com.joycity.joyclub.act.modal.generated.SaleActWithBLOBs;

/**
 * Created by fangchen.chai on 2017/10/13
 */
public class SaleActInfo extends SaleActWithBLOBs {
    private String applyActName;

    public String getApplyActName() {
        return applyActName;
    }

    public void setApplyActName(String applyActName) {
        this.applyActName = applyActName;
    }
}
