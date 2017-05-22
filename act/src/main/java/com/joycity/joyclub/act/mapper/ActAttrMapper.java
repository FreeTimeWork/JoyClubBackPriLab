package com.joycity.joyclub.act.mapper;

import com.joycity.joyclub.act.modal.ActAttrSimple;
import com.joycity.joyclub.act.modal.ActAttrWithActName;
import com.joycity.joyclub.act.modal.generated.SaleActAttr;
import com.joycity.joyclub.act.modal.generated.SaleActAttrExample;
import com.joycity.joyclub.commons.mapper.BaseMapper;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/5/17.
 */
public interface ActAttrMapper extends BaseMapper<SaleActAttr,Long,SaleActAttrExample> {
    Long countByStoreIdAndActName(@Param("storeId") Long storeId, @Param("nameLike") String nameLike, @Param("pageUtil") PageUtil pageUtil);

    List<ActAttrWithActName> selectByStoreIdAndActName(@Param("storeId") Long storeId, @Param("nameLike") String nameLike, @Param("pageUtil") PageUtil pageUtil);
    /**
     * 返回有库存的属性
     * 数据库默认排序，
     *
     * @param actId
     * @return
     */
    @Select("select id,act_id,name,num from sale_act_attr where act_id=#{actId} and num>0")
    List<ActAttrSimple> selectAvailableSimple(Long actId);

    @Update("update sale_act_attr set num= num + #{addNum} where id = #{attrId}")
    Integer addNum(@Param("attrId") Long attrId, @Param("addNum") Integer addNum);

}
