package com.joycity.joyclub.apiback.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;

import java.security.MessageDigest;

/**
 * Created by CallMeXYZ on 2017/3/27.
 */
public class MD5Util {
    private static final Log logger = LogFactory.getLog(MD5Util.class);

    public static String MD5(String sourceString) {
        return MD5(sourceString, null);
    }

    public static String MD5(String sourceString, String salt) {
        String resultString = null;
        try {
            resultString = sourceString + (salt != null ? salt : "");
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byte2hexString(md.digest(resultString.getBytes()));
        } catch (Exception ex) {
            logger.error("MD5 error", ex);
        }
        return resultString;
    }

    private static  String byte2hexString(byte[] bytes) {
        StringBuilder bf = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            if ((bytes[i] & 0xff) < 0x10) {
                bf.append("0");
            }
            bf.append(Long.toString(bytes[i] & 0xff, 16));
        }
        return bf.toString();
    }
}
