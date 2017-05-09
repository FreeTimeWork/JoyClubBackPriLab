package com.joycity.joyclub.commons.mapper.manual;

import com.joycity.joyclub.commons.mapper.BaseMapperWithBLOBS;
import com.joycity.joyclub.commons.modal.base.IdName;
import com.joycity.joyclub.commons.modal.base.IdNamePortrait;
import com.joycity.joyclub.commons.modal.designer.DesignerInfoPage;
import com.joycity.joyclub.commons.modal.designer.SimpleDesigner;
import com.joycity.joyclub.commons.modal.generated.SaleStoreDesigner;
import com.joycity.joyclub.commons.modal.generated.SaleStoreDesignerExample;
import com.joycity.joyclub.commons.modal.generated.SaleStoreDesignerWithBLOBs;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/3/29.
 */
public interface SaleStoreDesignerMapper extends BaseMapperWithBLOBS<SaleStoreDesigner,SaleStoreDesignerWithBLOBs, Long, SaleStoreDesignerExample> {
    @Select("select id,name from sale_store_designer where delete_flag=0 and store_id=#{storeId}")
    List<IdName> getSimpleListByStoreId(Long storeId);

    @Select("select id,name,portrait from sale_store_designer where id = #{id}")
    IdNamePortrait getSimpleInfo(Long id);

    @Select("select  id, store_id, name, head_img,portrait, intro, html_content from sale_store_designer where delete_flag=0 and id =#{id}")
    DesignerInfoPage getInfo(Long id);

    @Select("select id,name,portrait from sale_store_designer where delete_flag=0")
    List<SimpleDesigner> getSimpleList();

    @Select("select id,name,portrait from sale_store_designer where delete_flag=0 and store_id = #{storeId}")
    List<SimpleDesigner> getSimpleListByStore(Long storeId);

    @Select("select d.id,d.name,d.portrait from sale_store_designer d left join sys_store s on s.id=d.store_id  where d.delete_flag=0 and s.project_id = #{projectId}")
    List<SimpleDesigner> getSimpleListByProject(Long projectId);

}
