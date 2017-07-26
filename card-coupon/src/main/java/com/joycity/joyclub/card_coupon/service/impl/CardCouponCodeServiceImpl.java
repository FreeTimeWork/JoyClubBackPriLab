package com.joycity.joyclub.card_coupon.service.impl;

import com.joycity.joyclub.card_coupon.constant.CouponCodeUseStatus;
import com.joycity.joyclub.card_coupon.constant.CouponType;
import com.joycity.joyclub.card_coupon.mapper.*;
import com.joycity.joyclub.card_coupon.modal.ShowCouponCodeInfo;
import com.joycity.joyclub.card_coupon.modal.filter.ShowCouponCodeFilter;
import com.joycity.joyclub.card_coupon.modal.generated.CardCoupon;
import com.joycity.joyclub.card_coupon.modal.generated.CardCouponCode;
import com.joycity.joyclub.card_coupon.modal.generated.CardCouponLaunch;
import com.joycity.joyclub.card_coupon.modal.generated.CardThirdpartyCouponCode;
import com.joycity.joyclub.card_coupon.service.CardCouponCodeService;
import com.joycity.joyclub.commons.AbstractGetListData;
import com.joycity.joyclub.commons.constant.LogConst;
import com.joycity.joyclub.commons.constant.RedisKeyConst;
import com.joycity.joyclub.commons.constant.ResultCode;
import com.joycity.joyclub.commons.exception.BusinessException;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.modal.base.UpdateResult;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *  Created by fangchen.chai on 2017/7/13.
 */
@Service
public class CardCouponCodeServiceImpl implements CardCouponCodeService {

    private Log logger = LogFactory.getLog(LogConst.LOG_TASK);

    @Autowired
    private CardCouponLaunchMapper launchMapper;
    @Autowired
    private CardCouponCodeMapper cardCouponCodeMapper;
    @Autowired
    private CardCouponMapper cardCouponMapper;
    @Autowired
    private CardVipBatchMapper cardVipBatchMapper;
    @Autowired
    private CardThirdpartyCouponCodeMapper cardThirdpartyCouponCodeMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    @Transactional
    public void batchCreateCouponCode(Long launchId) {
        CardCouponLaunch cardCouponLaunch = launchMapper.selectByPrimaryKey(launchId);
        CardCoupon cardCoupon = cardCouponMapper.selectByPrimaryKey(cardCouponLaunch.getCouponId());
        String thirdPartyCodeBatch = cardCoupon.getBatch();
        String vipBatch = cardCouponLaunch.getVipBatch();
        List<Long> clientIds =  cardVipBatchMapper.selectClientIdByBatch(vipBatch);
        Date now = new Date();

        if (cardCoupon.getSysgenFlag()) {
            for (int i = 0; i < clientIds.size();) {
                CardCouponCode cardCouponCode = new CardCouponCode();

                if (cardCoupon.getType().equals(CouponType.THIRD_PARTY_COUPON)) {
                    cardCouponCode.setBelong(cardCoupon.getThirdpartyShopId());
                } else {
                    cardCouponCode.setBelong(-1L);
                }
                makeCouponCode(cardCouponCode);

                cardCouponCode.setLaunchId(launchId);
                cardCouponCode.setClientId(clientIds.get(i));
                cardCouponCode.setReceiveTime(now);
                cardCouponCode.setUseStatus(CouponCodeUseStatus.NOT_USED);

                try {
                    cardCouponCodeMapper.insertSelective(cardCouponCode);
                    i++;
                } catch (Exception e) {
                    logger.info("insert repeat code error", e);
                    continue;
                }
            }
        } else {
            BoundHashOperations<String, String, Set<String>> thirdPartyCouponCodeCache = redisTemplate.boundHashOps(RedisKeyConst.THIRD_PARTY_COUPON_CODE.getName());
            List<CardThirdpartyCouponCode> thirdpartyCouponCodes =  cardThirdpartyCouponCodeMapper.selectByBatch(thirdPartyCodeBatch);
            int index = 0;
            for (int i = 0; i < clientIds.size(); i++) {
                CardCouponCode cardCouponCode = new CardCouponCode();
                cardCouponCode.setBelong(cardCoupon.getThirdpartyShopId());

                //确保取出来的code， 没有被别的线程再次取出
                CardThirdpartyCouponCode cardThirdpartyCouponCode;
                Set<String> codesCache;
                synchronized (this) {
                    codesCache = thirdPartyCouponCodeCache.get(thirdPartyCodeBatch);
                    if (codesCache == null) {
                        thirdPartyCouponCodeCache.put(thirdPartyCodeBatch, new HashSet<>());
                    }
                    do {
                        cardThirdpartyCouponCode = thirdpartyCouponCodes.get(index++);
                    } while (codesCache.contains(cardThirdpartyCouponCode.getCode()));
                    codesCache.add(cardThirdpartyCouponCode.getCode());
                }

                cardCouponCode.setCode(cardThirdpartyCouponCode.getCode());

                cardCouponCode.setLaunchId(launchId);
                cardCouponCode.setClientId(clientIds.get(i));
                cardCouponCode.setReceiveTime(now);
                cardCouponCode.setUseStatus(CouponCodeUseStatus.NOT_USED);

                try {
                    cardCouponCodeMapper.insertSelective(cardCouponCode);
                    cardThirdpartyCouponCodeMapper.deleteById(cardThirdpartyCouponCode.getId());
                } catch (Exception e) {
                    //持久化失败，把code从cache中取出
                    codesCache.remove(cardThirdpartyCouponCode.getCode());
                    throw new RuntimeException(e);
                }

            }
        }
    }

