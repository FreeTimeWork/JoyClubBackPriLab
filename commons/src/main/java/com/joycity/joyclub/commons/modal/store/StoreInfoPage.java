package com.joycity.joyclub.commons.modal.store;

import com.joycity.joyclub.commons.modal.designer.SimpleDesigner;
import com.joycity.joyclub.commons.modal.generated.SysStore;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/4/14.
 */
public class StoreInfoPage extends SysStore {
    private List<SimpleDesigner> designers;

    public List<SimpleDesigner> getDesigners() {
        return designers;
    }

    public void setDesigners(List<SimpleDesigner> designers) {
        this.designers = designers;
    }
}
