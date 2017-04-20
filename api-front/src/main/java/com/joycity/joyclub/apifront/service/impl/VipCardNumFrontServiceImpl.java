package com.joycity.joyclub.apifront.service.impl;

import com.joycity.joyclub.apifront.constant.ResultCode;
import com.joycity.joyclub.apifront.exception.BusinessException;
import com.joycity.joyclub.apifront.mapper.manual.VipCardNumMapper;
import com.joycity.joyclub.apifront.service.VipCardNumFrontService;
import com.joycity.joyclub.commons.constants.ProjectVipCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by CallMeXYZ on 2017/4/10.
 */
@Service
public class VipCardNumFrontServiceImpl implements VipCardNumFrontService {
    @Autowired
    VipCardNumMapper vipCardNumMapper;

    @Override
    public Long useVipCard(Long projectId, String type) {
        Long numAvailable = vipCardNumMapper.getMinCardNumNotUsed(projectId, type);
        if (numAvailable == null) {
            throw new BusinessException(ResultCode.VIP_CARD_NUM_NO_AVAILABLE, "无法分配电子卡");
        }
        vipCardNumMapper.setCardStatusUsed(numAvailable);
        return numAvailable;
    }

    @Override
    public Long useDigitalVipCard(Long projectId) {
        return useVipCard(projectId, ProjectVipCard.VIP_CARD_DIGITAL);
    }

    @Override
    public Integer cancelUseVipCard(Long vipCardNum) {
        return vipCardNumMapper.setCardStatusUsed(vipCardNum);
    }
}
