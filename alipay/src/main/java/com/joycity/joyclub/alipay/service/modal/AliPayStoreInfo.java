package com.joycity.joyclub.alipay.service.modal;

/**
 * Created by CallMeXYZ on 2017/5/9.
 * 商户的相关密钥
 * 三项必须都赋值
 */
public class AliPayStoreInfo {
    private String appId;
   /* private String pId;*/
    private String privateKey;
    private String publicKey;

    public AliPayStoreInfo(String appId, /*String pId,*/ String privateKey, String publicKey) {
        this.appId = appId;
      /*  this.pId = pId;*/
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

  /*  public String getPId() {
        return pId;
    }

    public void setPId(String pId) {
        this.pId = pId;
    }*/

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
