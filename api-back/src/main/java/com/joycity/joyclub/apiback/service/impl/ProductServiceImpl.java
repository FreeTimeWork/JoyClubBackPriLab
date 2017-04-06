package com.joycity.joyclub.apiback.service.impl;

import com.joycity.joyclub.apiback.exception.BusinessException;
import com.joycity.joyclub.apiback.mapper.manual.SaleProductMapper;
import com.joycity.joyclub.apiback.mapper.manual.SaleStoreDesignerMapper;
import com.joycity.joyclub.apiback.mapper.manual.SysProductCategoryMapper;
import com.joycity.joyclub.apiback.modal.base.CreateResult;
import com.joycity.joyclub.apiback.modal.base.DataListResult;
import com.joycity.joyclub.apiback.modal.base.ResultData;
import com.joycity.joyclub.apiback.modal.base.UpdateResult;
import com.joycity.joyclub.apiback.modal.generated.SaleProductWithBLOBs;
import com.joycity.joyclub.apiback.modal.product.ProductFormData;
import com.joycity.joyclub.apiback.service.ProductService;
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
public class ProductServiceImpl implements ProductService {
    private final Log log = LogFactory.getLog(ProductServiceImpl.class);
    @Autowired
    SaleProductMapper productMapper;
    @Autowired
    SaleStoreDesignerMapper designerMapper;
    @Autowired
    SysProductCategoryMapper productCategoryMapper;

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
        long sum = productMapper.countByStoreIdAndName(storeId, name, pageUtil);
        dataListResult.setSum(sum);
        if (sum == 0) {
            dataListResult.setList(new ArrayList());
        } else {

            dataListResult.setList(productMapper.selectByStoreIdAndName(storeId, name, pageUtil));
        }
        return new ResultData(dataListResult);
    }

    @Override
    public ResultData getProduct(Long id) {
        SaleProductWithBLOBs product = productMapper.selectByPrimaryKey(id);
        if (product == null || product.getDeleteFlag()) throw new BusinessException(DATA_NOT_EXIST, "该商品不存在");
        return new ResultData(product);
    }


    @Override
    public ResultData createProduct(SaleProductWithBLOBs product) {
        productMapper.insertSelective(product);
        return new ResultData(new CreateResult(product.getId()));
    }

    @Override
    public ResultData getProductFormData(Long storeId) {
        ProductFormData formData = new ProductFormData();
        formData.setDesigners(designerMapper.getSimpleListByStoreId(storeId));
        formData.setCategories(productCategoryMapper.getSimpleList());
        return new ResultData(formData);
    }

    @Override
    public ResultData updateProduct(SaleProductWithBLOBs product) {
        return new ResultData(new UpdateResult(productMapper.updateByPrimaryKeySelective(product)));
    }

}
