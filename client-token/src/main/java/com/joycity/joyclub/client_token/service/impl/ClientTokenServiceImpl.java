package com.joycity.joyclub.client_token.service.impl;

import java.util.Calendar;
import java.util.Map;
import java.util.UUID;

import com.joycity.joyclub.client_token.service.ClientTokenService;
import com.joycity.joyclub.commons.constant.LogConst;
import com.joycity.joyclub.commons.constant.RedisKeyConst;
import com.joycity.joyclub.commons.constant.ResultCode;
import com.joycity.joyclub.commons.exception.BusinessException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;
// TODO: 2017/6/29 用户量过大时应该采取定时任务删除过期token

/**
 * 目前的存储逻辑是以token-id,token-expire分别存储在hash
 * 获取时先查token-expire的有效期，如果过期则删除token,抛出异常
 * Created by CallMeXYZ on 2017/6/29.
 */
@Service
public class ClientTokenServiceImpl implements ClientTokenService {
    private final String KEY_SUFFIX_CLIENT_TOKEN_EXPIRE = "_EXPIRE";
    /**
     * 过时的时间，毫秒
     * 目前是30天过时
     */
    private final Long EXPIRE_PERIOD = 1000 * 3600 * 24 * 30L;
    private Log taskLogger = LogFactory.getLog(LogConst.LOG_TASK);
    private final String CLIENT_TOKEN = RedisKeyConst.CLIENT_TOKEN.getName();
    private final String CLIENT_TOKEN_EXPIRE = RedisKeyConst.CLIENT_TOKEN_EXPIRE.getName();
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public Long getId(String token) {

        return getId(token, false);
    }

    @Override
    public Long getIdOrThrow(String token) {
        return getId(token, true);
    }

    @Override
    public String setToken(Long clientId) {
        String token = createToken();
        BoundHashOperations<String, String, String> tokenHO = redisTemplate.boundHashOps(CLIENT_TOKEN);
        BoundHashOperations<String, String, String> expireHO = redisTemplate.boundHashOps(CLIENT_TOKEN_EXPIRE);
        tokenHO.put(token, String.valueOf(clientId));
        expireHO.put(getExpireKey(token), getExpireTime());
        return token;
    }

    @Override
    public void removeToken(String token) {
        BoundHashOperations<String, String, String> tokenHO = redisTemplate.boundHashOps(CLIENT_TOKEN);
        BoundHashOperations<String, String, String> expireHO = redisTemplate.boundHashOps(CLIENT_TOKEN_EXPIRE);
        tokenHO.delete(token);
        expireHO.delete(getExpireKey(token));
    }

    @Override
    public boolean checkToken(String token) {
        return getId(token) != null;
    }

    @Override
    public void clearExpireToken() {
        BoundHashOperations<String, String, String> tokenHO = redisTemplate.boundHashOps(CLIENT_TOKEN);
        Cursor<Map.Entry<String, String>> cursor = tokenHO.scan(new ScanOptions.ScanOptionsBuilder().build());
        Long startTime = System.currentTimeMillis();
        int clearNum = 0;
        boolean singleResult;
        while (cursor.hasNext()) {
            singleResult = !checkToken(cursor.next().getKey());
            if (singleResult) {
                clearNum++;
            }
        }
        if (clearNum > 0) {
            taskLogger.info("清除过期clientToken " + clearNum + " 条，耗时" + (System.currentTimeMillis() - startTime) + "毫秒");

        }
    }

    private Long getId(String token, Boolean ifThrow) {
        BoundHashOperations<String, String, String> tokenHO = redisTemplate.boundHashOps(CLIENT_TOKEN);
        BoundHashOperations<String, String, String> expireHO = redisTemplate.boundHashOps(CLIENT_TOKEN_EXPIRE);
        String expireKey = getExpireKey(token);
        String expireAt = expireHO.get(expireKey);
        String clientId = tokenHO.get(token);
        if (expireAt == null || clientId == null) {
            if (ifThrow) {
                throw new BusinessException(ResultCode.CLIENT_TOKEN_NULL);
            } else {
                return null;
            }
        }
        if (System.currentTimeMillis() > Long.parseLong(expireAt)) {
            tokenHO.delete(token);
            expireHO.delete(expireKey);
            if (ifThrow) {
                throw new BusinessException(ResultCode.CLIENT_TOKEN_EXPIRE);
            } else {
                return null;
            }

        }
        return Long.parseLong(clientId);
    }

    private String getExpireKey(String token) {
        return token + KEY_SUFFIX_CLIENT_TOKEN_EXPIRE;
    }

    private String getExpireTime() {
        return String.valueOf(System.currentTimeMillis() + EXPIRE_PERIOD);
    }

    private String createToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
