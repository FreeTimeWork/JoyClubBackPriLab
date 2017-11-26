package com.joycity.joyclub.act.service.impl;


import com.joycity.joyclub.act.mapper.ActAttrMapper;
import com.joycity.joyclub.act.modal.ActAttrWithActName;
import com.joycity.joyclub.act.modal.ActWithCategoryName;
import com.joycity.joyclub.act.modal.generated.SaleActAttr;
import com.joycity.joyclub.act.service.ActAttrService;
import com.joycity.joyclub.commons.AbstractGetListData;
import com.joycity.joyclub.commons.exception.BusinessException;
import com.joycity.joyclub.commons.modal.base.CreateResult;
import com.joycity.joyclub.commons.modal.base.ListResult;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.modal.base.UpdateResult;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.joycity.joyclub.commons.constant.ResultCode.DATA_NOT_EXIST;

/**
 * Created by CallMeXYZ on 2017/2/27.
 */
@Service
public class ActAttrServiceImpl implements ActAttrService {
    private final Log log = LogFactory.getLog(ActAttrServiceImpl.class);
    @Autowired
    ActAttrMapper actAttrMapper;


    /**
     * @return resultData, data为按创建时间倒序的所有项目列表
     */
    @Override
    public ResultData getListByStoreIdAndActName(final Long projectId, String name, final PageUtil pageUtil) {
        if (name != null) {
            name = "%" + name + "%";
        }
        final String nameValue = name;
        return new AbstractGetListData<ActAttrWithActName>() {
            @Override
            public Long countByFilter() {
                return actAttrMapper.countByStoreIdAndActName(projectId, nameValue, pageUtil);
            }

            @Override
            public List<ActAttrWithActName> selectByFilter() {
                return actAttrMapper.selectByStoreIdAndActName(projectId, nameValue, pageUtil);
            }
        }.getList(pageUtil);

    }

    @Override
    public ResultData getActAttr(Long id) {
        SaleActAttr actAttr = actAttrMapper.selectByPrimaryKey(id);
        if (actAttr == null || actAttr.getDeleteFlag()) throw new BusinessException(DATA_NOT_EXIST, "该商品不存在");
        return new ResultData(actAttr);
    }


    @Override
    public ResultData createActAttr(SaleActAttr actAttr) {
        actAttr.setTargetNum(actAttr.getNum());
        actAttrMapper.insertSelective(actAttr);
        return new ResultData(new CreateResult(actAttr.getId()));
    }


    @Override
    public ResultData updateActAttr(SaleActAttr actAttr) {
        return new ResultData(new UpdateResult(actAttrMapper.updateByPrimaryKeySelective(actAttr)));
    }

}
