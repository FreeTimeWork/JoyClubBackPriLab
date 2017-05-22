package com.joycity.joyclub.act.service.impl;

import com.joycity.joyclub.act.mapper.ActAttrMapper;
import com.joycity.joyclub.act.mapper.ActMapper;
import com.joycity.joyclub.act.mapper.ActPriceMapper;
import com.joycity.joyclub.act.modal.ActFormData;
import com.joycity.joyclub.act.modal.ActInfoPage;
import com.joycity.joyclub.act.modal.ActWithCategoryName;
import com.joycity.joyclub.act.modal.generated.SaleAct;
import com.joycity.joyclub.act.modal.generated.SaleActPrice;
import com.joycity.joyclub.act.modal.generated.SaleActWithBLOBs;
import com.joycity.joyclub.act.service.ActService;
import com.joycity.joyclub.commons.AbstractGetListData;
import com.joycity.joyclub.commons.exception.BusinessException;
import com.joycity.joyclub.commons.mapper.manual.StoreMapper;
import com.joycity.joyclub.commons.mapper.manual.SysActCategoryMapper;
import com.joycity.joyclub.commons.modal.base.*;
import com.joycity.joyclub.commons.modal.generated.SysActCategory;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.joycity.joyclub.commons.constant.ResultCode.DATA_NOT_EXIST;
import static com.joycity.joyclub.commons.utils.ThrowBusinessExceptionUtil.checkNull;

/**
 * Created by CallMeXYZ on 2017/5/5.
 */
@Service
public class ActServiceImpl implements ActService {
    private final Log log = LogFactory.getLog(ActServiceImpl.class);
    @Autowired
    ActMapper actMapper;
    @Autowired
    StoreMapper storeMapper;
    @Autowired
    SysActCategoryMapper categoryMapper;
    @Autowired
    ActPriceMapper actPriceMapper;

    @Autowired
    ActAttrMapper attrMapper;
    /**
     * @return resultData, data为按创建时间倒序的所有项目列表
     */
    @Override
    public ResultData getListByStoreIdAndName(final Long storeId, String name, final PageUtil pageUtil) {
        if (name != null) {
            name = "%" + name + "%";
        }
        final String nameValue = name;
        return new AbstractGetListData<ActWithCategoryName>() {
            @Override
            public Long countByFilter() {
                return actMapper.countByStoreIdAndName(storeId, nameValue, pageUtil);
            }

            @Override
            public List<ActWithCategoryName> selectByFilter() {
                return actMapper.selectByStoreIdAndName(storeId, nameValue, pageUtil);
            }
        }.getList(pageUtil);
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
        formData.setCategories(categoryMapper.getSimpleList());
        return new ResultData(formData);
    }

    @Override
    public ResultData updateAct(SaleActWithBLOBs act) {
        return new ResultData(new UpdateResult(actMapper.updateByPrimaryKeySelective(act)));
    }

    @Override
    public ResultData getInfo(Long id) {
        SaleAct act = actMapper.selectByPrimaryKey(id);
        checkNull(act, "活动不存在");
        SaleActPrice price = actPriceMapper.getNowPrice(id);
        checkNull(price, "活动未上架或者已下架");
        IdNamePortrait store = storeMapper.getSimpleInfo(act.getStoreId());
        checkNull(store, "商户不存在");
        SysActCategory category  = categoryMapper.selectByPrimaryKey(act.getCategoryId());
        checkNull(category, "商户分类不存在");
        ActInfoPage infoPage = new ActInfoPage();
        infoPage.setAct(act);
        infoPage.setPrice(price);
        infoPage.setStore(store);
        infoPage.setCategory(category);
        return new ResultData(infoPage);
    }

    // TODO: 2017/4/13 待完善
    @Override
    public ResultData getList(Long projectId, Long storeId, PageUtil pageUtil) {

        return new ResultData(new ListResult(actMapper.selectSimpleList(projectId,storeId,pageUtil)));
    }

    @Override
    public ResultData getAttrs(Long id) {
        return new ResultData(new ListResult(attrMapper.selectAvailableSimple(id)));
    }
}
