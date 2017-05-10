package com.joycity.joyclub.product.service.impl;


import com.joycity.joyclub.commons.AbstractGetListData;
import com.joycity.joyclub.commons.constant.OrderStatus;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;
import com.joycity.joyclub.commons.utils.ThrowBusinessExceptionUtil;
import com.joycity.joyclub.product.mapper.ProductOrderStoreMapper;
import com.joycity.joyclub.product.modal.ProductOrderStoreInfo;
import com.joycity.joyclub.product.service.ProductStoreOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/4/27.
 */
@Service
public class ProductStoreOrderServiceImpl implements ProductStoreOrderService {
    @Autowired
    ProductOrderStoreMapper storeOrderMapper;

    @Override
    public ResultData getList(final Long storeId, final Byte receiveType, final Byte status, final String code, final String name, final String phone, final PageUtil pageUtil) {
        return new AbstractGetListData<ProductOrderStoreInfo>() {
            @Override
            public Long countByFilter() {
                return storeOrderMapper.countByFilter(storeId, receiveType, status, code, name, phone, pageUtil);
            }

            @Override
            public List<ProductOrderStoreInfo> selectByFilter() {
                return storeOrderMapper.selectByFilter(storeId, receiveType, status, code, name, phone, pageUtil);

            }
        }.getList(pageUtil);
    }

    @Override
    public ResultData getInfo(Long id) {
        ProductOrderStoreInfo info = storeOrderMapper.selectById(id);
        ThrowBusinessExceptionUtil.checkNull(info, "订单不存在");
        info.setDetails(storeOrderMapper.getDetails(id));
        return new ResultData(info);
    }

    @Override
    public ResultData completeSelfFetch(Long storeOrderId) {
        // TODO: 2017/4/27 验证该订单属于该商户 验证该订单状态为待收货
        storeOrderMapper.setReceived(storeOrderId, OrderStatus.STORE_ORDER_STATUS_RECEIVED);
        return new ResultData();
    }

    @Override
    public ResultData completeDelivery(Long storeOrderId, String deliveryCompany, String deliveryCode) {
        // TODO: 2017/4/27 验证该订单属于该商户 验证该订单状态为待发货
        storeOrderMapper.setDeliveryInfo(storeOrderId, OrderStatus.STORE_ORDER_STATUS_SENT, deliveryCompany, deliveryCode);
        return new ResultData();
    }
    ////////////////////////////api front////////////////////////////

}
