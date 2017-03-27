package com.joycity.joyclub.apiback.modal.project;

import com.joycity.joyclub.apiback.modal.generated.SysProject;

/**
 * Created by CallMeXYZ on 2017/2/27.
 */
public class BaseProject extends SysProject {
    private String typeName;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
