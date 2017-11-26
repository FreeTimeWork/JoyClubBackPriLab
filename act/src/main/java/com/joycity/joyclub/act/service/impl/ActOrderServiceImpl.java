package com.joycity.joyclub.act.service.impl;

import com.joycity.joyclub.act.mapper.ActOrderMapper;
import com.joycity.joyclub.act.modal.ActOrderForBack;
import com.joycity.joyclub.act.modal.MyActOrder;
import com.joycity.joyclub.act.service.ActOrderService;
import com.joycity.joyclub.commons.AbstractGetListData;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;
import com.joycity.joyclub.commons.utils.SqlUtil;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/5/22.
 */
@Service
public class ActOrderServiceImpl implements ActOrderService {
    @Autowired
    ActOrderMapper orderMapper;
    @Override
    public ResultData getList(final Long projectId, final Byte status, final String code, final String name, final String phone,final String actName, final PageUtil pageUtil) {
        final String actNameLike =SqlUtil.getLikeStr(actName);
        return new AbstractGetListData<ActOrderForBack>() {
            @Override
            public Long countByFilter() {
                return orderMapper.countForStore(projectId, status, code, name, phone,actNameLike, pageUtil);
            }

            @Override
            public List<ActOrderForBack> selectByFilter() {
                return orderMapper.selectForStore(projectId, status, code, name, phone,actNameLike, pageUtil);

            }
        }.getList(pageUtil);
    }

   /* @Override
    public ResultData getInfo(Long orderId) {
        return null;
    }*/

    @Override
    public ResultData checkOrder(Long orderId) {
        orderMapper.checkOrder(orderId);
        return new ResultData();
    }

    @Override
    public List<ActOrderForBack> getActOrderList() {
        Long num = orderMapper.countForStore(null,null,null,null,null, null, null);
        PageUtil pageUtil = new PageUtil();
        pageUtil.setPageSize(num.intValue());
        return orderMapper.selectForStore(null, null, null, null, null,null, pageUtil);
    }
}
