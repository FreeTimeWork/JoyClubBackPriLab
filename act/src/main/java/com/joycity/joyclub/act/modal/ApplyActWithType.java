package com.joycity.joyclub.act.modal;

import com.joycity.joyclub.act.modal.generated.FrontApplyAct;

/**
 * Created by fangchen.chai on 2017/9/16
 */
public class ApplyActWithType extends FrontApplyAct {
    private String applyActTypeName;

    public String getApplyActTypeName() {
        return applyActTypeName;
    }

    public void setApplyActTypeName(String applyActTypeName) {
        this.applyActTypeName = applyActTypeName;
    }
}
