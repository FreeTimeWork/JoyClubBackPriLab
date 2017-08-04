package com.joycity.joyclub.card_coupon.service.impl;

import com.joycity.joyclub.card_coupon.mapper.CardCouponLaunchMapper;
import com.joycity.joyclub.card_coupon.modal.ShowShopRatio;
import com.joycity.joyclub.card_coupon.service.ShopRatioService;
import com.joycity.joyclub.commons.AbstractGetListData;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fangchen.chai on 2017/8/4
 */
@Service
public class ShopRatioServiceImpl implements ShopRatioService{

    @Autowired
    private CardCouponLaunchMapper launchMapper;

    @Override
    public ResultData getListByLaunchId(Long launchId, String shopName, PageUtil pageUtil) {
        if (shopName != null) {

            shopName += "%" + shopName + "%";
        }

        String finalShopName = shopName;
        return new AbstractGetListData<ShowShopRatio>() {
            @Override
            public Long countByFilter() {
                return launchMapper.countShopRatioByLaunchId(launchId,finalShopName);
            }

            @Override
            public List<ShowShopRatio> selectByFilter() {
                return launchMapper.selectShopRatioByLaunchId(launchId,finalShopName,pageUtil);
            }
        }.getList(pageUtil);
    }
}
