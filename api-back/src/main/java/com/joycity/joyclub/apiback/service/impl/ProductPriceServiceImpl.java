package com.joycity.joyclub.apiback.service.impl;

import com.joycity.joyclub.apiback.constant.PriceReviewStatus;
import com.joycity.joyclub.apiback.exception.BusinessException;
import com.joycity.joyclub.apiback.mapper.manual.SaleProductPriceMapper;
import com.joycity.joyclub.apiback.modal.base.CreateResult;
import com.joycity.joyclub.apiback.modal.base.DataListResult;
import com.joycity.joyclub.apiback.modal.base.ResultData;
import com.joycity.joyclub.apiback.modal.base.UpdateResult;
import com.joycity.joyclub.apiback.modal.generated.SaleProductPrice;
import com.joycity.joyclub.apiback.service.ProductPriceService;
import com.joycity.joyclub.apiback.util.PageUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static com.joycity.joyclub.apiback.constant.PriceReviewStatus.STATUS_NOT_REVIEW;
import static com.joycity.joyclub.apiback.constant.ResultCode.DATA_NOT_EXIST;
import static com.joycity.joyclub.apiback.constant.ResultCode.DATA_NOT_PERMIT;

/**
 * Created by CallMeXYZ on 2017/2/27.
 */
@Service
public class ProductPriceServiceImpl implements ProductPriceService {
    private final Log log = LogFactory.getLog(ProductPriceServiceImpl.class);
    @Autowired
    SaleProductPriceMapper productPriceMapper;


    /**
     * @return resultData, data为按创建时间倒序的所有项目列表
     */
    @Override
    public ResultData getListByStoreIdAndProductName(Long storeId, Integer reviewStatus, String name, PageUtil pageUtil) {
        DataListResult dataListResult = new DataListResult();
        dataListResult.setByPageUtil(pageUtil);
        if (name != null) {
            name = "%" + name + "%";
        }
        long sum = productPriceMapper.countByStoreIdAndProductNameAndReviewStatus(storeId, reviewStatus, name, pageUtil);
        dataListResult.setSum(sum);
        if (sum == 0) {
            dataListResult.setList(new ArrayList());
        } else {

            dataListResult.setList(productPriceMapper.selectByStoreIdAndProductNameAndReviewStatus(storeId, reviewStatus, name, pageUtil));
        }
        return new ResultData(dataListResult);
    }

    @Override
    public ResultData getProductPrice(Long id) {
        SaleProductPrice productPrice = productPriceMapper.selectByPrimaryKey(id);
        if (productPrice == null || productPrice.getDeleteFlag()) throw new BusinessException(DATA_NOT_EXIST, "该商品不存在");
        return new ResultData(productPrice);
    }


    @Override
    public ResultData createProductPrice(SaleProductPrice productPrice) {
        //初次创建待审核
        productPrice.setReviewStatus(STATUS_NOT_REVIEW);
        //初次创建肯定是上架状态
        productPrice.setForbidFlag(false);
        checkPrice(productPrice);
        productPriceMapper.insertSelective(productPrice);
        return new ResultData(new CreateResult(productPrice.getId()));
    }


    @Override
    public ResultData updateProductPrice(SaleProductPrice productPrice) {
        checkPrice(productPrice);
        productPrice.setReviewStatus(STATUS_NOT_REVIEW);
        return new ResultData(new UpdateResult(productPriceMapper.updateByPrimaryKeySelective(productPrice)));
    }

    @Override
    public ResultData forbidProductPrice(Long id) {
        SaleProductPrice price = new SaleProductPrice();
        price.setId(id);
        price.setForbidFlag(true);
        return new ResultData(new UpdateResult(productPriceMapper.updateByPrimaryKeySelective(price)));
    }

    /**
     * 检验该价格是否合理
     * 考虑新建和修改都可以，根据id来判断
     *
     * @param productPrice
     */
    private void checkPrice(SaleProductPrice productPrice) {
        Long sum = productPriceMapper.countPriceTimeOverlap(productPrice.getStartTime(), productPrice.getEndTime(), productPrice.getId());
        if (sum != 0) {
            throw new BusinessException(DATA_NOT_PERMIT, "时间段与其他冲突");
        }
    }
}
