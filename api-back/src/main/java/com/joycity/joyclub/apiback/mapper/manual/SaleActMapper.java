package com.joycity.joyclub.apiback.mapper.manual;

import com.joycity.joyclub.commons.mapper.BaseMapperWithBLOBS;
import com.joycity.joyclub.apiback.modal.generated.SaleAct;
import com.joycity.joyclub.apiback.modal.generated.SaleActExample;
import com.joycity.joyclub.apiback.modal.generated.SaleActWithBLOBs;
import com.joycity.joyclub.apiback.modal.act.ActWithCategoryName;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/3/29.
 */
public interface SaleActMapper extends BaseMapperWithBLOBS<SaleAct,SaleActWithBLOBs, Long, SaleActExample> {


    Long countByStoreIdAndName(@Param("storeId") Long storeId, @Param("nameLike") String nameLike, @Param("pageUtil") PageUtil pageUtil);

    List<ActWithCategoryName> selectByStoreIdAndName(@Param("storeId") Long storeId, @Param("nameLike") String nameLike, @Param("pageUtil") PageUtil pageUtil);

}
