package com.joycity.joyclub.product.mapper;

import com.joycity.joyclub.product.modal.ProductInfoPage;
import com.joycity.joyclub.product.modal.ProductSimple;
import com.joycity.joyclub.product.modal.generated.SaleProduct;
import com.joycity.joyclub.product.modal.generated.SaleProductExample;
import com.joycity.joyclub.product.modal.generated.SaleProductWithBLOBs;
import com.joycity.joyclub.product.modal.ProductWithCategoryAndDesignerName;
import com.joycity.joyclub.commons.mapper.BaseMapperWithBLOBS;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/3/29.
 */
public interface ProductMapper extends BaseMapperWithBLOBS<SaleProduct, SaleProductWithBLOBs, Long, SaleProductExample> {
    ///////////////////////////////// api back//////////////////////////////////////////////


    Long countByStoreIdAndName(@Param("storeId") Long storeId, @Param("nameLike") String nameLike, @Param("pageUtil") PageUtil pageUtil);

    List<ProductWithCategoryAndDesignerName> selectByStoreIdAndName(@Param("storeId") Long storeId, @Param("nameLike") String nameLike, @Param("pageUtil") PageUtil pageUtil);

    ///////////////////////////////// api front//////////////////////////////////////////////

    ProductInfoPage getInfo(Long id);

    /**
     * @param storeId
     * @param designerId
     * @param specialPriceFlag 可以为null
     * @param pageUtil
     * @return
     */
    List<ProductSimple> selectByFilter(
            @Param("storeId") Long storeId,
            @Param("designerId") Long designerId,
            @Param("specialPriceFlag") Boolean specialPriceFlag,
            @Param("pageUtil") PageUtil pageUtil
    );

    /**
     * @param selectByProject
     * @param specialPriceFlag 可以为null
     * @param pageUtil
     * @return
     */
    List<ProductSimple> selectByProject(
            @Param("projectId") Long selectByProject,
            @Param("specialPriceFlag") Boolean specialPriceFlag,
            @Param("pageUtil") PageUtil pageUtil
    );

    /**
     * 某个会员的某个商品的所有未取消订单的数量，包括已支付和未支付，但是不包括未取消
     */
    @Select("select count(*)" +
            "  from sale_product_order_detail d" +
            "  LEFT JOIN  `sale_product_attr`  attr on attr.id= d.`product_attr`" +
            "  left join `sale_product` pro on pro.`id`= attr.product_id" +
            "  left join `sale_product_order_store` so on so.`id`= d.`store_order_id`" +
            "  LEFT JOIN `sale_product_order` mo on mo.`id`= so.`order_id`" +
            " where mo.`client_id`= #{clientId}" +
            "   and pro.`id`= #{productId} and mo.status in (0,2)")
    Integer countProductNotCanceledOrder(@Param("productId") Long productId, @Param("clientId") Long clientId);
}
