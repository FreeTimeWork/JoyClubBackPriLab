package com.joycity.joyclub.card_coupon.service.impl;

import java.util.List;

import com.joycity.joyclub.card_coupon.mapper.CardCouponLaunchMapper;
import com.joycity.joyclub.card_coupon.mapper.CardCouponMapper;
import com.joycity.joyclub.card_coupon.mapper.CardCouponStoreScopeMapper;
import com.joycity.joyclub.card_coupon.mapper.CardCouponVipScopeMapper;
import com.joycity.joyclub.card_coupon.modal.CreateCouponInfo;
import com.joycity.joyclub.card_coupon.modal.ShowCouponInfo;
import com.joycity.joyclub.card_coupon.modal.generated.CardCoupon;
import com.joycity.joyclub.card_coupon.modal.generated.CardCouponStoreScope;
import com.joycity.joyclub.card_coupon.modal.generated.CardCouponVipScope;
import com.joycity.joyclub.card_coupon.service.CardCouponService;
import com.joycity.joyclub.commons.AbstractGetListData;
import com.joycity.joyclub.commons.constant.ResultCode;
import com.joycity.joyclub.commons.exception.BusinessException;
import com.joycity.joyclub.commons.modal.base.CreateResult;
import com.joycity.joyclub.commons.modal.base.ListResult;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.modal.base.UpdateResult;
import com.joycity.joyclub.commons.utils.PageUtil;
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
    @Autowired
    private CardCouponLaunchMapper launchMapper;


    @Override
    @Transactional
    public ResultData createCardCoupon(CreateCouponInfo info) {
        couponMapper.insertSelective(info);
        for (CardCouponStoreScope storeScopes : info.getStoreScopes()) {
            storeScopes.setCouponId(info.getId());
            couponStoreScopeMapper.insertSelective(storeScopes);
        }

        CardCouponVipScope vipScope = new CardCouponVipScope();
        vipScope.setCouponId(info.getId());
        for (String type : info.getVipScopes()) {
            vipScope.setVipType(type);
            couponVipScopeMapper.insertSelective(vipScope);
        }
        return new ResultData(new CreateResult(info.getId()));
    }

    @Override
    public ResultData getListByNameAndType(String name, Integer type, PageUtil pageUtil) {
        if (name != null) {
            name = "%" + name + "%";
        }
        final String finalName = name;
        ResultData data = new AbstractGetListData<ShowCouponInfo>() {
            @Override
            public Long countByFilter() {
                return couponMapper.countCardCouponByNameAndType(finalName, type, pageUtil);
            }

            @Override
            public List<ShowCouponInfo> selectByFilter() {
                return couponMapper.selectCardCouponByNameAndType(finalName, type, pageUtil);
            }
        }.getList(pageUtil);

        if (data.getCode().equals(ResultCode.SUCCESS)) {
            ListResult listResult = (ListResult) data.getData();
            List<ShowCouponInfo> showCouponInfos = (List<ShowCouponInfo>) listResult.getList();
            for (ShowCouponInfo info : showCouponInfos) {
                if (info.getSumLaunchNum() != null) {
                    info.setAvailableNum(info.getNum() - info.getSumLaunchNum());
                }
            }

        }
        return data;
    }

    @Override
    public ResultData getCardCouponById(Long id) {
        return new ResultData(couponMapper.selectCardCouponById(id));
    }

    @Override
    public ResultData updateCardCoupon(CardCoupon cardCoupon) {
        return new ResultData(new UpdateResult(couponMapper.updateByPrimaryKeySelective(cardCoupon))) ;
    }

    @Override
    public ResultData deleteCardCoupon(Long id) {
        if (launchMapper.countCardCouponLaunchByCouponId(id) > 0) {
            throw new BusinessException(ResultCode.EXIST_LAUNCH);
        }
        return new ResultData(new UpdateResult(couponMapper.deleteCardCouponById(id)));
    }


}
