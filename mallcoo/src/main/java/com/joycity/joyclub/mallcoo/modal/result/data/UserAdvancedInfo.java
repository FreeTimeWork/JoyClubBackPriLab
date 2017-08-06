package com.joycity.joyclub.mallcoo.modal.result.data;

/**
 * 猫酷返回的高级用户信息
 */
public class UserAdvancedInfo extends  UserSimpleInfo{
    /* 猫酷会员卡号 */
    private String MallCardNo;
    /* 猫酷会员卡名称 */
    private String MallCardName;
    /* 第三方会员ID */
    private String ThirdPartyCardID;
    /* 第三方会员卡号 */
    private String ThirdPartyCardNo;
    /* 积分 */
    private String Score;
    /* 猫酷会员卡类型ID */
    private Long MallCardTypeID;

    public String getMallCardNo() {
        return MallCardNo;
    }

    public void setMallCardNo(String mallCardNo) {
        MallCardNo = mallCardNo;
    }

    public String getMallCardName() {
        return MallCardName;
    }

    public void setMallCardName(String mallCardName) {
        MallCardName = mallCardName;
    }

    public String getThirdPartyCardID() {
        return ThirdPartyCardID;
    }

    public void setThirdPartyCardID(String thirdPartyCardID) {
        ThirdPartyCardID = thirdPartyCardID;
    }

    public String getThirdPartyCardNo() {
        return ThirdPartyCardNo;
    }

    public void setThirdPartyCardNo(String thirdPartyCardNo) {
        ThirdPartyCardNo = thirdPartyCardNo;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }

    public Long getMallCardTypeID() {
        return MallCardTypeID;
    }

    public void setMallCardTypeID(Long mallCardTypeID) {
        MallCardTypeID = mallCardTypeID;
    }
}
