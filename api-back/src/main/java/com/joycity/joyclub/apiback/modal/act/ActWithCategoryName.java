package com.joycity.joyclub.apiback.modal.act;

import com.joycity.joyclub.apiback.modal.generated.SaleProductWithBLOBs;

/**
 * Created by CallMeXYZ on 2017/3/29.
 */
public class ActWithCategoryName extends SaleProductWithBLOBs {
    private String categoryName;


    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
