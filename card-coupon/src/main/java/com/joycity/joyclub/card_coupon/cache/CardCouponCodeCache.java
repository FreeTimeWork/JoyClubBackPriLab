package com.joycity.joyclub.card_coupon.cache;

/**
 * Created by fangchen.chai on 2017/7/31
 */
public interface CardCouponCodeCache {


    /**
     *  cache层发券
     *  可以发券 返回 true。
     *  不让发卡券 返回 false。
     * @param launchId
     * @return
     */
    public boolean sendCouponCode(Long launchId);

    /**
     * 添加inventory缓存
     */
    public void addInventory(Long launchId, int launchNum);

    /**
     * 得到库存
     */
    public int getInventory(Long launchId);

    public void changeInventory(Long launchId, int num);
}
