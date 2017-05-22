package com.joycity.joyclub.apifront.service.impl;

import com.joycity.joyclub.apifront.mapper.manual.cart.CartFrontMapper;
import com.joycity.joyclub.apifront.modal.cart.Cart;
import com.joycity.joyclub.apifront.modal.cart.CartInfo;
import com.joycity.joyclub.apifront.service.CartFrontService;
import com.joycity.joyclub.commons.modal.base.CreateResult;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.modal.base.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/4/17.
 */
@Service
public class CartFrontServiceImpl implements CartFrontService {
    @Autowired
    CartFrontMapper cartMapper;

    @Override
    public ResultData addToCart(Long projectId, Long clientId, Long attrId, Integer num) {
        Long cartId = cartMapper.getIdByAttr(projectId, clientId, attrId);
        if (cartId != null) {
            //存在则增加num
            return new ResultData(new UpdateResult(cartMapper.addCartNum(cartId, num)));
        } else {
            Cart cart = new Cart();
            //project client attr存在与否不验证了
            cart.setProjectId(projectId);
            cart.setClientId(clientId);
            cart.setAttrId(attrId);
            cart.setNum(num);
            cartMapper.insertSelective(cart);
            return new ResultData(new CreateResult(cart.getId()));
        }

    }

    @Override
    public ResultData setCartNum(Long id, Integer num) {
        //这里就不判断购物车是否存在了，返回值的affectNum里可以看出来
        UpdateResult updateResult = new UpdateResult();

        Integer oldNum = cartMapper.getNumById(id);
        if (!(oldNum > 0)) {
            updateResult.setAffectNum(0);
        } else {
            updateResult.setAffectNum(cartMapper.setCartNum(id, num));
        }
        return new ResultData(updateResult);
    }

    @Override
    public List<CartInfo> getCartList(Long projectId, Long clientId) {

        return cartMapper.getCartInfoList(projectId, clientId);
    }

    @Override
    public ResultData deleteCart(Long id) {
        return new ResultData(new UpdateResult(cartMapper.deleteCart(id)));
    }

    @Override
    public Integer subCartNum(Long projectId, Long clientId, Long attrId, Integer num) {
        Integer oldNum = cartMapper.getNumByFilter(projectId,clientId,attrId);
        if(oldNum==0) {
            return 0;
        }
        else {
            return cartMapper.subCartNum(projectId,clientId,attrId,num);
        }

    }
}
