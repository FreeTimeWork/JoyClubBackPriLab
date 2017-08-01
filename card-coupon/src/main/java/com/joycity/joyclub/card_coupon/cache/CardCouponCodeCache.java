package com.joycity.joyclub.card_coupon.cache;

/**
 * Created by fangchen.chai on 2017/7/31
 */
public interface CardCouponCodeCache {

    /**
     *  cache层发卡券的入口，
     *  如果制造的卡券重复，抛出DuplicateKeyException异常，
     *  插入成功 返回 true。
     *  不让发卡券 返回 false。
     * @param launchId
     * @param couponType
     * @param thirdPartyShopId
     * @param clientId
     * @return
     */
    public boolean sendCouponCode(Long launchId, Byte couponType, Long clientId, Long thirdPartyShopId, String couponCode);
}
