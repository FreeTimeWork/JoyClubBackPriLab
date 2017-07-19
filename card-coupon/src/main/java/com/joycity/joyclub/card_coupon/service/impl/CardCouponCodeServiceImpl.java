package com.joycity.joyclub.card_coupon.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.joycity.joyclub.card_coupon.constant.CouponCodeUseStatus;
import com.joycity.joyclub.card_coupon.constant.CouponType;
import com.joycity.joyclub.card_coupon.mapper.*;
import com.joycity.joyclub.card_coupon.modal.generated.CardCoupon;
import com.joycity.joyclub.card_coupon.modal.generated.CardCouponCode;
import com.joycity.joyclub.card_coupon.modal.generated.CardCouponLaunch;
import com.joycity.joyclub.card_coupon.modal.generated.CardThirdpartyCouponCode;
import com.joycity.joyclub.card_coupon.service.CardCouponCodeService;
import com.joycity.joyclub.commons.constant.LogConst;
import com.joycity.joyclub.commons.constant.RedisKeyConst;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

/**
 *  Created by fangchen.chai on 2017/7/13.
 */
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

    private final BoundHashOperations<String, String, Set<String>> thirdPartyCouponCodeCache;

    @Autowired
    public CardCouponCodeServiceImpl(RedisTemplate redisTemplate) {
        thirdPartyCouponCodeCache = redisTemplate.boundHashOps(RedisKeyConst.THIRD_PARTY_COUPON_CODE);
    }

    @Override
    @Transactional
    public void batchCreateCouponCode(Long launchId) {
        CardCouponLaunch cardCouponLaunch = launchMapper.selectByPrimaryKey(launchId);
        CardCoupon cardCoupon = cardCouponMapper.selectByPrimaryKey(cardCouponLaunch.getCouponId());
        String vipBatch = cardCouponLaunch.getVipBatch();
        List<Long> clientIds =  cardVipBatchMapper.selectClientIdByBatch(vipBatch);
        Date now = new Date();

        if (cardCoupon.getSysgenFlag()) {
            for (int i = 0; i < clientIds.size();) {
                CardCouponCode cardCouponCode = new CardCouponCode();

                if (cardCoupon.getType().equals(CouponType.THIRDPARTY_COUPON)) {
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
                    logger.info("insert error", e);
                    continue;
                }
            }
        } else {
            List<CardThirdpartyCouponCode> thirdpartyCouponCodes =  cardThirdpartyCouponCodeMapper.selectByBatch(vipBatch);
            int index = 0;
            for (int i = 0; i < clientIds.size(); i++) {
                CardCouponCode cardCouponCode = new CardCouponCode();
                cardCouponCode.setBelong(cardCoupon.getThirdpartyShopId());

                //确保取出来的code， 没有被别的线程再次取出
                CardThirdpartyCouponCode cardThirdpartyCouponCode;
                Set<String> codesCache;
                synchronized (this) {
                    codesCache = thirdPartyCouponCodeCache.get(vipBatch);
                    if (codesCache == null) {
                        thirdPartyCouponCodeCache.put(vipBatch, new HashSet<>());
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

    private void makeCouponCode(CardCouponCode cardCouponCode) {
        String code = RandomStringUtils.random(12, "1234567890");
        cardCouponCode.setCode(code);
    }

}
