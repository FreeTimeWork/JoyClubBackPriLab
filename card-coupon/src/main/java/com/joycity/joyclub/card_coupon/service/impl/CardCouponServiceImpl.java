package com.joycity.joyclub.card_coupon.service.impl;

import java.util.List;

import com.joycity.joyclub.card_coupon.mapper.CardCouponMapper;
import com.joycity.joyclub.card_coupon.mapper.CardCouponStoreScopeMapper;
import com.joycity.joyclub.card_coupon.mapper.CardCouponVipScopeMapper;
import com.joycity.joyclub.card_coupon.modal.CreateCouponInfo;
import com.joycity.joyclub.card_coupon.modal.generated.CardCouponStoreScope;
import com.joycity.joyclub.card_coupon.modal.generated.CardCouponVipScope;
import com.joycity.joyclub.card_coupon.service.CardCouponService;
import com.joycity.joyclub.commons.AbstractGetListData;
import com.joycity.joyclub.commons.modal.base.CreateResult;
import com.joycity.joyclub.commons.modal.base.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by fangchen.chai on 2017/7/11.
 */
@Service
public class CardCouponServiceImpl implements CardCouponService {

    @Autowired
    private CardCouponMapper couponMapper;
    @Autowired
    private CardCouponStoreScopeMapper couponStoreScopeMapper;
    @Autowired
    private CardCouponVipScopeMapper couponVipScopeMapper;


    @Override
    @Transactional
    public ResultData createCardCoupon(CreateCouponInfo info) {
        couponMapper.insertSelective(info.getCardCoupon());
        for (CardCouponStoreScope storeScopes : info.getStoreScopes()) {
            storeScopes.setCouponId(info.getCardCoupon().getId());
            couponStoreScopeMapper.insertSelective(storeScopes);
        }
        for (CardCouponVipScope vipScopes : info.getVipScopes()) {
            vipScopes.setCouponId(info.getCardCoupon().getId());
            couponVipScopeMapper.insertSelective(vipScopes);
        }
        return new ResultData(new CreateResult(info.getCardCoupon().getId()));
    }

    @Override
    public ResultData getListByNameAndType(String name, Integer type) {
        name = "%" + name + "%";
        final String finalName = name;

        return new ResultData(new AbstractGetListData<CreateCouponInfo>() {
            @Override
            public Long countByFilter() {
                return null;
            }

            @Override
            public List<CreateCouponInfo> selectByFilter() {
                return null;
            }
        });
    }

}
