package com.joycity.joyclub.commons.modal.designer;

import com.joycity.joyclub.commons.modal.base.IdNamePortrait;
import com.joycity.joyclub.commons.modal.generated.SaleStoreDesigner;
import com.joycity.joyclub.commons.modal.generated.SaleStoreDesignerWithBLOBs;

/**
 * Created by CallMeXYZ on 2017/4/14.
 */
public class DesignerInfoPage extends SaleStoreDesignerWithBLOBs {
    private IdNamePortrait store;

    public IdNamePortrait getStore() {
        return store;
    }

    public void setStore(IdNamePortrait store) {
        this.store = store;
    }
}
