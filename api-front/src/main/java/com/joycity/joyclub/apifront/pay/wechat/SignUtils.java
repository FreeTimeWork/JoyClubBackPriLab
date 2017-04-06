package com.joycity.joyclub.apifront.pay.wechat;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class SignUtils {

    /**
     * 公共签名规则
     *
     * @param params
     * @param encode
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String basicSign(Map<String, Object> params, boolean encode) {
        Set<String> keysSet = params.keySet();
        Object[] keys = keysSet.toArray();
        Arrays.sort(keys);
        StringBuffer temp = new StringBuffer();
        boolean first = true;
        for (Object key : keys) {
            Object value = params.get(key);
            if (value == null || org.apache.commons.lang3.StringUtils.isBlank(value.toString())) {
                continue;
            }
            if (first) {
                first = false;
            } else {
                temp.append("&");
            }
            temp.append(key).append("=");
            String valueString = "";
            if (null != value) {
                valueString = value.toString();
            }
            if (encode) {
                try {
                    temp.append(URLEncoder.encode(valueString, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else {
                temp.append(valueString);
            }
        }
        return temp.toString();
    }

    /**
     * 支付签名
     *
     * @param params 参数集合
     * @param payKey 支付密匙key
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String paySign(Map<String, Object> params, String payKey) {
        String string1 = basicSign(params, false);
        string1 += "&key=" + payKey;
        System.out.println(string1);
        String paySign = DigestUtils.md5Hex(string1);
        return paySign;
    }

    /**
     * 支付签名校验
     */
    public static boolean checkPaySign(Map<String, Object> params, String payKey) {
        if (params != null) {
            String sign = (String) params.remove("sign");
            if (StringUtils.isNotBlank(sign)) {
                return sign.equalsIgnoreCase(paySign(params, payKey));
            }
        }
        return false;
    }

    /**
     * SHA1或MD5加密基础方法
     */
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String encode(String algorithm, String str) {
        if (str == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(str.getBytes());
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        // 把密文转换成十六进制的字符串形式
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }

}
