package com.joycity.joyclub.we_chat.modal;

import org.apache.commons.lang3.StringUtils;

/**
 * 微信获取用户信息的返回对象
 * 示例json格式
 * <pre>
 {
 "subscribe": 1,
 "openid": "o6_bmjrPTlm6_2sgVt7hMZOPfL2M",
 "nickname": "Band",
 "sex": 1,
 "language": "zh_CN",
 "city": "广州",
 "province": "广东",
 "country": "中国",
 "headimgurl":    "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0",
 "subscribe_time": 1382694957,
 "unionid": " o6_bmasdasdsad6_2sgVt7hMZOPfL"
 "remark": "",
 "groupid": 0
 }
 * <pre/>
 * Created by CallMeXYZ on 2017/4/7.
 */
public class WechatUserInfo {
    private String openid;

    private String access_token;

    private String nickname;

    private String sex;

    private String language;

    private String city;

    private String province;

    private String country;

    private String headimgurl;
/*    *//**
     * 是否订阅
     *//*
    private Boolean subscribe;
    private Integer subscribe_time;*/

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        if(StringUtils.isNotBlank(nickname)) {
            this.nickname = nickname.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "*"); //过滤emoji表情
        }else {
            this.nickname = nickname;
        }
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }


}
