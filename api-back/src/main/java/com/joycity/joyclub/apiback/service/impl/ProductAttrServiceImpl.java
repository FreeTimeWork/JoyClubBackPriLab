package com.joycity.joyclub.apiback.service.impl;

import com.joycity.joyclub.apiback.exception.BusinessException;
import com.joycity.joyclub.apiback.mapper.manual.SaleProductAttrMapper;
import com.joycity.joyclub.apiback.modal.base.CreateResult;
import com.joycity.joyclub.apiback.modal.base.DataListResult;
import com.joycity.joyclub.apiback.modal.base.ResultData;
import com.joycity.joyclub.apiback.modal.base.UpdateResult;
import com.joycity.joyclub.apiback.modal.generated.SaleProductAttr;
import com.joycity.joyclub.apiback.service.ProductAttrService;
import com.joycity.joyclub.apiback.util.PageUtil;
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
public class ProductAttrServiceImpl implements ProductAttrService {
    private final Log log = LogFactory.getLog(ProductAttrServiceImpl.class);
    @Autowired
    SaleProductAttrMapper productAttrMapper;



    /**
     * @return resultData, data为按创建时间倒序的所有项目列表
     */
    @Override
    public ResultData getListByStoreIdAndProductName(Long storeId, String name, PageUtil pageUtil) {
        DataListResult dataListResult = new DataListResult();
        dataListResult.setByPageUtil(pageUtil);
        if (name != null) {
            name="%"+name+"%";
        }
        long sum = productAttrMapper.countByStoreIdAndProductName(storeId,name,pageUtil);
        dataListResult.setSum(sum);
        if (sum == 0) {
            dataListResult.setList(new ArrayList());
        } else {

            dataListResult.setList(productAttrMapper.selectByStoreIdAndProductName(storeId,name,pageUtil));
        }
        return new ResultData(dataListResult);
    }

    @Override
    public ResultData getProductAttr(Long id) {
        SaleProductAttr productAttr = productAttrMapper.selectByPrimaryKey(id);
        if (productAttr == null || productAttr.getDeleteFlag()) throw new BusinessException(DATA_NOT_EXIST, "该商品不存在");
        return new ResultData(productAttr);
    }


    @Override
    public ResultData createProductAttr(SaleProductAttr productAttr) {
        productAttrMapper.insertSelective(productAttr);
        return new ResultData(new CreateResult(productAttr.getId()));
    }

   

    @Override
    public ResultData updateProductAttr( SaleProductAttr productAttr) {
        return new ResultData(new UpdateResult(productAttrMapper.updateByPrimaryKeySelective(productAttr)));
    }

}
