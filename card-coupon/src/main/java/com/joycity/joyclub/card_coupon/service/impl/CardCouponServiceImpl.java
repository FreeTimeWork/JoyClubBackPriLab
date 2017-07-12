package com.joycity.joyclub.card_coupon.service.impl;

import java.util.List;

import com.joycity.joyclub.card_coupon.mapper.CardCouponMapper;
import com.joycity.joyclub.card_coupon.mapper.CardCouponStoreScopeMapper;
import com.joycity.joyclub.card_coupon.mapper.CardCouponVipScopeMapper;
import com.joycity.joyclub.card_coupon.modal.CreateCouponInfo;
import com.joycity.joyclub.card_coupon.modal.ShowCouponInfo;
import com.joycity.joyclub.card_coupon.modal.generated.CardCoupon;
import com.joycity.joyclub.card_coupon.modal.generated.CardCouponLaunch;
import com.joycity.joyclub.card_coupon.modal.generated.CardCouponStoreScope;
import com.joycity.joyclub.card_coupon.modal.generated.CardCouponVipScope;
import com.joycity.joyclub.card_coupon.service.CardCouponService;
import com.joycity.joyclub.commons.AbstractGetListData;
import com.joycity.joyclub.commons.constant.ResultCode;
import com.joycity.joyclub.commons.modal.base.CreateResult;
import com.joycity.joyclub.commons.modal.base.ListResult;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.modal.base.UpdateResult;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.apache.commons.collections.CollectionUtils;
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
                if (CollectionUtils.isNotEmpty(info.getLaunchs())) {
                    int launchNumSum = 0;
                    for (CardCouponLaunch launch : info.getLaunchs()) {
                        launchNumSum += launch.getLaunchNum();
                    }

                    info.setAvailableNum(info.getNum() - launchNumSum);
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
        return new ResultData(new UpdateResult(couponMapper.deleteCardCouponById(id)));
    }

}
