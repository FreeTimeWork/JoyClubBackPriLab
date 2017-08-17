package com.joycity.joyclub.api_async.modal.kechuan;

/**
 * KechuanAsyncData
 *
 * @author CallMeXYZ
 * @date 2017/8/11
 */
public class KeChuanAsyncData {
    /* 用户识别码,全局唯一，用于识别数据所属用户 */
    private String usercode;
    /* 请求标识符，请求应生成一个新的 uuid */
    private String uuid;
    /* 内容校验码，格式为 MD5_32 位（加密前的 content） */
    private String checkcode;
    /**
     * AES256 密钥每次请求随机生成，
     * 作为参数传递前通过 RSA 加密，
     * RSA 加密公钥双方实现协定，
     * 加密后的字节数据经过BASE64 编码转换后作为参数传递
     */
    private String secretkey;
    /* AES256加密后经过 BASE64编码的 JSON数组，单次请求数组最大长度 256 */
    private String content;

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCheckcode() {
        return checkcode;
    }

    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }

    public String getSecretkey() {
        return secretkey;
    }

    public void setSecretkey(String secretkey) {
        this.secretkey = secretkey;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
