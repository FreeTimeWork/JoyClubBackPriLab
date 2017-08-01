package com.joycity.joyclub.card_coupon.service.impl;

import com.joycity.joyclub.card_coupon.cache.CardCouponCodeCache;
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
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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
    private final String THIRD_PARTY_COUPON_CODE = RedisKeyConst.THIRD_PARTY_COUPON_CODE.getName();


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
    private CardCouponCodeCache couponCodeCache;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    @Transactional
    public void batchCreateCouponCode(Long launchId) {
        CardCouponLaunch cardCouponLaunch = launchMapper.selectByPrimaryKey(launchId);
        CardCoupon cardCoupon = cardCouponMapper.selectByPrimaryKey(cardCouponLaunch.getCouponId());

        //是否系统制卡
        if (cardCoupon.getSysgenFlag()) {
            String vipBatch = cardCouponLaunch.getVipBatch();
            List<Long> clientIds =  cardVipBatchMapper.selectClientIdByBatch(vipBatch);
            for (int i = 0; i < clientIds.size();) {

                try {
                    boolean result = couponCodeCache.sendCouponCode(launchId, cardCoupon.getType(), clientIds.get(i), cardCoupon.getThirdpartyShopId(), null);

                    if (!result) {
                        return;
                    }
                    i++;
                } catch (DuplicateKeyException e) {
                    logger.info("insert repeat code error", e);
                    continue;
                }
            }

        } else {
            sendThirdPartyCode(launchId, cardCoupon, cardCouponLaunch);
        }
    }

    /**
     *  只能通过缓存 CardCouponCodeCache.sendCouponCode() 调用此方法
     *  不允许私自调用
     * @param launchId
     * @param couponType
     * @param thirdPartyShopId
     * @param clientId
     * @return
     */

    @Override
    public Long sendSingleCouponCode(Long launchId, Byte couponType, Long clientId,Long thirdPartyShopId, String couponCode) {

        CardCouponCode cardCouponCode = new CardCouponCode();

        if (couponType.equals(CouponType.THIRD_PARTY_COUPON)) {
            cardCouponCode.setBelong(thirdPartyShopId);
        } else {
            cardCouponCode.setBelong(-1L);
        }
        if (couponCode == null) {
            makeCouponCode(cardCouponCode);
        } else {
            cardCouponCode.setCode(couponCode);
        }
        cardCouponCode.setLaunchId(launchId);
        cardCouponCode.setClientId(clientId);
        cardCouponCode.setReceiveTime(new Date());
        cardCouponCode.setUseStatus(CouponCodeUseStatus.NOT_USED);
        cardCouponCodeMapper.insertSelective(cardCouponCode);
        return cardCouponCode.getId();
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
    public ResultData checkCouponCode(Long id, String orderCode) {
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
            if (orderCode != null) {
                cardCouponCode.setOrderCode(orderCode);
            }
            int num = cardCouponCodeMapper.updateByPrimaryKeySelective(cardCouponCode);
            return new ResultData(new UpdateResult(num));
        }
    }

    @Override
    public int updateNotUsedCouponCode(Long couponCodeId) {
        CardCouponCode cardCouponCode = cardCouponCodeMapper.selectByPrimaryKey(couponCodeId);
        cardCouponCode.setId(couponCodeId);
        cardCouponCode.setOrderCode(null);
        cardCouponCode.setUseStatus(CouponCodeUseStatus.NOT_USED);
        cardCouponCode.setUseTime(null);
        return cardCouponCodeMapper.updateByPrimaryKey(cardCouponCode);
    }

    /**
     * 从三方卡券code中间表取出数据，给批量会员发卡券。
     * @param launchId
     * @param cardCoupon
     * @param cardCouponLaunch
     */
    private void sendThirdPartyCode(Long launchId, CardCoupon cardCoupon, CardCouponLaunch cardCouponLaunch) {

        Long thirdPartyShopId = cardCoupon.getThirdpartyShopId();
        String vipBatch = cardCouponLaunch.getVipBatch();
        List<Long> clientIds = cardVipBatchMapper.selectClientIdByBatch(vipBatch);
        BoundHashOperations<String, String, String> thirdPartyCouponCodeCache = redisTemplate.boundHashOps(getThirdCodeKey(thirdPartyShopId));

        PageUtil pageUtil = new PageUtil(0, clientIds.size());
        List<CardThirdpartyCouponCode> thirdPartyCouponCodes = cardThirdpartyCouponCodeMapper.selectByThirdPartyShopId(thirdPartyShopId, pageUtil);
        int index = 0;
        for (int i = 0; i < clientIds.size(); i++) {
            CardCouponCode cardCouponCode = new CardCouponCode();
            cardCouponCode.setBelong(cardCoupon.getThirdpartyShopId());

            CardThirdpartyCouponCode cardThirdpartyCouponCode = null;
            String code = null;
            if (CollectionUtils.isNotEmpty(thirdPartyCouponCodes)) {
                cardThirdpartyCouponCode = thirdPartyCouponCodes.get(index++);
                code = cardThirdpartyCouponCode.getCode();
                // code缓存没有，说明可用，存入缓存，再使用code。
                // code缓存存在，说明不可用，取下一个
                while (true) {
                    if (thirdPartyCouponCodeCache.get(code) == null) {
                        thirdPartyCouponCodeCache.put(code, code);
                        break;
                    } else {
                        // 取一次codes不够，再取一次。如果取null，直接返回
                        if (index < thirdPartyCouponCodes.size()) {
                            cardThirdpartyCouponCode = thirdPartyCouponCodes.get(index++);
                        } else {
                            pageUtil.setPage(pageUtil.getPage() + 1);
                            thirdPartyCouponCodes = cardThirdpartyCouponCodeMapper.selectByThirdPartyShopId(thirdPartyShopId, pageUtil);
                            index = 0;
                            if (CollectionUtils.isEmpty(thirdPartyCouponCodes)) {
                                return;
                            }

                        }
                    }
                }

            } else {
                return;
            }

            boolean result = couponCodeCache.sendCouponCode(launchId, cardCoupon.getType(), clientIds.get(i), thirdPartyShopId, code);
            //发卡成功，删除第三方卡券code，失败，缓存取出已经存在的code
            if (result) {
                cardThirdpartyCouponCodeMapper.deleteById(cardThirdpartyCouponCode.getId());
            } else {
                thirdPartyCouponCodeCache.delete(code);
            }
        }
    }

    private void makeCouponCode(CardCouponCode cardCouponCode) {
        String code = RandomStringUtils.random(12, "1234567890");
        cardCouponCode.setCode(code);
    }

    private String getThirdCodeKey(Long thirdPartyShopId) {
        return THIRD_PARTY_COUPON_CODE + "_" + thirdPartyShopId;
    }

}
