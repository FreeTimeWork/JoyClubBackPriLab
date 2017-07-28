package com.joycity.joyclub.mallcoo.modal.result.data;

/**
 * Created by CallMeXYZ on 2017/7/28.
 */
public class UserSimpleInfo {
    private String OpenUserId;
    private String NickName;
    private String Avatar;
    private String Mobile;
    private String UserToken;
    private String WXOpenID;
    private Integer Expires;
    private String UserName;
    private Integer Gender;
    private Integer Age;
    private String Birthday;

    public String getOpenUserId() {
        return OpenUserId;
    }

    public void setOpenUserId(String openUserId) {
        OpenUserId = openUserId;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getUserToken() {
        return UserToken;
    }

    public void setUserToken(String userToken) {
        UserToken = userToken;
    }

    public String getWXOpenID() {
        return WXOpenID;
    }

    public void setWXOpenID(String WXOpenID) {
        this.WXOpenID = WXOpenID;
    }

    public Integer getExpires() {
        return Expires;
    }

    public void setExpires(Integer expires) {
        Expires = expires;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public Integer getGender() {
        return Gender;
    }

    public void setGender(Integer gender) {
        Gender = gender;
    }

    public Integer getAge() {
        return Age;
    }

    public void setAge(Integer age) {
        Age = age;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }
}
