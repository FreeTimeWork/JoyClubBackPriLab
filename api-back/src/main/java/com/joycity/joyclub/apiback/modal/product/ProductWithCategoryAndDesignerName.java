package com.joycity.joyclub.apiback.modal.product;

import com.joycity.joyclub.apiback.modal.generated.SaleProductWithBLOBs;

/**
 * Created by CallMeXYZ on 2017/3/29.
 */
public class ProductWithCategoryAndDesignerName extends SaleProductWithBLOBs {
    private String categoryName;
    private String designerName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDesignerName() {
        return designerName;
    }

    public void setDesignerName(String designerName) {
        this.designerName = designerName;
    }
}
