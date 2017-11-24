package com.joycity.joyclub.product.mapper;

import java.util.Date;
import java.util.List;

import com.joycity.joyclub.commons.mapper.BaseMapperWithBLOBS;
import com.joycity.joyclub.commons.utils.PageUtil;
import com.joycity.joyclub.product.modal.*;
import com.joycity.joyclub.product.modal.generated.SaleProduct;
import com.joycity.joyclub.product.modal.generated.SaleProductExample;
import com.joycity.joyclub.product.modal.generated.SaleProductWithBLOBs;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by CallMeXYZ on 2017/3/29.
 */
public interface ProductMapper extends BaseMapperWithBLOBS<SaleProduct, SaleProductWithBLOBs, Long, SaleProductExample> {
    ///////////////////////////////// api back//////////////////////////////////////////////


    Long countByStoreIdAndName(@Param("storeId") Long storeId, @Param("nameLike") String nameLike, @Param("pageUtil") PageUtil pageUtil);

    List<ProductWithCategoryAndDesignerName> selectByStoreIdAndName(@Param("storeId") Long storeId, @Param("nameLike") String nameLike, @Param("pageUtil") PageUtil pageUtil);

    Long countByProductNameAndStoreName(@Param("projectId") Long projectId, @Param("productName") String productName, @Param("storeName") String storeName, @Param("pageUtil") PageUtil pageUtil);

    List<ProductWithStoreName> selectByProductNameAndStoreName(@Param("projectId") Long projectId, @Param("productName") String productName, @Param("storeName") String storeName, @Param("pageUtil") PageUtil pageUtil);



    ///////////////////////////////// api front//////////////////////////////////////////////

    ProductInfoPage getInfo(Long id);

    List<ProductSimple> selectProductSimpleByIds(@Param("ids") String[] productIds);

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
            @Param("categoryId") Long categoryId,
            @Param("specialPriceFlag") Boolean specialPriceFlag,
            @Param("pageUtil") PageUtil pageUtil
    );
    Long countByProject(
            @Param("projectId") Long selectByProject,
            @Param("categoryId") Long categoryId,
            @Param("specialPriceFlag") Boolean specialPriceFlag
    );

    @Select("select * from sale_special_price_act where id =#{id}")
    SpecialPriceAct getSpecialPriceAct(@Param("id") Long id);
    @Select("select * from sale_special_price_act where project_id =#{projectId} order by create_time desc limit 0,1")
    SpecialPriceAct getProjectLatestSpecialPriceAct(@Param("projectId") Long projectId);
    /**
     * 获取某个秒杀活动下所有商品的情况
     * 如果没有特价上架审核通过，则该商品不返回，
     * 如果活动区间里有多个特价，则商品的价格显示为最新添加的审核通过的价格
     *
     * @param specialPriceActId 特价活动的id，会根据特价活动与商品关系表 搜索商品
     * @param actStartTime      特价活动的开始时间，在特价活动时间区间寻找对应的特价
     * @param actEndTime        特价活动的结束时间
     * @return
     */
    List<ProductSimple> selectSpecialPriceActProducts(
            @Param("id") Long specialPriceActId,
            @Param("startTime") Date actStartTime,
            @Param("endTime") Date actEndTime,
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
