package com.joycity.joyclub.act.modal;


import com.joycity.joyclub.act.modal.generated.SaleActWithBLOBs;

/**
 * Created by CallMeXYZ on 2017/3/29.
 */
public class ActWithCategoryName extends SaleActWithBLOBs {
    private String categoryName;


    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
