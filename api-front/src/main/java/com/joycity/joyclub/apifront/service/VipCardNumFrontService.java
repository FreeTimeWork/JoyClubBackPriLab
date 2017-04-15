package com.joycity.joyclub.apifront.service;

/**
 * Created by CallMeXYZ on 2017/4/10.
 */
public interface VipCardNumFrontService {

    /**
     * 为某一类型的会员卡找出最小可用的，置为已使用
     * 会抛出 VIP_CARD_NUM_NO_AVAILABLE
     *
     * @param type
     * @param projectId
     * @return
     */
    Long useVipCard(Long projectId, String type);

    /**
     * 为电子卡找出最小可用的，置为已使用
     *
     * @param projectId
     * @return
     */
    Long useDigitalVipCard(Long projectId);
}
