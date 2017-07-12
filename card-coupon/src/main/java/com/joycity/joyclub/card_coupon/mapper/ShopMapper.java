package com.joycity.joyclub.card_coupon.mapper;

import com.joycity.joyclub.card_coupon.modal.generated.SysShop;
import com.joycity.joyclub.card_coupon.modal.generated.SysShopExample;
import com.joycity.joyclub.commons.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by fangchen.chai on 2017/7/11.
 */
public interface ShopMapper extends BaseMapper<SysShop, Long, SysShopExample> {

    int batchInsertShop(@Param("sql") String sql);

}
