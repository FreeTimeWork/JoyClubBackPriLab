package com.joycity.joyclub.product.modal;



import com.joycity.joyclub.commons.modal.base.IdName;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/3/29.
 */
public class ProductFormData {
    private List<IdName> designers;
    private List<IdName> categories;

    public List<IdName> getDesigners() {
        return designers;
    }

    public void setDesigners(List<IdName> designers) {
        this.designers = designers;
    }

    public List<IdName> getCategories() {
        return categories;
    }

    public void setCategories(List<IdName> categories) {
        this.categories = categories;
    }
}
