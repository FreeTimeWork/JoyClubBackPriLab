package com.joycity.joyclub.apiback.service.impl;

import com.joycity.joyclub.apiback.constant.PriceReviewStatus;
import com.joycity.joyclub.apiback.exception.BusinessException;
import com.joycity.joyclub.apiback.mapper.manual.SaleActPriceMapper;
import com.joycity.joyclub.commons.modal.base.CreateResult;
import com.joycity.joyclub.commons.modal.base.DataListResult;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.modal.base.UpdateResult;
import com.joycity.joyclub.apiback.modal.generated.SaleActPrice;
import com.joycity.joyclub.apiback.service.ActPriceService;
import com.joycity.joyclub.commons.utils.PageUtil;
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
public class ActPriceServiceImpl implements ActPriceService {
    private final Log log = LogFactory.getLog(ActPriceServiceImpl.class);
    @Autowired
    SaleActPriceMapper actPriceMapper;

    @Override
    public ResultData getListForStore(Long storeId, Integer reviewStatus, String actName, PageUtil pageUtil) {
        DataListResult dataListResult = new DataListResult();
        dataListResult.setByPageUtil(pageUtil);
        if (actName != null) {
            actName = getSearchLikeStr(actName);
        }
        long sum = actPriceMapper.countForStore(storeId, reviewStatus, actName, pageUtil);
        dataListResult.setSum(sum);
        if (sum == 0) {
            dataListResult.setList(new ArrayList());
        } else {

            dataListResult.setList(actPriceMapper.selectForStore(storeId, reviewStatus, actName, pageUtil));
        }
        return new ResultData(dataListResult);
    }

    @Override
    public ResultData getListForProject(String storeName, Integer reviewStatus, String actName, PageUtil pageUtil) {
        DataListResult dataListResult = new DataListResult();
        dataListResult.setByPageUtil(pageUtil);
        if (storeName != null) {
            storeName = getSearchLikeStr(storeName);
        }
        if (actName != null) {
            actName = getSearchLikeStr(actName);
        }
        long sum = actPriceMapper.countForProject(storeName, reviewStatus, actName, pageUtil);
        dataListResult.setSum(sum);
        if (sum == 0) {
            dataListResult.setList(new ArrayList());
        } else {

            dataListResult.setList(actPriceMapper.selectForProject(storeName, reviewStatus, actName, pageUtil));
        }
        return new ResultData(dataListResult);
    }

    private String getSearchLikeStr(String likeStr) {
        return "%" + likeStr + "%";
    }

    @Override
    public ResultData getActPrice(Long id) {
        SaleActPrice actPrice = actPriceMapper.selectByPrimaryKey(id);
        if (actPrice == null || actPrice.getDeleteFlag()) throw new BusinessException(DATA_NOT_EXIST, "该商品不存在");
        return new ResultData(actPrice);
    }


    @Override
    public ResultData createActPrice(SaleActPrice actPrice) {
        //初次创建待审核
        actPrice.setReviewStatus(STATUS_NOT_REVIEW);
        //初次创建肯定是上架状态
        actPrice.setForbidFlag(false);
        checkPrice(actPrice);
        actPriceMapper.insertSelective(actPrice);
        return new ResultData(new CreateResult(actPrice.getId()));
    }


    @Override
    public ResultData updateActPrice(SaleActPrice price) {
        checkPrice(price);
        price.setReviewStatus(STATUS_NOT_REVIEW);
        return updateByPrimaryKeySelective(price);
    }

    @Override
    public ResultData forbidActPrice(Long id) {
        SaleActPrice price = new SaleActPrice();
        price.setId(id);
        price.setForbidFlag(true);
        return updateByPrimaryKeySelective(price);
    }

    @Override
    public ResultData permitActPrice(Long id) {
        SaleActPrice price = new SaleActPrice();
        price.setId(id);
        price.setReviewStatus(PriceReviewStatus.STATUS_REVIEW_PERMIT);
        return new ResultData(new UpdateResult(actPriceMapper.updateByPrimaryKeySelective(price)));
    }

    @Override
    public ResultData rejectActPrice(Long id, String reviewInfo) {
        SaleActPrice price = new SaleActPrice();
        price.setId(id);
        price.setReviewStatus(PriceReviewStatus.STATUS_REVIEW_REJECT);
        price.setReviewInfo(reviewInfo);
        return updateByPrimaryKeySelective(price);
    }


    /**
     * 检验该价格是否合理
     * 考虑新建和修改都可以，根据id来判断
     *
     * @param actPrice
     */
    private void checkPrice(SaleActPrice actPrice) {
        Long sum = actPriceMapper.countPriceTimeOverlap(actPrice.getId(),actPrice.getActId(),actPrice.getStartTime(), actPrice.getEndTime());
        if (sum != 0) {
            throw new BusinessException(DATA_NOT_PERMIT, "时间段与其他冲突");
        }
    }

    private ResultData updateByPrimaryKeySelective(SaleActPrice price) {
        return new ResultData(new UpdateResult(actPriceMapper.updateByPrimaryKeySelective(price)));
    }
}