    @Override
    public ResultData getListByFilter(Long projectId, ShowCouponCodeFilter filter, PageUtil pageUtil) {

        return new AbstractGetListData<ShowCouponCodeInfo>() {
            @Override
            public Long countByFilter() {
                return cardCouponCodeMapper.countCardCouponCodeByFilter(projectId, filter);
            }

            @Override
            public List<ShowCouponCodeInfo> selectByFilter() {
                return cardCouponCodeMapper.selectCardCouponCodeByFilter(projectId, filter, pageUtil);
            }
        }.getList(pageUtil);
    }

    @Override
    public ResultData checkCouponCode(Long id, Long posSaleDetailId) {
        CardCouponCode cardCouponCodeDb = cardCouponCodeMapper.selectByPrimaryKey(id);
        String errorText = null;
        if (cardCouponCodeDb == null) {
            errorText = "该卡券号不存在";
        } else {
            if (!cardCouponCodeDb.getUseStatus().equals(CouponCodeUseStatus.NOT_USED)) {
                errorText = "该卡券号已使用或已作废";
            }
        }

        if (errorText != null) {
            throw new BusinessException(ResultCode.COUPON_CHECK_ERROR, errorText);
        } else {
            CardCouponCode cardCouponCode = new CardCouponCode();
            cardCouponCode.setId(id);
            cardCouponCode.setUseStatus(CouponCodeUseStatus.USED);
            cardCouponCode.setUseTime(new Date());
            if (posSaleDetailId != null) {
                cardCouponCode.setPosSaleDetailId(posSaleDetailId);
            }
            int num = cardCouponCodeMapper.updateByPrimaryKeySelective(cardCouponCode);
            return new ResultData(new UpdateResult(num));
        }
    }

    @Override
    public int updateNotUsedCouponCode(Long couponCodeId) {
        CardCouponCode cardCouponCode = new CardCouponCode();
        cardCouponCode.setId(couponCodeId);
        cardCouponCode.setPosSaleDetailId(null);
        cardCouponCode.setUseStatus(CouponCodeUseStatus.NOT_USED);
        cardCouponCode.setUseTime(null);
        return cardCouponCodeMapper.updateByPrimaryKeySelective(cardCouponCode);
    }

    private void makeCouponCode(CardCouponCode cardCouponCode) {
        String code = RandomStringUtils.random(12, "1234567890");
        cardCouponCode.setCode(code);
    }


}
