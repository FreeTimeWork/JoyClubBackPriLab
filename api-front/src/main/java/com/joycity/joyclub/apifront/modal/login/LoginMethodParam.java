package com.joycity.joyclub.apifront.modal.login;

/**
 * Created by CallMeXYZ on 2017/5/12.
 */
public class LoginMethodParam {
    /**
     * 是发卡的项目id,商场项目使用自己的projectId
     */
    private Long cardProjectId;
    /**
     * 微信对应的项目id，商场项目的悦客会使用平台的，所以微信项目id使用平台id
     * 可以不提供，如果提供，则需要提供openId和accessToken
     */
    private Long openIdProjectId;
    /**
     * 可以为null，如果null,则不进行微信相关业务
     */
    private String openId;
    private String accessToken;

    private String phone;
    /**
     * 手机验证码 可以为null，如果提供，则在登录逻辑前先进行验证
     */
    private String authCode;



    private LoginMethodParam() {
    }

    public Long getCardProjectId() {
        return cardProjectId;
    }

    public void setCardProjectId(Long cardProjectId) {
        this.cardProjectId = cardProjectId;
    }

    public Long getOpenIdProjectId() {
        return openIdProjectId;
    }

    public void setOpenIdProjectId(Long openIdProjectId) {
        this.openIdProjectId = openIdProjectId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public static class LoginMethodParamBuilder {
        private Long cardProjectId;
        private Long openIdProjectId;
        private String openId;
        private String accessToken;
        private String phone;
        private String authCode;

        private LoginMethodParamBuilder() {
        }

        public static LoginMethodParamBuilder create() {
            return new LoginMethodParamBuilder();
        }

        public LoginMethodParamBuilder setCardProjectId(Long cardProjectId) {
            this.cardProjectId = cardProjectId;
            return this;
        }

        public LoginMethodParamBuilder setWechat(Long openIdProjectId,String openId,String accessToken) {
            this.openIdProjectId = openIdProjectId;
            this.openId = openId;
            this.accessToken = accessToken;
            return this;
        }
        public LoginMethodParamBuilder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public LoginMethodParamBuilder setAuthCode(String authCode) {
            this.authCode = authCode;
            return this;
        }

        public LoginMethodParam build() {
            LoginMethodParam loginMethodParam = new LoginMethodParam();
            loginMethodParam.setCardProjectId(cardProjectId);
            loginMethodParam.setOpenIdProjectId(openIdProjectId);
            loginMethodParam.setOpenId(openId);
            loginMethodParam.setAccessToken(accessToken);
            loginMethodParam.setPhone(phone);
            loginMethodParam.setAuthCode(authCode);
            return loginMethodParam;
        }
    }

}
