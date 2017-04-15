package com.joycity.joyclub.apiback.mapper.manual;

import com.joycity.joyclub.commons.mapper.BaseMapperWithBLOBS;
import com.joycity.joyclub.apiback.modal.base.IdName;
import com.joycity.joyclub.apiback.modal.generated.SaleStoreDesigner;
import com.joycity.joyclub.apiback.modal.generated.SaleStoreDesignerExample;
import com.joycity.joyclub.apiback.modal.generated.SaleStoreDesignerWithBLOBs;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/3/29.
 */
public interface SaleStoreDesignerMapper extends BaseMapperWithBLOBS<SaleStoreDesigner,SaleStoreDesignerWithBLOBs, Long, SaleStoreDesignerExample> {
    @Select("select id,name from sale_store_designer where delete_flag=0 and store_id=#{storeId}")
    List<IdName> getSimpleListByStoreId(Long storeId);
}
