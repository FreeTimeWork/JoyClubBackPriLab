package com.joycity.joyclub.apiback.mapper.manual;

import com.joycity.joyclub.commons.mapper.BaseMapper;
import com.joycity.joyclub.apiback.modal.generated.SaleActPrice;
import com.joycity.joyclub.apiback.modal.generated.SaleActPriceExample;
import com.joycity.joyclub.apiback.modal.act.ActWithCategoryName;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by CallMeXYZ on 2017/3/29.
 */
public interface SaleActPriceMapper extends BaseMapper<SaleActPrice, Long, SaleActPriceExample> {

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
    Long countForStore(@Param("storeId") Long storeId, @Param("reviewStatus") Integer reviewStatus, @Param("actNameLike") String actNameLike, @Param("pageUtil") PageUtil pageUtil);

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
    List<ActWithCategoryName> selectForStore(@Param("storeId") Long storeId, @Param("reviewStatus") Integer reviewStatus, @Param("actNameLike") String actNameLike, @Param("pageUtil") PageUtil pageUtil);

    /**
     * 为商户用户计算总数
     * By StoreName,ActName,ReviewStatus
     *
     * @param reviewStatus
     * @param actNameLike
     * @param pageUtil
     * @return
     */
    Long countForProject(@Param("storeNameLike") String storeNameLike, @Param("reviewStatus") Integer reviewStatus, @Param("actNameLike") String actNameLike, @Param("pageUtil") PageUtil pageUtil);

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
    List<ActWithCategoryName> selectForProject(@Param("storeNameLike") String storeNameLike, @Param("reviewStatus") Integer reviewStatus, @Param("actNameLike") String actNameLike, @Param("pageUtil") PageUtil pageUtil);

    /**
     * @param id 如果提供id 就排除跟该id的冲突检查
     * @param actId
     * @param statTime
     * @param endTime
     * @return
     */
    Long countPriceTimeOverlap(@Param("id") Long id,@Param("actId") Long actId,@Param("startTime") Date statTime, @Param("endTime") Date endTime );

}
