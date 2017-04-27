package com.joycity.joyclub.client.mapper;


import com.joycity.joyclub.client.modal.Client;
import com.joycity.joyclub.client.modal.ClientExample;
import com.joycity.joyclub.client.modal.WechatUserInfo;
import com.joycity.joyclub.commons.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by CallMeXYZ on 2017/3/29.
 */
public interface ClientUserMapper extends BaseMapper<Client, Long, ClientExample> {
    @Select("select id from client where tel=#{tel}")
    Long getIdByTel(String tel);
    @Select("select vip_code from client where id=#{id}")
    String getVipCodeById(Long id);
    @Select("select id from client where vip_code=#{vipCode} limit 0,1")
    Long getIdByVipCode(String vipCode);
    @Select("select wx_head_img_url headimgurl,wx_nick_name nickName,wx_gender sex,wx_language language,wx_city city, wx_country country,wx_province province from client where id=#{id}")
    WechatUserInfo getWechatInfo(Long id);
    @Select("select vip_point from client where id=#{id}")
    Integer getPoint(Long id);

    @Select("select tel from client where id=#{id}")
    String getTel(Long id);
    @Select("update client set vip_point=#{point}  where id=#{id}")
    Integer setPoint(@Param("id") Long id,@Param("point") Integer point);
}
