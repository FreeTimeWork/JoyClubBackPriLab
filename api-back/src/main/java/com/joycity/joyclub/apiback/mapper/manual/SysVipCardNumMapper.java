package com.joycity.joyclub.apiback.mapper.manual;

import com.joycity.joyclub.apiback.mapper.BaseMapper;
import com.joycity.joyclub.apiback.modal.vipcardnum.VipCardNumInfo;
import com.joycity.joyclub.apiback.modal.generated.SysVipCardNum;
import com.joycity.joyclub.apiback.modal.generated.SysVipCardNumExample;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/3/29.
 */
public interface SysVipCardNumMapper extends BaseMapper<SysVipCardNum, Long, SysVipCardNumExample> {

    Long countWithFilter(@Param("projectId") Long projectId,
                         @Param("batch") String batch,
                         @Param("type") String type,
                         @Param("status") Byte status);

    /**
     * 按照默认排序
     * @param projectId
     * @param batch
     * @param type
     * @param status
     * @param pageUtil
     * @return
     */
    List<VipCardNumInfo> selectWithFilter(@Param("projectId") Long projectId,
                                          @Param("batch") String batch,
                                          @Param("type") String type,
                                          @Param("status") Byte status,
                                          @Param("pageUtil") PageUtil pageUtil);

    /**
     * 使用<code>${}</code>语法，可能会导致sql注入
     * @param values (num,project_id,batch,type,status),字符串，多个值用逗号分隔
     * @return
     */
    @Insert("insert into sys_vip_card_num(num,project_id,batch,type,status) values ${values}")
    Integer insertWithValues(@Param("values") String values);

    /**
     * 按id倒序
     * @return
     */
    @Select("select distinct batch from sys_vip_card_num  order by id desc")
    List<String> getBatches();
}
