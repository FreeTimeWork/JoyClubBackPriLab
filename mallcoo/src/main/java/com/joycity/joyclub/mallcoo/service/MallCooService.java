package com.joycity.joyclub.mallcoo.service;

import com.joycity.joyclub.mallcoo.modal.result.data.OffLineShopInfo;
import com.joycity.joyclub.mallcoo.modal.result.data.UserSimpleInfo;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/6/6.
 */
public interface MallCooService {
    /**
     * 获取UserToken
     * @param ticket 必须是v2接口传过来的，v2的ticket未加密 v1加密的
     * @return
     */
    UserSimpleInfo getUserToken(Long projectId, String ticket);

    /**
     * 获取线下商户列表
     * @param projectId
     * @return
     */
    List<OffLineShopInfo> getShops(Long projectId);

}
