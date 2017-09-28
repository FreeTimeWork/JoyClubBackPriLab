package com.joycity.joyclub.act.mapper;

import com.joycity.joyclub.act.modal.ActPriceWithActInfo;
import com.joycity.joyclub.act.modal.ActWithCategoryName;
import com.joycity.joyclub.act.modal.generated.SaleActPrice;
import com.joycity.joyclub.act.modal.generated.SaleActPriceExample;
import com.joycity.joyclub.commons.mapper.BaseMapper;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * Created by CallMeXYZ on 2017/5/17.
 */
public interface ActPriceMapper extends BaseMapper<SaleActPrice, Long, SaleActPriceExample> {
    /**
     * 为商户用户计算总数
     * By StoreId,ActName,ReviewStatus
     *
     * @param storeId
     * @param reviewStatus
     * @param actNameLike
     * @param pageUtil
     * @return
     */
    Long countForStore(
            @Param("storeId") Long storeId,
            @Param("reviewStatus") Integer reviewStatus,
            @Param("actNameLike") String actNameLike,
            @Param("buyType") Byte  buyType,
            @Param("pageUtil") PageUtil pageUtil);

    /**
     * 为商户用户获取list
     * By StoreId,ActName,ReviewStatus
     *
     * @param storeId
     * @param reviewStatus
     * @param actNameLike
     * @param pageUtil
     * @return
     */
    List<ActWithCategoryName> selectForStore(
            @Param("storeId") Long storeId,
            @Param("reviewStatus") Integer reviewStatus,
            @Param("actNameLike") String actNameLike,
            @Param("buyType") Byte  buyType,
            @Param("pageUtil") PageUtil pageUtil);

    /**
     * 为商户用户计算总数
     * By StoreName,ActName,ReviewStatus
     *
     * @param reviewStatus
     * @param actNameLike
     * @param pageUtil
     * @return
     */
    Long countForProject(
            @Param("projectId") Long projectId,
            @Param("storeNameLike") String storeNameLike,
            @Param("reviewStatus") Integer reviewStatus,
            @Param("actNameLike") String actNameLike,
            @Param("buyType") Byte  buyType,
            @Param("pageUtil") PageUtil pageUtil);

    /**
     * 为商户用户获取list
     * By projectName,ActName,ReviewStatus
     *
     * @param storeNameLike
     * @param reviewStatus
     * @param actNameLike
     * @param pageUtil
     * @return
     */
    List<ActPriceWithActInfo> selectForProject(
            @Param("projectId") Long projectId,
            @Param("storeNameLike") String storeNameLike,
            @Param("reviewStatus") Integer reviewStatus,
            @Param("actNameLike") String actNameLike,
            @Param("buyType") Byte  buyType,
            @Param("pageUtil") PageUtil pageUtil);

    /**
     * @param id       如果提供id 就排除跟该id的冲突检查
     * @param actId
     * @param statTime
     * @param endTime
     * @return
     */
    Long countPriceTimeOverlap(@Param("id") Long id, @Param("actId") Long actId, @Param("startTime") Date statTime, @Param("endTime") Date endTime);
/////////////////////////////api front////////////////////////

    /**
     * 获得商品现在的价格，可能会不存在
     *
     * @param actId
     * @return
     */
    @Select("select * from sale_act_price where delete_flag=0 and  act_id=#{actId} and forbid_flag=false and review_status=1 and start_time<=now() and end_time>=now() limit 0,1 ")
    SaleActPrice getNowPrice(@Param("actId") Long actId);

    @Select("select * from sale_act_price where delete_flag=0 and  act_id=#{actId} and forbid_flag=false and review_status=1  limit 0,1 ")
    SaleActPrice getPrice(@Param("actId") Long actId);
}
