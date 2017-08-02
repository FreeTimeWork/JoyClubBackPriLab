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
    private RedisTemplate redisTemplate;

    public boolean sendCouponCode(Long launchId) {
        BoundHashOperations<String, String, Integer> inventoryHO = redisTemplate.boundHashOps(INVENTORY);

        //没有库存直接返回false
        if (inventoryHO.get(getKey(launchId)) <= 0) {
            return false;
        }

        // cache 减库存,并判断返回值。小于零，返回false，并把cache 库存设为0;
        if (inventoryHO.increment(getKey(launchId), -1) < 0) {
            inventoryHO.put(getKey(launchId), 0);
            return false;
        }

        return true;
    }

    @Override
    public void addInventory(Long launchId, int launchNum) {
        BoundHashOperations<String, String, Integer> inventoryHO = redisTemplate.boundHashOps(INVENTORY);

        if (inventoryHO.get(getKey(launchId)) == null) {
            inventoryHO.put(getKey(launchId), launchNum);
        }
    }

    @Override
    public int getInventory(Long launchId) {
        BoundHashOperations<String, String, Integer> inventoryHO = redisTemplate.boundHashOps(INVENTORY);
        if (inventoryHO.get(getKey(launchId)) == null) {
            return 0;
        }
        return inventoryHO.get(getKey(launchId));
    }

    @Override
    public void changeInventory(Long launchId, int num) {
        BoundHashOperations<String, String, Integer> inventoryHO = redisTemplate.boundHashOps(INVENTORY);
        inventoryHO.increment(getKey(launchId), num);
    }


    private String getKey(Long launchId) {
        return INVENTORY + "_" + launchId;
    }
}
