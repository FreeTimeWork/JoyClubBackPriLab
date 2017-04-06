package com.joycity.joyclub.apiback.service.impl;

import com.joycity.joyclub.apiback.exception.BusinessException;
import com.joycity.joyclub.apiback.mapper.manual.SaleActMapper;
import com.joycity.joyclub.apiback.mapper.manual.SaleStoreDesignerMapper;
import com.joycity.joyclub.apiback.mapper.manual.SysActCategoryMapper;
import com.joycity.joyclub.apiback.modal.base.CreateResult;
import com.joycity.joyclub.apiback.modal.base.DataListResult;
import com.joycity.joyclub.apiback.modal.base.ResultData;
import com.joycity.joyclub.apiback.modal.base.UpdateResult;
import com.joycity.joyclub.apiback.modal.generated.SaleActWithBLOBs;
import com.joycity.joyclub.apiback.modal.act.ActFormData;
import com.joycity.joyclub.apiback.service.ActService;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static com.joycity.joyclub.apiback.constant.ResultCode.DATA_NOT_EXIST;

/**
 * Created by CallMeXYZ on 2017/2/27.
 */
@Service
public class ActServiceImpl implements ActService {
    private final Log log = LogFactory.getLog(ActServiceImpl.class);
    @Autowired
    SaleActMapper actMapper;
    @Autowired
    SaleStoreDesignerMapper designerMapper;
    @Autowired
    SysActCategoryMapper actCategoryMapper;

    /**
     * @return resultData, data为按创建时间倒序的所有项目列表
     */
    @Override
    public ResultData getListByStoreIdAndName(Long storeId, String name, PageUtil pageUtil) {
        DataListResult dataListResult = new DataListResult();
        dataListResult.setByPageUtil(pageUtil);
        if (name != null) {
            name = "%" + name + "%";
        }
        long sum = actMapper.countByStoreIdAndName(storeId, name, pageUtil);
        dataListResult.setSum(sum);
        if (sum == 0) {
            dataListResult.setList(new ArrayList());
        } else {

            dataListResult.setList(actMapper.selectByStoreIdAndName(storeId, name, pageUtil));
        }
        return new ResultData(dataListResult);
    }

    @Override
    public ResultData getAct(Long id) {
        SaleActWithBLOBs act = actMapper.selectByPrimaryKey(id);
        if (act == null || act.getDeleteFlag()) throw new BusinessException(DATA_NOT_EXIST, "该商品不存在");
        return new ResultData(act);
    }


    @Override
    public ResultData createAct(SaleActWithBLOBs act) {
        actMapper.insertSelective(act);
        return new ResultData(new CreateResult(act.getId()));
    }

    @Override
    public ResultData getActFormData(Long storeId) {
        ActFormData formData = new ActFormData();
        formData.setCategories(actCategoryMapper.getSimpleList());
        return new ResultData(formData);
    }

    @Override
    public ResultData updateAct(SaleActWithBLOBs act) {
        return new ResultData(new UpdateResult(actMapper.updateByPrimaryKeySelective(act)));
    }

}
