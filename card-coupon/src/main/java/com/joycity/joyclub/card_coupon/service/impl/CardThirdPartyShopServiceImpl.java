package com.joycity.joyclub.card_coupon.service.impl;

import com.joycity.joyclub.card_coupon.mapper.CardThirdPartyShopMapper;
import com.joycity.joyclub.card_coupon.modal.generated.CardThirdPartyShop;
import com.joycity.joyclub.card_coupon.service.CardThirdPartyShopService;
import com.joycity.joyclub.commons.AbstractGetListData;
import com.joycity.joyclub.commons.constant.ResultCode;
import com.joycity.joyclub.commons.exception.BusinessException;
import com.joycity.joyclub.commons.modal.base.CreateResult;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.modal.base.UpdateResult;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fangchen.chai on 2017/7/26
 */
@Service
public class CardThirdPartyShopServiceImpl implements CardThirdPartyShopService {

    @Autowired
    private CardThirdPartyShopMapper cardThirdPartyShopMapper;

    @Override
    public ResultData createThirdPartyShop(CardThirdPartyShop thirdPartyShop) {
        cardThirdPartyShopMapper.insertSelective(thirdPartyShop);
        return new ResultData(new CreateResult(thirdPartyShop.getId()));
    }

    @Override
    public ResultData getListByName(Long projectId, String name, PageUtil pageUtil) {
        if (name != null) {

            name = "%" + name + "%";
        }
        String finalName = name;

        return new AbstractGetListData<CardThirdPartyShop>() {
            @Override
            public Long countByFilter() {
                return cardThirdPartyShopMapper.countListByName(projectId,finalName);
            }

            @Override
            public List<CardThirdPartyShop> selectByFilter() {
                return cardThirdPartyShopMapper.selectListByName(projectId,finalName,pageUtil);
            }
        }.getList(pageUtil);
    }

    @Override
    public ResultData getShop(Long id) {
        CardThirdPartyShop shop = cardThirdPartyShopMapper.selectByPrimaryKey(id);
        if (shop == null || shop.getDeleteFlag())
            throw new BusinessException(ResultCode.DATA_NOT_EXIST);
        return new ResultData(shop);
    }

    @Override
    public ResultData updateShop(CardThirdPartyShop shop) {
        return new ResultData(new UpdateResult(cardThirdPartyShopMapper.updateByPrimaryKeySelective(shop)));
    }

}
