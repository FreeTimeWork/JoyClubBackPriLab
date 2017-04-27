package com.joycity.joyclub.apiback.service.impl;

import com.joycity.joyclub.commons.exception.BusinessException;
import com.joycity.joyclub.apiback.mapper.manual.SaleActAttrMapper;
import com.joycity.joyclub.commons.modal.base.CreateResult;
import com.joycity.joyclub.commons.modal.base.ListResult;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.modal.base.UpdateResult;
import com.joycity.joyclub.apiback.modal.generated.SaleActAttr;
import com.joycity.joyclub.apiback.service.ActAttrService;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static com.joycity.joyclub.commons.constant.ResultCode.DATA_NOT_EXIST;

/**
 * Created by CallMeXYZ on 2017/2/27.
 */
@Service
public class ActAttrServiceImpl implements ActAttrService {
    private final Log log = LogFactory.getLog(ActAttrServiceImpl.class);
    @Autowired
    SaleActAttrMapper actAttrMapper;


    /**
     * @return resultData, data为按创建时间倒序的所有项目列表
     */
    @Override
    public ResultData getListByStoreIdAndActName(Long storeId, String name, PageUtil pageUtil) {
        ListResult listResult = new ListResult();
        listResult.setByPageUtil(pageUtil);
        if (name != null) {
            name = "%" + name + "%";
        }
        long sum = actAttrMapper.countByStoreIdAndActName(storeId, name, pageUtil);
        listResult.setSum(sum);
        if (sum == 0) {
            listResult.setList(new ArrayList());
        } else {

            listResult.setList(actAttrMapper.selectByStoreIdAndActName(storeId, name, pageUtil));
        }
        return new ResultData(listResult);
    }

    @Override
    public ResultData getActAttr(Long id) {
        SaleActAttr actAttr = actAttrMapper.selectByPrimaryKey(id);
        if (actAttr == null || actAttr.getDeleteFlag()) throw new BusinessException(DATA_NOT_EXIST, "该商品不存在");
        return new ResultData(actAttr);
    }


    @Override
    public ResultData createActAttr(SaleActAttr actAttr) {
        actAttrMapper.insertSelective(actAttr);
        return new ResultData(new CreateResult(actAttr.getId()));
    }


    @Override
    public ResultData updateActAttr(SaleActAttr actAttr) {
        return new ResultData(new UpdateResult(actAttrMapper.updateByPrimaryKeySelective(actAttr)));
    }

}
