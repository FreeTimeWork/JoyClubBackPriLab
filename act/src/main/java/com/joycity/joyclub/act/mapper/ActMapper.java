package com.joycity.joyclub.act.mapper;

import com.joycity.joyclub.act.modal.ActInfoForOrder;
import com.joycity.joyclub.act.modal.ActSimple;
import com.joycity.joyclub.act.modal.ActWithCategoryName;
import com.joycity.joyclub.act.modal.generated.SaleAct;
import com.joycity.joyclub.act.modal.generated.SaleActExample;
import com.joycity.joyclub.act.modal.generated.SaleActWithBLOBs;
import com.joycity.joyclub.commons.mapper.BaseMapperWithBLOBS;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/5/17.
 */
public interface ActMapper extends BaseMapperWithBLOBS<SaleAct, SaleActWithBLOBs, Long, SaleActExample> {
    //////////////////////////api back//////////////////
    Long countByStoreIdAndName(@Param("storeId") Long storeId, @Param("nameLike") String nameLike, @Param("pageUtil") PageUtil pageUtil);

    List<ActWithCategoryName> selectByStoreIdAndName(@Param("storeId") Long storeId, @Param("nameLike") String nameLike, @Param("pageUtil") PageUtil pageUtil);

    //////////////////////////api front//////////////////
    @Select("select  * from sale_act where id =#{id}")
    SaleAct getById(Long id);

    /**
     * projectId,storeId都可以为空
     * 都空是查询所有
     */
    List<ActSimple> selectSimpleList(@Param("projectId") Long projectId, @Param("storeId") Long storeId, @Param("pageUtil") PageUtil pageUtil);

    /**
     *
     * @param attrId
     * @return 返回值不包含price,pointRate,buyType信息
     */
    @Select(
            "select act.store_id storeId,act.id actId,attr.id attrId,act.portrait, act.name actName,attr.num,attr.name attrName,act.base_price ,cate.name categoryName,cate.point_rate basePointRate" +
                    " from sale_act_attr attr " +
                    " join sale_act act on attr.act_id = act.id" +
                    " join sys_act_category cate on cate.id = act.category_id" +
                    " where attr.id=#{attrId}")
    ActInfoForOrder getActInfoForOrderByAttr(@Param("attrId") Long attrId);
}
