package com.joycity.joyclub.apifront.modal.store;

import com.joycity.joyclub.apifront.modal.base.IdNamePortrait;
import com.joycity.joyclub.apifront.modal.designer.SimpleDesigner;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/4/14.
 */
public class StoreInfoPage extends Store {
    private List<SimpleDesigner> designers;

    public List<SimpleDesigner> getDesigners() {
        return designers;
    }

    public void setDesigners(List<SimpleDesigner> designers) {
        this.designers = designers;
    }
}
