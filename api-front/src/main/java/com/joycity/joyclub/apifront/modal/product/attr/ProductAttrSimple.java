package com.joycity.joyclub.apifront.modal.product.attr;

/**
 * Created by Administrator on 2017/4/15.
 */
public class ProductAttrSimple {
    private Long id;
    private Long productId;
    private String name;
    private Integer num;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
