package com.joycity.joyclub.mallcoo.service;

import java.util.Map;

/**
 * Created by CallMeXYZ on 2017/6/6.
 */
public interface MallCooService {
    /**
     * 获取UserToken
     * @param ticket 必须是v2接口传过来的，v2的ticket未加密 v1加密的
     * @return
     */
    public Map getUserToken(Long projectId,String ticket);
  /*  *//**
     * 获取用户信息
     * @param userToken
     * @return
     *//*
    public Map getUser(Long projectId,String userToken) ;*/
}
