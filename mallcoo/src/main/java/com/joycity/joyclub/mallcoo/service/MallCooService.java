package com.joycity.joyclub.mallcoo.service;

import java.util.Map;

/**
 * Created by CallMeXYZ on 2017/6/6.
 */
public interface MallCooService {
    /**
     * 获取UserToken
     * @param ticket
     * @return
     */
    public Map getUserToken(Long projectId,String ticket);

    public Map getShops(Long projectId);


  /*  *//**
     * 获取用户信息
     * @param userToken
     * @return
     *//*
    public Map getUser(Long projectId,String userToken) ;*/
}
