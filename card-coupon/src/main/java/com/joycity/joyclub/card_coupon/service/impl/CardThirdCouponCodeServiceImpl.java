package com.joycity.joyclub.card_coupon.service.impl;

import com.joycity.joyclub.card_coupon.mapper.*;
import com.joycity.joyclub.card_coupon.modal.ShowCouponCodeInfo;
import com.joycity.joyclub.card_coupon.modal.filter.ShowCouponCodeFilter;
import com.joycity.joyclub.card_coupon.service.CardThirdCouponCodeService;
import com.joycity.joyclub.commons.AbstractGetListData;
import com.joycity.joyclub.commons.constant.LogConst;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  Created by fangchen.chai on 2017/7/13.
 */
@Service
public class CardThirdCouponCodeServiceImpl implements CardThirdCouponCodeService {

    private Log logger = LogFactory.getLog(LogConst.LOG_TASK);

    @Autowired
    private CardCouponLaunchMapper launchMapper;
    @Autowired
    private CardCouponCodeMapper cardCouponCodeMapper;
    @Autowired
    private CardCouponMapper cardCouponMapper;
    @Autowired
    private CardVipBatchMapper cardVipBatchMapper;
    @Autowired
    private CardThirdpartyCouponCodeMapper cardThirdpartyCouponCodeMapper;

    @Override
    public ResultData getListByFilter(Long projectId, ShowCouponCodeFilter filter, PageUtil pageUtil) {

        return new AbstractGetListData<ShowCouponCodeInfo>() {
            @Override
            public Long countByFilter() {
                return cardCouponCodeMapper.countCardCouponCodeByFilter(projectId, filter);
            }

            @Override
            public List<ShowCouponCodeInfo> selectByFilter() {
                return cardCouponCodeMapper.selectCardCouponCodeByFilter(projectId, filter, pageUtil);
            }
        }.getList(pageUtil);
    }


}
