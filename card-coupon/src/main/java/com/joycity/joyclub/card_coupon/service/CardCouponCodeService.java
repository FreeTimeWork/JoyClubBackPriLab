package com.joycity.joyclub.card_coupon.service;

import com.joycity.joyclub.card_coupon.modal.filter.ShowCouponCodeFilter;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;

/**
 * Created by fangchen.chai on 2017/7/13.
 */
public interface CardCouponCodeService {

    void  batchCreateCouponCode(Long launchId);


    /**
     * 防止对缓存有影响
     * 只能通过缓存 CardCouponCodeCache.sendCouponCode() 调用此方法
     * 不允许私自调用
     * @param launchId
     * @param couponType
     * @param thirdPartyShopId
     * @param clientId
     * @return
     */
    Long sendSingleCouponCode(Long launchId, Byte couponType, Long clientId, Long thirdPartyShopId, String couponCode);

    ResultData getListByFilter(Long projectId, ShowCouponCodeFilter filter, PageUtil pageUtil);

    /**
     * 核销卡券
     * 有orderCode，传入orderCode
     */
    ResultData checkCouponCode(Long id, String orderCode);

    /**
     * 卡券改为未使用
     */
    int updateNotUsedCouponCode(Long couponCodeId);

    ResultData orderForWechat(Long projectId, Long subProjectId, Long clientId, Long attrId,Boolean moneyOrPoint);
    ResultData orderForAli(Long projectId, Long subProjectId, Long clientId, Long attrId,Boolean moneyOrPoint);

    /**
     * 取消超过十分钟订单
     */
    void cancelOverTenMinsOrder();


}
