package com.joycity.joyclub.card_coupon.service;

import com.joycity.joyclub.card_coupon.modal.ClientCouponBag;
import com.joycity.joyclub.card_coupon.modal.filter.ShowCouponCodeFilter;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;

import java.util.List;

/**
 * Created by fangchen.chai on 2017/7/13.
 */
public interface CardCouponCodeService {

    void batchCreateCouponCode(Long launchId);

    /**
     * 免费或积分领取卡券
     *
     * @param clientId
     * @param launchId
     * @return
     */
    ResultData freeOrPointReceiveCoupon(Long clientId, Long launchId);

    /**
     * 纯发卡业务，里面没有cache发卡。
     * 注：调用此方法，必须先前通过cache发卡，因为方法内集成清除cache库存的操作
     *
     * @param clientId
     * @param launchId
     */
    Long sendCouponCode(Long clientId, Long launchId, Long couponId,Long shopId);

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


    /**
     * 用户券包，查询用户当前可用券
     */
    List<ClientCouponBag> getListCurrentClientCouponUsable(Long clientId);

    /**
     * 获得用户当前可用券
     */
    ResultData getAvailableCardCouponsById(Long projectId,Long clientId);

    /**
     * 猫酷页面，获得用户当前可用券
     * crmId, ticket有一个可以为null
     * @return
     */
    ResultData getAvailableCardCouponsByMallcooTicket(Long projectId,String ticket, String vipCode);

    /**
     * 券包页点击系统券进入查看详情
     * @param id cardCouponCode id
     */
    ResultData getCouponInfoByCodeId(Long id,Long clientId);
    /**
     * 券包页点击猫酷券进入查看详情
     * @param code 猫酷券码
     */
    ResultData getMallcooCouponInfoByCode(Long projectId,String code);

}
