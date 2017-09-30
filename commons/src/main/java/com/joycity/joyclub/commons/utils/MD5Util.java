package com.joycity.joyclub.commons.utils;

import com.alibaba.fastjson.JSONObject;
import com.joycity.joyclub.commons.modal.store.StoreInfo;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.security.MessageDigest;

/**
 * Created by CallMeXYZ on 2017/3/27.0
 * 返回的默认是全小写
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

    public static void main(String[] args) throws Exception {
//        StoreInfo info = new StoreInfo();
//        info.setHeadImg("aaa");
//        info.setId(1L);
//        info.setIntro("bbb");
//        info.setName("ccc");
//        info.setPickupAddress("ddd");
//        info.setPortrait("eee");
//        info.setProjectId(2L);
//        info.setServicePhone("fff");
//        JSONObject.toJSONString(info);
//        String json = JSONObject.toJSONString(info);
        String text = "test";
        String password = "013c5b2630558c4d";
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDFfuLJRUuaJknPQL714QKxw7gf52Hhsrc8sl7q3qqMJfMZUMA/eXDAcuFkv1beHM5YnBYASXK2yWLX3QvpvJLHnCJYq0jImQfocJCDy5Qsyr7FA7yboF5YHsoCCchKU/0spjhao3y3VzKdKKZW8rzvrfqtrKDryF6tQSKyDIn/xQIDAQAB";
        String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMV+4slFS5omSc9AvvXhArHDuB/nYeGytzyyXureqowl8xlQwD95cMBy4WS/Vt4czlicFgBJcrbJYtfdC+m8ksecIlirSMiZB+hwkIPLlCzKvsUDvJugXlgeygIJyEpT/SymOFqjfLdXMp0oplbyvO+t+q2soOvIXq1BIrIMif/FAgMBAAECgYAmnKkgKqEeUTALCiCLl+Nb9+Tur+EuLC+w+TqfdssnPWAq6eO8RTTrG0q/1LzaDg21u0Z95d48qGDfzUK//vM/JNIm11NunQqitcosqQ8g+SdON5vhybycNEdYu9OwfY8OVlU5k5jUnqb7vScOgfguURBvTcRmBoKPSytrUlpxIQJBAP2z+yGKeyanfB4ZmyWXGRIFjcdA/oU8NpWhhKcOsfLzlBqRQoRLw5xgPNiTKHbjj6veplCYPz16CzaiVnvnyrkCQQDHSKFmSz2X6d5We5M14vue0wCNssoq+CKEWh5/oI3nnCEPIMCiXq5pF7zqgI6qY1lj2u1xGTOMGaSfshPNaqdtAkEAoIBdrxGB6alpr005xuU5ehpDYLwwiS/XxIADgQCd+Xq5xrkBINshrGuB8u3eWqCKgwv/ods/nu/36h93oTm6GQJAVHRlYjK00bKdNU19n7KoLDED5QlGQFuUVT7T+gaS3Pr/z6dzu9wgUg1rVH5S98bO1VCsm+ewPsmHSN5xIfb3KQJASGaFlchTGF8Jm8evxLfJHDpk+i0RfHJdwjsqH8E65AHdpo8z3rtpTB2Q0yoFsfxzikLsJDDc2+IRghOsJJ+F4g==";
        String secretkey = EncryptionUtil.rsaEncrypt(password, publicKey);
        System.out.println("secretkey: "+secretkey);
        String content = EncryptionUtil.aesEncrypt(text,password);
        System.out.println("content:"+content);
        System.out.println("checkCode");

    }
}
