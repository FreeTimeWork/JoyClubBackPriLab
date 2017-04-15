package com.joycity.joyclub.apifront.service.impl;

import com.joycity.joyclub.apifront.mapper.manual.designer.DesignerFrontMapper;
import com.joycity.joyclub.apifront.mapper.manual.product.ProductFrontMapper;
import com.joycity.joyclub.apifront.mapper.manual.store.StoreFrontMapper;
import com.joycity.joyclub.apifront.mapper.manual.product.ProductPriceFrontMapper;
import com.joycity.joyclub.apifront.modal.product.ProductInfoPage;
import com.joycity.joyclub.apifront.modal.base.IdNamePortrait;
import com.joycity.joyclub.apifront.modal.product.price.ProductPrice;
import com.joycity.joyclub.apifront.service.ProductFrontService;
import com.joycity.joyclub.commons.modal.base.DataListResult;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.joycity.joyclub.apifront.util.ThrowBusinessExceptionUtil.checkNull;

/**
 * Created by CallMeXYZ on 2017/4/13.
 */
@Service
public class ProductFrontServiceImpl implements ProductFrontService {
    @Autowired
    ProductFrontMapper productMapper;
    @Autowired
    StoreFrontMapper storeMapper;
    @Autowired
    DesignerFrontMapper designerMapper;
    @Autowired
    ProductPriceFrontMapper productPriceMapper;

    @Override
    public ResultData getInfo(Long id) {
        ProductInfoPage info = productMapper.getInfo(id);
        checkNull(info, "商品不存在");
        ProductPrice price = productPriceMapper.getNowPrice(id);
        checkNull(price, "商品未上架或者已下架");
        info.setPrice(price.getPrice());
        //设置真实的积分比例
        info.setPointRate(price.getPointRate());
        IdNamePortrait store = storeMapper.getSimpleInfo(info.getStoreId());
        checkNull(store, "商户不存在");
        info.setStore(store);
        if (info.getDesignerId() != null) {
            IdNamePortrait designer = designerMapper.getSimpleInfo(info.getDesignerId());
            if (designer != null) {
                info.setDesigner(designer);
            }
        }

        return new ResultData(info);
    }

    @Override
    public ResultData getList(Long storeId, Long designerId, PageUtil pageUtil) {
        DataListResult listResult = new DataListResult(productMapper.selectByFilter(storeId, designerId, pageUtil));
        listResult.setByPageUtil(pageUtil);
        return new ResultData(listResult);
    }

    // TODO: 2017/4/13 待完善
   /* @Override
    public ResultData getList(Long storeId) {
        return new ResultData(new DataListResult(productMapper.selectByStore(storeId)));
    }*/
}
