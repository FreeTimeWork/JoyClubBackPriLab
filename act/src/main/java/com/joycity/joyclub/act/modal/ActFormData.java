package com.joycity.joyclub.act.modal;



import com.joycity.joyclub.commons.modal.base.IdName;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/3/29.
 */
public class ActFormData {

    private List<IdName> categories;
    private List<IdName> applyTypes;


    public List<IdName> getCategories() {
        return categories;
    }

    public void setCategories(List<IdName> categories) {
        this.categories = categories;
    }

    public List<IdName> getApplyTypes() {
        return applyTypes;
    }

    public void setApplyTypes(List<IdName> applyTypes) {
        this.applyTypes = applyTypes;
    }
}
