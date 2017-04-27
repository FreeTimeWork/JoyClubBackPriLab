package com.joycity.joyclub.apiback.service.impl;

import com.joycity.joyclub.apiback.mapper.manual.SysProjectMapper;
import com.joycity.joyclub.apiback.service.VipUserService;
import com.joycity.joyclub.client.service.ClientService;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;
import com.joycity.joyclub.commons.utils.ThrowBusinessExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by CallMeXYZ on 2017/4/27.
 */
@Service
public class VipUserServiceImpl implements VipUserService {
    @Autowired
    ClientService clientService;
    @Autowired
    SysProjectMapper projectMapper;

    @Override
    public ResultData getList(Long projectId, String cardType, Integer pointStart, Integer pointEnd, String vipNo, String cardNo, String phone, PageUtil pageUtil) {
        String vipShare = projectMapper.getVipShare(projectId);
        ThrowBusinessExceptionUtil.checkNull(vipShare, "项目分摊不存在");
        return clientService.getListForBack(vipShare, cardType, pointStart, pointEnd, vipNo, cardNo, phone, pageUtil);

    }
}
