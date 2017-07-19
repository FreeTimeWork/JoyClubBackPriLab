package com.joycity.joyclub.card_coupon.mapper;

import java.util.List;

import com.joycity.joyclub.card_coupon.modal.SubCommercialShopInfo;
import com.joycity.joyclub.card_coupon.modal.generated.SysShop;
import com.joycity.joyclub.card_coupon.modal.generated.SysShopExample;
import com.joycity.joyclub.commons.mapper.BaseMapper;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.apache.ibatis.annotations.Param;

/**
 * Created by fangchen.chai on 2017/7/11.
 */
public interface ShopMapper extends BaseMapper<SysShop, Long, SysShopExample> {

    int batchInsertShop(@Param("sql") String sql);

    Long countShopByCodeAndSubCommercial(@Param("projectId") Long projectId, @Param("code") String code, @Param("name") String name, @Param("pageUtil") PageUtil pageUtil);

    List<SysShop> selectShopByCodeAndSubCommercial(@Param("projectId") Long projectId, @Param("code") String code, @Param("name") String name, @Param("pageUtil") PageUtil pageUtil);

    List<SubCommercialShopInfo> selectShopGroupBySubCommercial();
}
