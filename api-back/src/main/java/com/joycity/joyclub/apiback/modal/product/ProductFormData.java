package com.joycity.joyclub.apiback.modal.product;


import com.joycity.joyclub.apiback.modal.base.IdNameResult;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/3/29.
 */
public class ProductFormData {
    private List<IdNameResult> designers;
    private List<IdNameResult> categories;

    public List<IdNameResult> getDesigners() {
        return designers;
    }

    public void setDesigners(List<IdNameResult> designers) {
        this.designers = designers;
    }

    public List<IdNameResult> getCategories() {
        return categories;
    }

    public void setCategories(List<IdNameResult> categories) {
        this.categories = categories;
    }
}
