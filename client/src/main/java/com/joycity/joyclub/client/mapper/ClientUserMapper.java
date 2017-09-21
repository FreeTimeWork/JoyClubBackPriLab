package com.joycity.joyclub.client.mapper;


import com.joycity.joyclub.client.modal.Client;
import com.joycity.joyclub.client.modal.ClientExample;
import com.joycity.joyclub.commons.mapper.BaseMapper;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;

/**
 * Created by CallMeXYZ on 2017/3/29.
 */
public interface ClientUserMapper extends BaseMapper<Client, Long, ClientExample> {
    @Select("select id from client where tel=#{tel}")
    Long getIdByTel(String tel);

    @Select("select vip_code from client where id=#{id} limit 1")
    String getVipCodeById(Long id);

    @Select("select id from client where vip_code=#{vipCode} limit 1")
    Long getIdByVipCode(String vipCode);

    @Select("select wx_head_img_url headimgurl,wx_nick_name nickName,wx_gender sex,wx_language language,wx_city city, wx_country country,wx_province province from client where id=#{id}")
    HashMap<String,String> getWechatInfo(Long id);

    @Select("select vip_point from client where id=#{id}")
    Integer getPoint(Long id);

    @Select("select tel from client where id=#{id}")
    String getTel(Long id);

    @Select("update client set vip_point=#{point}  where id=#{id}")
    Integer setPoint(@Param("id") Long id, @Param("point") Integer point);

    /**
     * vipNo,cardNo,phone
     */
    List<Client> getListForBack(
            /*@Param("group13") String group13,*/
            @Param("cardType") String cardType,
            @Param("pointStart") Integer pointStart,
            @Param("pointEnd") Integer pointEnd,
            @Param("vipNo") String vipNo,
            @Param("cardNo") String cardNo,
            @Param("phone") String phone,
            @Param("pageUtil") PageUtil pageUtil
    );

    /**
     * vipNo,cardNo,phone
     */
    Long countForBack(
           /* @Param("group13") String group13,*/
           @Param("cardType") String cardType,
           @Param("pointStart") Integer pointStart,
           @Param("pointEnd") Integer pointEnd,
           @Param("vipNo") String vipNo,
           @Param("cardNo") String cardNo,
           @Param("phone") String phone,
           @Param("pageUtil") PageUtil pageUtil
    );

    // TODO: 2017/9/21 用完删掉
    /**
     * 临时sql,为了从本地数据库中查找出crmId
     */
    List<String> getCrmId(@Param("pageUtil") PageUtil pageUtil);
}
