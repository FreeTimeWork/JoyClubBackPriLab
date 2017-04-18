package com.joycity.joyclub.apifront.mapper.manual;

import com.joycity.joyclub.apifront.modal.client.Client;
import com.joycity.joyclub.apifront.modal.client.ClientExample;
import com.joycity.joyclub.apifront.modal.wechat.WechatUserInfo;
import com.joycity.joyclub.commons.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * Created by CallMeXYZ on 2017/3/29.
 */
public interface ClientUserMapper extends BaseMapper<Client, Long, ClientExample> {
    @Select("select id from client where tel=#{tel}")
    Long getIdByTel(String tel);
    @Select("select vip_code from client where id=#{id}")
    String getVipCodeById(Long id);
    @Select("select wx_head_img_url headimgurl,wx_nick_name nickName,wx_gender sex,wx_language language,wx_city city, wx_country country,wx_province province from client where id=#{id}")
    WechatUserInfo getWechatInfo(Long id);



}
