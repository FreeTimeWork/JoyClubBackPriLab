package com.joycity.joyclub.product.service.impl;

import static com.joycity.joyclub.commons.constant.ResultCode.DATA_NOT_EXIST;
import static com.joycity.joyclub.commons.utils.ThrowBusinessExceptionUtil.checkNull;

import java.util.ArrayList;
import java.util.List;

import com.joycity.joyclub.commons.AbstractGetListData;
import com.joycity.joyclub.commons.exception.BusinessException;
import com.joycity.joyclub.commons.mapper.manual.SaleStoreDesignerMapper;
import com.joycity.joyclub.commons.mapper.manual.StoreMapper;
import com.joycity.joyclub.commons.mapper.manual.SysProductCategoryMapper;
import com.joycity.joyclub.commons.modal.base.*;
import com.joycity.joyclub.commons.utils.PageUtil;
import com.joycity.joyclub.commons.utils.ThrowBusinessExceptionUtil;
import com.joycity.joyclub.product.mapper.ProductAttrMapper;
import com.joycity.joyclub.product.mapper.ProductMapper;
import com.joycity.joyclub.product.mapper.ProductPriceMapper;
import com.joycity.joyclub.product.modal.*;
import com.joycity.joyclub.product.modal.generated.SaleProductPrice;
import com.joycity.joyclub.product.modal.generated.SaleProductWithBLOBs;
import com.joycity.joyclub.product.service.ProductService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by CallMeXYZ on 2017/2/27.
 */
@Service
public class ProductServiceImpl implements ProductService {
    private final Log log = LogFactory.getLog(ProductServiceImpl.class);
    @Autowired
    ProductMapper productMapper;
    @Autowired
    SaleStoreDesignerMapper designerMapper;
    @Autowired
    SysProductCategoryMapper productCategoryMapper;
    @Autowired
    ProductPriceMapper priceMapper;
    @Autowired
    ProductAttrMapper attrMapper;
    @Autowired
    StoreMapper storeMapper;

    /**
     * @return resultData, data为按创建时间倒序的所有项目列表
     */
    @Override
    public ResultData getListByStoreIdAndName(Long storeId, String name, PageUtil pageUtil) {
        ListResult listResult = new ListResult();
        listResult.setByPageUtil(pageUtil);
        if (name != null) {
            name = "%" + name + "%";
        }
        long sum = productMapper.countByStoreIdAndName(storeId, name, pageUtil);
        listResult.setSum(sum);
        if (sum == 0) {
            listResult.setList(new ArrayList());
        } else {

            listResult.setList(productMapper.selectByStoreIdAndName(storeId, name, pageUtil));
        }
        return new ResultData(listResult);
    }

    @Override
    public ResultData getListByProductNameAndStoreName(Long projectId, String productName, String storeName, PageUtil pageUtil) {

        if (productName != null) {
            productName = "%" + productName + "%";
        }
        if (storeName != null) {
            storeName = "%" + storeName + "%";
        }
        final String finalProductName = productName;
        final String finalStoreName = storeName;

        return new AbstractGetListData<ProductWithStoreName>() {
            @Override
            public Long countByFilter() {
                return productMapper.countByProductNameAndStoreName(projectId, finalProductName, finalStoreName, pageUtil);
            }

            @Override
            public List<ProductWithStoreName> selectByFilter() {
                return productMapper.selectByProductNameAndStoreName(projectId, finalProductName, finalStoreName, pageUtil);

            }
        }.getList(pageUtil);
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


    @Override
    public ResultData getInfo(Long id) {
        ProductInfoPage info = productMapper.getInfo(id);
        checkNull(info, "商品不存在");
        SaleProductPrice price = priceMapper.getNowPrice(id);
        checkNull(price, "商品未上架或者已下架");
        info.setPrice(price.getPrice());
        //设置真实的积分比例
        info.setPointRate(price.getPointRate());
        //设置特价标志
        info.setSpecialPriceFlag(price.getSpecialPriceFlag());

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
    public ResultData getAttrs(Long id) {
        return new ResultData(new ListResult(attrMapper.selectAvailableSimpleByProduct(id)));
    }

    @Override
    public ResultData getProductList(Long projectId, Long categoryId, Long storeId, Long designerId, PageUtil pageUtil) {

        return getProductList(projectId,categoryId, storeId, designerId, null, pageUtil);
    }

    @Override
    public ResultData getSpecialPriceProductList(Long projectId,Long categoryId, Long storeId, Long designerId, PageUtil pageUtil) {

        return getProductList(projectId, categoryId, storeId, designerId, true, pageUtil);
    }

    @Override
    public ResultData getSpecialPriceAct(Long id) {

        return new ResultData(productMapper.getSpecialPriceAct(id));
    }

    @Override
    public ResultData getProjectLatestSpecialPriceAct(Long projectId) {
        SpecialPriceAct act = productMapper.getProjectLatestSpecialPriceAct(projectId);
        if (act == null) {
            throw new BusinessException(DATA_NOT_EXIST, "目前没有任何优选活动");
        }
        return new ResultData(act);
    }

    @Override
    public ResultData getSpecialPriceActProducts(Long id, PageUtil pageUtil) {
        SpecialPriceAct act = productMapper.getSpecialPriceAct(id);
        ThrowBusinessExceptionUtil.checkNull(act, "特价活动不存在");
        ListResult listResult = new ListResult(productMapper.selectSpecialPriceActProducts(id, act.getStartTime(), act.getEndTime(), pageUtil));
        listResult.setByPageUtil(pageUtil);
        return new ResultData(listResult);
    }

    /**
     * @param projectId
     * @param storeId
     * @param designerId
     * @param specialPriceFlag 可以为null
     * @param pageUtil
     * @return
     */
    private ResultData getProductList(Long projectId,Long categoryId, Long storeId, Long designerId, Boolean specialPriceFlag, PageUtil pageUtil) {
        List<ProductSimple> list;
        if (storeId == null && designerId == null) {
            list = productMapper.selectByProject(projectId, categoryId, specialPriceFlag, pageUtil);
        } else {
            list = productMapper.selectByFilter(storeId, designerId, specialPriceFlag, pageUtil);
        }
        ListResult listResult = new ListResult(list);
        listResult.setByPageUtil(pageUtil);
        return new ResultData(listResult);
    }

}
