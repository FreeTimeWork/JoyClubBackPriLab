package com.joycity.joyclub.client_token.service;

/**
 * Created by CallMeXYZ on 2017/6/29.
 */
public interface ClientTokenService {
    Long getId(String token);

    /**
     * 与getId类似，但是不存在会抛出异常
     *
     * @param token
     * @return
     */
    Long getIdOrThrow(String token);

    /**
     * @param clientId
     * @return to
     */
    String setToken(Long clientId);

    void removeToken(String token);

    boolean checkToken(String token);

    /**
     * 耗时任务，主要应该放在定时任务
     */
    void clearExpireToken();
}
