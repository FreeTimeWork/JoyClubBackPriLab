package com.joycity.joyclub.recharge.util;

import com.joycity.joyclub.recharge.modal.vo.RtmParamVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @auther fangchen.chai ON 2017/12/1
 */
@Component
public class RtmMD5Util {

    @Value("${rtm.key}")
    private String key;

    private static final Logger logger = LoggerFactory.getLogger(RtmMD5Util.class);
    private RtmMD5Util() {}
    public static String toMD5(String signature) {
        MessageDigest mDigest = null;
        try {
            mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(signature.getBytes());
            return encodeHex(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            logger.error("NoSuchAlgorithmException", e);
        }
        return null;
    }
    private static String encodeHex(byte[] bytes) {
        StringBuffer buffer = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            if (((int) bytes[i] & 0xff) < 0x10)
                buffer.append("0");
            buffer.append(Long.toString((int) bytes[i] & 0xff, 16));
        }
        return buffer.toString();
    }

    /**
     * 1：校验uid和登录加密以及积分查询接口
     * String str = clientId#uid_key#timestamp ， 加密后的
     sign=MD5Util.toMD5(str);
     其中clientId：商户号，uid用户在商户处唯一标识，timestamp 时间
     （long型），key 秘钥（邮件发送商户负责对接人）；
     */
    public String getRtmSign1(RtmParamVo vo) {
        String sign = "clientId#uid_key#timestamp";
        sign = sign.replace("clientId", vo.getClientId())
                .replace("uid", vo.getUid())
                .replace("key", key)
                .replace("timestamp", vo.getTimestamp());
        return toMD5(sign);
    }


    /**
     * 2：增加积分、扣减积分、退还积分
     *   String str = clientId#uid_rtmOrderNum_credits#key#timestamp ，
     加密后的 sign=MD5Util.toMD5(str);
     其中clientId：商户号，uid用户在商户处唯一标识，rtmOrderNum：积
     分兑换订单号，credits 积分数量，key 秘钥（邮件发送商户负责对接人），
     timestamp 时间（long 型）；
     */
    public String getRtmSign2(RtmParamVo vo) {
        String sign = "clientId#uid_rtmOrderNum_credits#key#timestamp";
        sign = getReplaceSign(sign, vo);
        return sign;
    }
    /**
     * 3：查询订单明细：
     *  String str = clientId#rtmOrderNum_key#timestamp ， 加密后的
     sign=MD5Util.toMD5(str);
     其中clientId：商户号，，rtmOrderNum：积分兑换订单号，key 秘钥（邮件发送商
     户负责对接人），timestamp 时间（long 型）；
     */
    public String getRtmSign3(RtmParamVo vo) {
        String sign = "clientId#rtmOrderNum_key#timestamp";
        sign = getReplaceSign(sign, vo);
        return sign;
    }

    private String getReplaceSign(String template, RtmParamVo vo) {
        template = template.replace("clientId", vo.getClientId())
                .replace("key", key)
                .replace("timestamp", vo.getTimestamp())
                .replace("rtmOrderNum", vo.getRtmOrderNum())
                .replace("uid", vo.getUid())
                .replace("credits", vo.getCredits());
        return toMD5(template);
    }

    public static void main(String[] args) {
        String sign = "clientId#uid_key#timestamp";
        sign.replace("uid", "");
        System.out.println(sign);
        System.out.println(encodeBase64("15105492645"));
        System.out.println(decodeBase64(encodeBase64("15105492645")));
    }

    public static String encodeBase64(String text) {
        return Base64Utils.encodeToString(text.getBytes());
    }

    public static String decodeBase64(String text) {
        return new String(Base64Utils.decodeFromString(text));
    }
}
