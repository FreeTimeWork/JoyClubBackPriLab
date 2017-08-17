package com.joycity.joyclub.api_async.modal.kechuan;

import java.util.Date;

/**
 * KechuanClient
 *
 * @author CallMeXYZ
 * @date 2017/8/11
 */
public class KeChuanClient {
    /* 姓名(字符串，长度 32) 必填 */
    private String realName;
    /* 性别(0=未知，1=男，2=女) 必填 */
    private String gender;
    /* 证件号(字符串，长度 32) */
    private String idCode;
    /* 籍贯(字符串，长度 32) */
    private String nativePlace;
    /* 会员识别号(字符串，长度 64) 必填 */
    private String crmId;
    /* 手机号(字符串，长度 18) 必填*/
    private String phoneNumber;
    /* 通讯地址(字符串，长度 128) */
    private String address;
    /* 电子邮件(字符串，长度 128) */
    private String email;
    /* 生日(yyyy-MM-dd hh:mm:ss) */
    private String birthday;
    /* 会员类别(字符串，长度 64) */
    private String type;
    /* 加入时间(yyyy-MM-dd hh:mm:ss) 必填 */
    private Date joinTime;
    /* 失效时间(yyyy-MM-dd hh:mm:ss) */
    private Date expTime;
    /* 状态(0=正常，1=失效) */
    private Integer status;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getCrmId() {
        return crmId;
    }

    public void setCrmId(String crmId) {
        this.crmId = crmId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }

    public Date getExpTime() {
        return expTime;
    }

    public void setExpTime(Date expTime) {
        this.expTime = expTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
