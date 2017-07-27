package com.joycity.joyclub.card_coupon.mapper;

import com.joycity.joyclub.card_coupon.modal.generated.CardThirdPartyShop;
import com.joycity.joyclub.card_coupon.modal.generated.CardThirdPartyShopExample;
import com.joycity.joyclub.commons.mapper.BaseMapper;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by fangchen.chai on 2017/7/26
 */
public interface CardThirdPartyShopMapper extends BaseMapper<CardThirdPartyShop, Long, CardThirdPartyShopExample> {

    List<CardThirdPartyShop> selectListByName(@Param("projectId") Long projectId,@Param("name") String name, @Param("pageUtil") PageUtil pageUtil);

    Long countListByName(@Param("projectId") Long projectId, @Param("name") String name);

}
