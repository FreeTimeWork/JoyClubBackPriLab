package com.joycity.joyclub.apifront.modal.vo.product.order;

import com.joycity.joyclub.apifront.modal.vo.BaseSubProjectVO;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by CallMeXYZ on 2017/7/20.
 */
public class ProductOrderVO extends BaseSubProjectVO {
    @NotNull
    private List<ProductOrderItemVO> items;
    /**
     * 自提还是快递
     */
    @NotNull
    private Boolean pickupOrPost;
    /**
     * 是否是从购物车下的订单 如果是则需要对购物车减数量
     */
    private Boolean fromCart = false;
    /**
     * 如果快递 需要提供快递地址id
     */
    private Long postAddressId;

    public List<ProductOrderItemVO> getItems() {
        return items;
    }

    public void setItems(List<ProductOrderItemVO> items) {
        this.items = items;
    }

    public Boolean getPickupOrPost() {
        return pickupOrPost;
    }

    public void setPickupOrPost(Boolean pickupOrPost) {
        this.pickupOrPost = pickupOrPost;
    }

    public Boolean getFromCart() {
        return fromCart;
    }

    public void setFromCart(Boolean fromCart) {
        this.fromCart = fromCart;
    }

    public Long getPostAddressId() {
        return postAddressId;
    }

    public void setPostAddressId(Long postAddressId) {
        this.postAddressId = postAddressId;
    }
}
