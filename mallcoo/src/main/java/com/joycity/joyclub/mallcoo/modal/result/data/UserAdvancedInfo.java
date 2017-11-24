package com.joycity.joyclub.mallcoo.modal.result.data;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 猫酷返回的高级用户信息
 */
public class UserAdvancedInfo extends  UserSimpleInfo{
    /* 猫酷会员卡号 */
    private String mallCardNo;
    /* 猫酷会员卡名称 */
    private String mallCardName;
    /* 第三方会员ID */
    private String thirdPartyCardID;
    /* 第三方会员卡号 */
    private String thirdPartyCardNo;
    /* 积分 */
    private String score;
    /* 猫酷会员卡类型ID */
    private Long mallCardTypeID;

    public String getMallCardNo() {
        return mallCardNo;
    }

    public void setMallCardNo(String mallCardNo) {
        this.mallCardNo = mallCardNo;
    }

    public String getMallCardName() {
        return mallCardName;
    }

    public void setMallCardName(String mallCardName) {
        this.mallCardName = mallCardName;
    }

    public String getThirdPartyCardID() {
        return thirdPartyCardID;
    }

    public void setThirdPartyCardID(String thirdPartyCardID) {
        this.thirdPartyCardID = thirdPartyCardID;
    }

    public String getThirdPartyCardNo() {
        return thirdPartyCardNo;
    }

    public void setThirdPartyCardNo(String thirdPartyCardNo) {
        this.thirdPartyCardNo = thirdPartyCardNo;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Long getMallCardTypeID() {
        return mallCardTypeID;
    }

    public void setMallCardTypeID(Long mallCardTypeID) {
        this.mallCardTypeID = mallCardTypeID;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
