package com.joycity.joyclub.mallcoo.service;

import com.joycity.joyclub.mallcoo.modal.result.data.*;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/6/6.
 */
public interface MallCooService {
    /**
     * 获取UserToken,简单信息
     *
     * @param ticket 必须是v2接口传过来的，v2的ticket未加密 v1加密的
     * @return
     */
    UserSimpleInfo getUserToken(Long projectId, String ticket);

    /**
     * 获取用户高级信息，主要是包含会员号
     */
    UserAdvancedInfo getUserAdvancedInfoByTicket(Long projectId, String ticket);

    UserAdvancedInfo getUserAdvancedInfoById(Long projectId, String openUserId);

    /**
     * 获取线下商户列表
     *
     * @param projectId
     * @return
     */
    List<OffLineShopInfo> getShops(Long projectId);

    /**
     * @param crmId 会员号
     */
    CouponsResult getCoupons(Long projectId, String crmId);

    /**
     * @param code 券号 VCode
     */
    CouponInfo getCouponInfo(Long projectId, String code);

}
