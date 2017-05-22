package com.joycity.joyclub.act.service.impl;

import com.joycity.joyclub.act.constant.ActPriceConst;
import com.joycity.joyclub.act.mapper.ActPriceMapper;
import com.joycity.joyclub.act.modal.ActPriceWithActInfo;
import com.joycity.joyclub.act.modal.generated.SaleActPrice;
import com.joycity.joyclub.act.service.ActPriceService;

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
import static com.joycity.joyclub.commons.constant.ResultCode.DATA_NOT_PERMIT;

/**
 * 已废弃
 * Created by CallMeXYZ on 2017/2/27.
 */
@Service

public class ActPriceServiceImpl implements ActPriceService {
    private final Log log = LogFactory.getLog(ActPriceServiceImpl.class);
    @Autowired
    ActPriceMapper actPriceMapper;

    @Override
    public ResultData getListForStore(Long storeId, Integer reviewStatus, String actName,final Byte buyType, PageUtil pageUtil) {
        ListResult listResult = new ListResult();
        listResult.setByPageUtil(pageUtil);
        if (actName != null) {
            actName = getSearchLikeStr(actName);
        }
        long sum = actPriceMapper.countForStore(storeId, reviewStatus, actName,buyType, pageUtil);
        listResult.setSum(sum);
        if (sum == 0) {
            listResult.setList(new ArrayList());
        } else {

            listResult.setList(actPriceMapper.selectForStore(storeId, reviewStatus, actName, buyType, pageUtil));
        }
        return new ResultData(listResult);
    }

    @Override
    public ResultData getListForProject(String storeName, final Integer reviewStatus, String actName,final Byte buyType, final PageUtil pageUtil) {
        ListResult listResult = new ListResult();
        listResult.setByPageUtil(pageUtil);
        if (storeName != null) {
            storeName = getSearchLikeStr(storeName);
        }
        if (actName != null) {
            actName = getSearchLikeStr(actName);
        }
        final String storeNameLike = storeName;
        final String actNameLike = actName;
        new AbstractGetListData<ActPriceWithActInfo>() {
            @Override
            public Long countByFilter() {
                return  actPriceMapper.countForProject(storeNameLike, reviewStatus, actNameLike, buyType, pageUtil);
            }

            @Override
            public List<ActPriceWithActInfo> selectByFilter() {
                return actPriceMapper.selectForProject(storeNameLike, reviewStatus, actNameLike,buyType, pageUtil);
            }
        }.getList(pageUtil);

        return new ResultData(listResult);
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
        actPrice.setReviewStatus(ActPriceConst.STATUS_NOT_REVIEW);
        //初次创建肯定是上架状态
        actPrice.setForbidFlag(false);
        checkPrice(actPrice);
        actPriceMapper.insertSelective(actPrice);
        return new ResultData(new CreateResult(actPrice.getId()));
    }


    @Override
    public ResultData updateActPrice(SaleActPrice price) {
        checkPrice(price);
        price.setReviewStatus(ActPriceConst.STATUS_NOT_REVIEW);
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
        price.setReviewStatus(ActPriceConst.STATUS_REVIEW_PERMIT);
        return new ResultData(new UpdateResult(actPriceMapper.updateByPrimaryKeySelective(price)));
    }

    @Override
    public ResultData rejectActPrice(Long id, String reviewInfo) {
        SaleActPrice price = new SaleActPrice();
        price.setId(id);
        price.setReviewStatus(ActPriceConst.STATUS_REVIEW_REJECT);
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
