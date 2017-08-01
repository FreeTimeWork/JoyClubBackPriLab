package com.joycity.joyclub.card_coupon.cache.impl;

import com.joycity.joyclub.card_coupon.cache.CardCouponCodeCache;
import com.joycity.joyclub.card_coupon.mapper.CardCouponLaunchMapper;
import com.joycity.joyclub.card_coupon.service.CardCouponCodeService;
import com.joycity.joyclub.commons.constant.RedisKeyConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by fangchen.chai on 2017/7/31
 */
@Service
public class CardCouponCodeCacheImpl implements CardCouponCodeCache {

    private final String INVENTORY = RedisKeyConst.INVENTORY.getName();

    @Autowired
    private CardCouponLaunchMapper launchMapper;
    @Autowired
    private CardCouponCodeService couponCodeService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean sendCouponCode(Long launchId, Byte couponType,Long clientId,Long thirdPartyShopId, String couponCode) {
        BoundHashOperations<String, String, Integer> inventoryHO = redisTemplate.boundHashOps(INVENTORY);

        if (inventoryHO.get(getKey(launchId)) == null) {
            int launchNum = launchMapper.selectInventoryNumById(launchId);
            inventoryHO.put(getKey(launchId), launchNum);
        }

        //没有库存
        if (inventoryHO.get(getKey(launchId)) <= 0) {
            return false;
        }

        // cache 减库存,并判断返回值。小于零，返回false，并把cache 库存设为0;
        if (inventoryHO.increment(getKey(launchId), -1) < 0) {
            inventoryHO.put(getKey(launchId), 0);
            return false;
        }

        //执行发券逻辑
        try {
            couponCodeService.sendSingleCouponCode(launchId, couponType, clientId,thirdPartyShopId , couponCode);
        } catch (Exception e) {
            //发券逻辑执行失败,库存恢复。
            inventoryHO.increment(getKey(launchId), 1);
            if (e instanceof DuplicateKeyException) {
                //捕获唯一约束异常后再抛出
                throw new DuplicateKeyException("已经存在一个code",e);
            }
            return false;
        }

        return true;
    }



    private String getKey(Long launchId) {
        return INVENTORY + "_" + launchId;
    }
}
