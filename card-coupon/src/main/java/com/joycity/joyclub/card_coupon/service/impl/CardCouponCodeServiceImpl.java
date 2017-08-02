package com.joycity.joyclub.card_coupon.service.impl;

import com.joycity.joyclub.card_coupon.cache.CardCouponCodeCache;
import com.joycity.joyclub.card_coupon.constant.CouponCodeUseStatus;
import com.joycity.joyclub.card_coupon.constant.CouponLaunchType;
import com.joycity.joyclub.card_coupon.constant.CouponType;
import com.joycity.joyclub.card_coupon.mapper.*;
import com.joycity.joyclub.card_coupon.modal.CouponLaunchWithCoupon;
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
import com.joycity.joyclub.commons.modal.base.CreateResult;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.modal.base.UpdateResult;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by fangchen.chai on 2017/7/13.
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

        // TODO: 2017/8/2  cfc 批量和 单独发卡券整合在一起，批量把单独发卡券套起来
        //是否系统制卡
        if (cardCoupon.getSysgenFlag()) {
            batchSendSysgenCouponCode(launchId, cardCoupon, cardCouponLaunch);
        } else {
            batchSendThirdPartyCode(launchId, cardCoupon, cardCouponLaunch);
        }
    }

    @Override
    public ResultData freeReceiveCoupon(Long clientId, Long launchId) {
        CouponLaunchWithCoupon launch = launchMapper.selectLaunchWithCouponById(launchId);
        if (!launch.getType().equals(CouponLaunchType.ONLINE_LAUNCH)) {
            throw new BusinessException(ResultCode.REQUEST_PARAMS_ERROR, "必须为线上发放");
        }
        if (launch.getPayAmount().compareTo(BigDecimal.ZERO) != 0) {
            throw new BusinessException(ResultCode.REQUEST_PARAMS_ERROR, "卡券不能免费领取");
        }
        if (cardCouponCodeMapper.checkCouponCode(launchId, clientId) > 0) {
            throw new BusinessException(ResultCode.REQUEST_PARAMS_ERROR, "已领取");
        }
        //cache发卡
        boolean result = couponCodeCache.sendCouponCode(launchId);
        if (!result) {
            throw new BusinessException(ResultCode.REQUEST_PARAMS_ERROR, "卡券已售罄");
        }
        Long id = sendCouponCode(clientId, launchId, launch.getCouponId());
        return new ResultData(new CreateResult(id));
    }

    @Override
    public Long sendCouponCode(Long clientId, Long launchId, Long couponId) {

        CardCoupon cardCoupon = cardCouponMapper.selectByPrimaryKey(couponId);
        Long id = null;
        if (cardCoupon.getSysgenFlag()) {
            try {
                id = sendSingleCouponCode(launchId, cardCoupon.getType(), clientId, cardCoupon.getThirdpartyShopId(), null);
            } catch (Exception e) {
                //发券逻辑执行失败,cache库存恢复。
                couponCodeCache.changeInventory(launchId, 1);
                throw new RuntimeException(e);
            }
        } else {
            BoundHashOperations<String, String, String> thirdPartyCouponCodeCache = null;
            CardThirdpartyCouponCode cardThirdpartyCouponCode = null;
            PageUtil pageUtil = new PageUtil();
            Long thirdPartyShopId = cardCoupon.getThirdpartyShopId();
            try {
                thirdPartyCouponCodeCache = redisTemplate.boundHashOps(getThirdCodeKey(thirdPartyShopId));
                List<CardThirdpartyCouponCode> thirdPartyCouponCodes = cardThirdpartyCouponCodeMapper.selectByThirdPartyShopId(thirdPartyShopId, pageUtil);
                cardThirdpartyCouponCode = getThirdCode(thirdPartyCouponCodes, 0, thirdPartyCouponCodeCache, pageUtil);
                if (cardThirdpartyCouponCode == null) {
                    couponCodeCache.changeInventory(launchId, 1);
                    thirdPartyCouponCodeCache.delete(cardThirdpartyCouponCode.getCode());
                    throw new BusinessException(ResultCode.DATA_NOT_EXIST);
                }
                id = sendThirdPartyCode(launchId, cardCoupon, clientId, cardThirdpartyCouponCode);
            } catch (Exception e) {
                //发券逻辑执行失败,cache库存恢复。
                couponCodeCache.changeInventory(launchId, 1);
                thirdPartyCouponCodeCache.delete(cardThirdpartyCouponCode.getCode());
                throw new RuntimeException(e);
            }
        }
        return id;
    }

    /**
     * 先执行cache发券，再执行这个；系统自动生成卡号，couponCode = null
     *
     * @param launchId
     * @param couponType
     * @param clientId
     * @param thirdPartyShopId
     * @param couponCode
     * @return
     */
    private Long sendSingleCouponCode(Long launchId, Byte couponType, Long clientId, Long thirdPartyShopId, String couponCode) {

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
                return cardCouponCodeMapper.countSysCardCouponCodeByFilter(projectId, filter);
            }

            @Override
            public List<ShowCouponCodeInfo> selectByFilter() {
                return cardCouponCodeMapper.selectSysCardCouponCodeByFilter(projectId, filter, pageUtil);
            }
        }.getList(pageUtil);
    }

    @Override
    public ResultData checkCouponCode(Long id, String orderCode) {

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

    @Override
    public int updateNotUsedCouponCode(Long couponCodeId) {
        CardCouponCode cardCouponCode = cardCouponCodeMapper.selectByPrimaryKey(couponCodeId);
        cardCouponCode.setId(couponCodeId);
        cardCouponCode.setOrderCode(null);
        cardCouponCode.setUseStatus(CouponCodeUseStatus.NOT_USED);
        cardCouponCode.setUseTime(null);
        return cardCouponCodeMapper.updateByPrimaryKey(cardCouponCode);
    }

    private void batchSendSysgenCouponCode(Long launchId, CardCoupon cardCoupon, CardCouponLaunch cardCouponLaunch) {

        String vipBatch = cardCouponLaunch.getVipBatch();
        List<Long> clientIds = cardVipBatchMapper.selectClientIdByBatch(vipBatch);
        for (int i = 0; i < clientIds.size(); ) {

            boolean result = couponCodeCache.sendCouponCode(launchId);
            if (!result) {
                return;
            } else {
                //执行发券逻辑
                try {
                    sendSingleCouponCode(launchId, cardCoupon.getType(), clientIds.get(i), cardCoupon.getThirdpartyShopId(), null);
                } catch (Exception e) {
                    //发券逻辑执行失败,cache库存恢复。
                    couponCodeCache.changeInventory(launchId, 1);
                    logger.info("insert repeat code error", e);
                    continue;
                }
                //给下一个人发券
                i++;
            }
        }
    }

    /**
     * 从三方卡券code中间表取出数据，给批量会员发卡券。
     *
     * @param launchId
     * @param cardCoupon
     * @param cardCouponLaunch
     */
    private void batchSendThirdPartyCode(Long launchId, CardCoupon cardCoupon, CardCouponLaunch cardCouponLaunch) {

        Long thirdPartyShopId = cardCoupon.getThirdpartyShopId();
        String vipBatch = cardCouponLaunch.getVipBatch();
        List<Long> clientIds = cardVipBatchMapper.selectClientIdByBatch(vipBatch);
        BoundHashOperations<String, String, String> thirdPartyCouponCodeCache = redisTemplate.boundHashOps(getThirdCodeKey(thirdPartyShopId));

        PageUtil pageUtil = new PageUtil(0, clientIds.size());
        List<CardThirdpartyCouponCode> thirdPartyCouponCodes = cardThirdpartyCouponCodeMapper.selectByThirdPartyShopId(thirdPartyShopId, pageUtil);
        int index = 0;
        for (int i = 0; i < clientIds.size(); i++) {
            boolean result = couponCodeCache.sendCouponCode(launchId);
            if (!result) {
                return;
            }
            CardThirdpartyCouponCode cardThirdpartyCouponCode = getThirdCode(thirdPartyCouponCodes, index, thirdPartyCouponCodeCache, pageUtil);
            //第三方没有卡券了，直接返回
            if (cardThirdpartyCouponCode == null) {
                couponCodeCache.changeInventory(launchId, 1);
                thirdPartyCouponCodeCache.delete(cardThirdpartyCouponCode.getCode());
                return;
            }

            try {
                //执行发券逻辑
                sendThirdPartyCode(launchId, cardCoupon, clientIds.get(i), cardThirdpartyCouponCode);
            } catch (Exception e) {
                //发券逻辑执行失败,cache库存恢复。
                couponCodeCache.changeInventory(launchId, 1);
                thirdPartyCouponCodeCache.delete(cardThirdpartyCouponCode.getCode());
                throw new RuntimeException(e);
            }
        }
    }


    /**
     * 得到第三方的code,如果没有，返回null
     *
     * @param thirdPartyCouponCodes
     * @param index
     * @param thirdPartyCouponCodeCache
     * @param pageUtil
     * @return
     */
    private CardThirdpartyCouponCode getThirdCode(List<CardThirdpartyCouponCode> thirdPartyCouponCodes,
                                                  int index,
                                                  BoundHashOperations<String, String, String> thirdPartyCouponCodeCache,
                                                  PageUtil pageUtil) {

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
                        thirdPartyCouponCodes = cardThirdpartyCouponCodeMapper.selectByThirdPartyShopId(cardThirdpartyCouponCode.getThirdpartyShopId(), pageUtil);
                        index = 0;
                        if (CollectionUtils.isEmpty(thirdPartyCouponCodes)) {
                            cardThirdpartyCouponCode = null;
                        }

                    }
                }
            }

        } else {
            cardThirdpartyCouponCode = null;
        }
        return cardThirdpartyCouponCode;
    }


    public Long sendThirdPartyCode(Long launchId,
                                   CardCoupon cardCoupon,
                                   Long clientId,
                                   CardThirdpartyCouponCode cardThirdpartyCouponCode) {

        CardCouponCode cardCouponCode = new CardCouponCode();
        Long thirdPartyShopId = cardCoupon.getThirdpartyShopId();
        cardCouponCode.setBelong(thirdPartyShopId);
        Long id = sendSingleCouponCode(launchId, cardCoupon.getType(), clientId, thirdPartyShopId, cardThirdpartyCouponCode.getCode());
        cardThirdpartyCouponCodeMapper.deleteById(cardThirdpartyCouponCode.getId());
        return id;
    }

    private void makeCouponCode(CardCouponCode cardCouponCode) {
        String code = RandomStringUtils.random(12, "1234567890");
        cardCouponCode.setCode(code);
    }

    private String getThirdCodeKey(Long thirdPartyShopId) {
        return THIRD_PARTY_COUPON_CODE + "_" + thirdPartyShopId;
    }

}
