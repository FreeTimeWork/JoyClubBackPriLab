package com.joycity.joyclub.act.mapper;

import java.util.List;

import com.joycity.joyclub.act.modal.*;
import com.joycity.joyclub.act.modal.generated.SaleAct;
import com.joycity.joyclub.act.modal.generated.SaleActExample;
import com.joycity.joyclub.act.modal.generated.SaleActWithBLOBs;
import com.joycity.joyclub.commons.mapper.BaseMapperWithBLOBS;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Created by CallMeXYZ on 2017/5/17.
 */
public interface ActMapper extends BaseMapperWithBLOBS<SaleAct, SaleActWithBLOBs, Long, SaleActExample> {
    //////////////////////////api back//////////////////
    Long countByStoreIdAndName(@Param("storeId") Long storeId, @Param("nameLike") String nameLike, @Param("pageUtil") PageUtil pageUtil);

    List<ActWithCategoryName> selectByStoreIdAndName(@Param("storeId") Long storeId, @Param("nameLike") String nameLike, @Param("pageUtil") PageUtil pageUtil);

    Long countByActNameAndStoreName(@Param("projectId") Long projectId, @Param("actName") String actName, @Param("storeName") String storeName, @Param("pageUtil") PageUtil pageUtil);

    List<ActWithStoreName> selectByActNameAndStoreName(@Param("projectId") Long projectId, @Param("actName") String actName, @Param("storeName") String storeName, @Param("pageUtil") PageUtil pageUtil);


    //////////////////////////api front//////////////////
    @Select("select  * from sale_act where id =#{id}")
    SaleAct getById(Long id);

    /**
     * projectId,storeId都可以为空
     * 都空是查询所有
     */
    List<ActSimple> selectSimpleList(@Param("projectId") Long projectId, @Param("storeId") Long storeId,
                                     @Param("actTypeId") Long actTypeId, @Param("history") boolean history, @Param("pageUtil") PageUtil pageUtil);
    Long countSimpleList(@Param("projectId") Long projectId, @Param("storeId") Long storeId,
                                     @Param("actTypeId") Long actTypeId, @Param("history") boolean history);

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


    /**
     * 查找我的活动
     */

    List<MineAct> getListMineAct(@Param("clientId") Long clientId);

    /**
     * 我参与的活动
     */
    List<MineAct> getListMineJoinAct(@Param("clientId") Long clientId);


}
