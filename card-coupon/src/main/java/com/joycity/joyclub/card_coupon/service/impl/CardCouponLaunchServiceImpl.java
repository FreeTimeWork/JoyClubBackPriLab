package com.joycity.joyclub.card_coupon.service.impl;

import java.util.Date;
import java.util.List;

import com.joycity.joyclub.card_coupon.mapper.CardCouponLaunchMapper;
import com.joycity.joyclub.card_coupon.modal.ShowCouponLaunchInfo;
import com.joycity.joyclub.card_coupon.modal.generated.CardCouponLaunch;
import com.joycity.joyclub.card_coupon.service.CardCouponLaunchService;
import com.joycity.joyclub.commons.AbstractGetListData;
import com.joycity.joyclub.commons.constant.ResultCode;
import com.joycity.joyclub.commons.modal.base.CreateResult;
import com.joycity.joyclub.commons.modal.base.ListResult;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by fangchen.chai on 2017/7/12.
 */
@Service
public class CardCouponLaunchServiceImpl implements CardCouponLaunchService {

    @Autowired
    private CardCouponLaunchMapper launchMapper;

    @Override
    public ResultData createCardCouponLaunch(CardCouponLaunch launch) {
        launchMapper.insertSelective(launch);
        return new ResultData(new CreateResult(launch.getId()));
    }

    @Override
    public ResultData getListByCouponNameAndCouponTypeAndStatus(String name, Integer type, Integer status, PageUtil pageUtil) {
        if (name != null) {
            name = "%" + name + "%";
        }
        final String finalName = name;
        final Date now = new Date();
        ResultData data = new AbstractGetListData<ShowCouponLaunchInfo>() {
            @Override
            public Long countByFilter() {
                return launchMapper.countByCouponNameAndCouponTypeAndStatus(finalName, type, status, now, pageUtil);
            }

            @Override
            public List<ShowCouponLaunchInfo> selectByFilter() {
                return launchMapper.selectByCouponNameAndCouponTypeAndStatus(finalName, type, status, now, pageUtil);
            }

        }.getList(pageUtil);

        if (data.getCode().equals(ResultCode.SUCCESS)) {
            ListResult listResult = (ListResult) data.getData();
            List<ShowCouponLaunchInfo> infos = listResult.getList();
            if (CollectionUtils.isNotEmpty(infos)) {
                for (ShowCouponLaunchInfo info : infos) {
                    ShowCouponLaunchInfo.setStatus(info, now);
                }
            }
        }
        return data;
    }
}
