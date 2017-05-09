package com.joycity.joyclub.product.mapper;


import com.joycity.joyclub.commons.mapper.BaseMapper;
import com.joycity.joyclub.commons.utils.PageUtil;
import com.joycity.joyclub.product.modal.ProductWithCategoryAndDesignerName;
import com.joycity.joyclub.product.modal.generated.SaleProductPrice;
import com.joycity.joyclub.product.modal.generated.SaleProductPriceExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * Created by CallMeXYZ on 2017/3/29.
 */
public interface ProductPriceMapper extends BaseMapper<SaleProductPrice, Long, SaleProductPriceExample> {
    ///////////////////////////////// api back//////////////////////////////////////////////

    /**
     * 为商户用户计算总数
     * By StoreId,ProductName,ReviewStatus
     *
     * @param storeId
     * @param reviewStatus
     * @param productNameLike
     * @param pageUtil
     * @return
     */
    Long countForStore(@Param("storeId") Long storeId, @Param("reviewStatus") Integer reviewStatus, @Param("productNameLike") String productNameLike, @Param("pageUtil") PageUtil pageUtil);

    /**
     * 为商户用户获取list
     * By StoreId,ProductName,ReviewStatus
     *
     * @param storeId
     * @param reviewStatus
     * @param productNameLike
     * @param pageUtil
     * @return
     */
    List<ProductWithCategoryAndDesignerName> selectForStore(@Param("storeId") Long storeId, @Param("reviewStatus") Integer reviewStatus, @Param("productNameLike") String productNameLike, @Param("pageUtil") PageUtil pageUtil);

    /**
     * 为商户用户计算总数
     * By StoreName,ProductName,ReviewStatus
     *
     * @param reviewStatus
     * @param productNameLike
     * @param pageUtil
     * @return
     */
    Long countForProject(@Param("storeNameLike") String storeNameLike, @Param("reviewStatus") Integer reviewStatus, @Param("productNameLike") String productNameLike, @Param("pageUtil") PageUtil pageUtil);

    /**
     * 为商户用户获取list
     * By projectName,ProductName,ReviewStatus
     *
     * @param storeNameLike
     * @param reviewStatus
     * @param productNameLike
     * @param pageUtil
     * @return
     */
    List<ProductWithCategoryAndDesignerName> selectForProject(@Param("storeNameLike") String storeNameLike, @Param("reviewStatus") Integer reviewStatus, @Param("productNameLike") String productNameLike, @Param("pageUtil") PageUtil pageUtil);

    /**
     * @param id        如果提供id 就排除跟该id的冲突检查
     * @param productId
     * @param statTime
     * @param endTime
     * @return
     */
    Long countPriceTimeOverlap(@Param("id") Long id, @Param("productId") Long productId, @Param("startTime") Date statTime, @Param("endTime") Date endTime);

    ///////////////////////////////// api front//////////////////////////////////////////////

    /**
     * 获得商品现在的价格，可能会不存在
     *
     * @param productId
     * @return
     */
    @Select("select * from sale_product_price where delete_flag=0 and  product_id=#{productId} and forbid_flag=0 and review_status=1 and start_time<=now() and end_time>=now() limit 0,1 ")
    SaleProductPrice getNowPrice(Long productId);

}
