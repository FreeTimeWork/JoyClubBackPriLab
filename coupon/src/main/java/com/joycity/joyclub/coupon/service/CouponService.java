package com.joycity.joyclub.coupon.service;

import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;
import com.joycity.joyclub.coupon.exception.CouponException;
import com.joycity.joyclub.coupon.modal.CouponForClient;
import com.joycity.joyclub.coupon.modal.generated.Coupon;
import com.joycity.joyclub.coupon.modal.generated.CouponCardType;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/4/12.
 * 卡券的业务逻辑层
 */
public interface CouponService {
    Coupon getById(Long id);

    List<CouponCardType> getCardTypes(Long couponId);

    /**
     * 给管理端用的
     *
     * @param projectId
     * @param type      可空
     * @param name      可空
     * @param useFlag   可空
     * @param pageUtil
     * @return
     */
    ResultData getCouponList(Long projectId, Integer type, String name, Boolean useFlag, PageUtil pageUtil);

    /**\
     * @param type     卡券类型
     * @param code     某个卡券号码（严格相等）
     * @param phone    用户手机号
     * @param name     卡券名称相似
     * @param pageUtil
     * @return
     */
    ResultData getCouponCodeList(String type, String code, String phone, String name, PageUtil pageUtil);

    /**
     * 其实coupon对象的id已经被赋值
     *
     * @param coupon
     * @param cardTypes 允许领取的卡类型
     * @return
     */
    ResultData insert(Coupon coupon, String[] cardTypes);


    /**
     * 根据id修改info
     *
     * @param id
     * @param info
     * @return 受影响的数量
     */
    ResultData updateInfo(Long id, String info, String[] cardTypes);

    /**
     * 卡券开始使用
     *
     * @param id
     * @return 受影响的数量
     */
    ResultData startUse(Long id);

    /**
     * 根据id修改info
     *
     * @param id
     * @return 受影响的数量
     */
    ResultData forbid(Long id);

    /**
     * 为某个卡券增加号码，并更新总数量
     * throws CouponException 该卡券不存在
     *
     * @param id
     * @param codes
     * @return
     */
    ResultData addCodes(Long id, List<String> codes);

    /**
     * 核销

     */
    ResultData checkCode(Long couponId,Long managerId, String code);

    ResultData getSimpleCouponList();


    ///////////下面是手机端/////////////////////////////////////////////////////////////////////////////////

    /**
     * 手机端的获取的卡券list
     *
     * @return
     */
    List<CouponForClient> getCouponListForFront(Long projectId, Long clientId, String cardType, PageUtil pageUtil);

    /**
     * 对于未登录用户
     */
    List<CouponForClient> getCouponListForFront(Long projectId, PageUtil pageUtil);

    /**
     * 用户查看我的卡券
     * @param clientId
     * @param pageUtil
     * @return
     */
    List<CouponForClient> getCouponListForFrontClient( Long clientId,  PageUtil pageUtil);

    /**
     * @return 领券需要的积分，业务需要根据这个修改用户积分
     */
    Integer clientReceiveCoupon(Long couponId, Long clientId) ;
}
