package com.joycity.joyclub.client.util;


import com.joycity.joyclub.commons.constant.ResultCode;
import com.joycity.joyclub.commons.exception.BusinessException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;


import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import static com.joycity.joyclub.commons.constant.ResultCode.KECHUAN_INFO_ERROR;

/**
 * 类名：KeChuanEncryption <br>
 * 描述：加密 <br>
 * 创建者： mateng <br>
 * 创建时间： Jan 25, 2015 9:46:44 PM <br>
 */
public class KeChuanEncryption {

    /**
     * 加密
     *
     * @param str 需要加密的字符串
     * @param key 密钥
     * @return
     */
    public static String aesEncrypt(String str, String key) {
        if (str == null || key == null) return null;
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes("utf-8"), "AES"));
            byte[] bytes = cipher.doFinal(str.getBytes("utf-8"));
            return new String(new Base64().encode(bytes), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ResultCode.KECHUAN_INFO_ERROR, "科传加密失败");
        }
    }

    /**
     * 解密
     *
     * @param str 需要解密的字符串
     * @param key 密钥
     * @return
     */
    public static String aesDecrypt(String str, String key) {
        if (str == null || key == null) return null;
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes("utf-8"), "AES"));
            byte[] bytes = new Base64().decode(str.getBytes("utf-8"));
            bytes = cipher.doFinal(bytes);
            return new String(bytes, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ResultCode.KECHUAN_INFO_ERROR, "科传解密失败");
        }
    }

    public static boolean validate(String signature, String token, String timestamp, String nonce) {
        System.out.println("signature:" + signature);
        System.out.println("token:" + token);
        System.out.println("timestamp:" + timestamp);
        System.out.println("nonce:" + nonce);

        List<String> key = new ArrayList<String>();
        key.add(token);
        key.add(timestamp);
        key.add(nonce);
        Collections.sort(key);
        String tempStr = key.get(0) + key.get(1) + key.get(2);
        tempStr = DigestUtils.sha1Hex(tempStr);
        return tempStr.equals(signature);
    }

    public static String signature(String token, String timestamp, String nonce) {
        List<String> key = new ArrayList<String>();
        key.add(token);
        key.add(timestamp);
        key.add(nonce);
        Collections.sort(key);
        String tempStr = key.get(0) + key.get(1) + key.get(2);
        return DigestUtils.sha1Hex(tempStr);
    }

 /*   public static void main(String[] args) {
        List<String> key = new ArrayList<String>();
        key.add("4028678156348fd7015634911b370002");//token
        key.add("1483524166242");//timestamp当前时间的时间戳
        key.add("120");//nonce随机数
        Collections.sort(key);
        String tempStr = key.get(0) + key.get(1) + key.get(2);
        System.out.println("加密结果：" + DigestUtils.sha1Hex(tempStr));

        System.out.println("解密结果：" + KeChuanEncryption.aesDecrypt("O4PMm3EKbQ4cQfD6LRDXQg==", "89622015104709087435617163207900"));
    }*/

}
