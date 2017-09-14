package com.joycity.joyclub.act.modal;

import com.joycity.joyclub.act.modal.generated.SaleAct;
import com.joycity.joyclub.act.modal.generated.SaleActType;

import java.util.List;

/**
 * Created by fangchen.chai on 2017/9/14
 */
public class ActTypeWithAct extends SaleActType{
    List<ActSimple> acts;

    public List<ActSimple> getActs() {
        return acts;
    }

    public void setActs(List<ActSimple> acts) {
        this.acts = acts;
    }
}
