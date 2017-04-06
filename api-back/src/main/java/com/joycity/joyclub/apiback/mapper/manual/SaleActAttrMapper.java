package com.joycity.joyclub.apiback.mapper.manual;

import com.joycity.joyclub.apiback.mapper.BaseMapper;
import com.joycity.joyclub.apiback.modal.generated.SaleActAttr;
import com.joycity.joyclub.apiback.modal.generated.SaleActAttrExample;
import com.joycity.joyclub.apiback.modal.act.attr.ActAttrWithActName;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/3/29.
 */
public interface SaleActAttrMapper extends BaseMapper<SaleActAttr, Long, SaleActAttrExample> {
    Long countByStoreIdAndActName(@Param("storeId") Long storeId, @Param("nameLike") String nameLike, @Param("pageUtil") PageUtil pageUtil);

    List<ActAttrWithActName> selectByStoreIdAndActName(@Param("storeId") Long storeId, @Param("nameLike") String nameLike, @Param("pageUtil") PageUtil pageUtil);

}
