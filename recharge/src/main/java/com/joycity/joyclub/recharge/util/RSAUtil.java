package com.joycity.joyclub.recharge.util;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Hex;

import org.apache.commons.codec.binary.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class RSAUtil {

    public static final String RSA = "RSA";

    /**
     * 生成RSA密钥
     * 
     * @return
     * @throws Exception
     */
    public static RSADTO generateRSA() throws Exception {
        RSADTO rsa = null;

        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA);
            keyPairGenerator.initialize(1024);

            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            PrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            rsa = new RSADTO();
            String publicString = keyToString(publicKey);
            String privateString = keyToString(privateKey);
            rsa.setPublicKey(publicString);
            rsa.setPrivateKey(privateString);

        } catch (NoSuchAlgorithmException e) {
        	e.printStackTrace();
            throw new Exception(e.getMessage());
        }

        return rsa;
    }

    /**
     * BASE64 String 转换为 PublicKey
     * 
     * @param publicKeyString
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKey(String publicKeyString) throws Exception {
        PublicKey publicKey = null;
        BASE64Decoder base64Decoder = new BASE64Decoder();
        try {
            byte[] keyByteArray = base64Decoder.decodeBuffer(publicKeyString);
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyByteArray);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            publicKey = keyFactory.generatePublic(x509KeySpec);
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }

        return publicKey;
    }

    /**
     * BASE64 String 转换为 PrivateKey
     * 
     * @param privateKeyString
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String privateKeyString) throws Exception {
        PrivateKey privateKey = null;
        BASE64Decoder base64Decoder = new BASE64Decoder();

        try {
            byte[] keyByteArray = base64Decoder.decodeBuffer(privateKeyString);
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyByteArray);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }

        return privateKey;

    }

    /**
     * RSA 加密返回byte[]
     * 
     * @param content
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static byte[] encodeBytePrivate(byte[] content, PrivateKey privateKey) throws Exception {
        byte[] encodeContent = null;
        try {
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            encodeContent = cipher.doFinal(content);
        } catch (NoSuchAlgorithmException e) {
        	e.printStackTrace();
            throw new Exception(e.getMessage());
        } catch (NoSuchPaddingException e) {
        	e.printStackTrace();
            throw new Exception(e.getMessage());
        } catch (InvalidKeyException e) {
        	e.printStackTrace();
            throw new Exception(e.getMessage());
        } catch (IllegalBlockSizeException e) {
        	e.printStackTrace();
            throw new Exception(e.getMessage());
        } catch (BadPaddingException e) {
        	e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        return encodeContent;
    }

    /**
     * RSA 加密返回 Hex
     * 
     * @param content
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String encodeStringPrivate(byte[] content, PrivateKey privateKey) throws Exception {
        byte[] encodeContent = encodeBytePrivate(content, privateKey);
        String stringContent = Hex.encodeHexString(encodeContent);
        return stringContent;
    }

    /**
     * 解密返回byte[]
     * 
     * @param content
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static byte[] decodeBytePublic(byte[] content, PublicKey publicKey) throws Exception {
        byte[] decodeContent = null;
        try {
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            decodeContent = cipher.doFinal(content);
        } catch (NoSuchAlgorithmException e) {
        	e.printStackTrace();
            throw new Exception(e.getMessage());
        } catch (NoSuchPaddingException e) {
        	e.printStackTrace();
            throw new Exception(e.getMessage());
        } catch (InvalidKeyException e) {
        	e.printStackTrace();
            throw new Exception(e.getMessage());
        } catch (IllegalBlockSizeException e) {
        	e.printStackTrace();
            throw new Exception(e.getMessage());
        } catch (BadPaddingException e) {
        	e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        return decodeContent;
    }

    /**
     * 解密 Hex字符串
     * 
     * @param content
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static byte[] decodeStringPublic(String content, PublicKey publicKey) throws Exception {
        byte[] decodeContent = null;
        try {
            byte[] sourceByteArray = Hex.decodeHex(content.toCharArray());
            decodeContent = decodeBytePublic(sourceByteArray, publicKey);
        } catch (IOException e) {
        	e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        return decodeContent;
    }

    /**
     * 将Key转为String
     * 
     * @param key
     * @return
     */
    private static String keyToString(Key key) {
        byte[] keyByteArray = key.getEncoded();
        BASE64Encoder base64 = new BASE64Encoder();
        String keyString = base64.encode(keyByteArray);
        return keyString;
    }
    
    public static class RSADTO {
        /**
         * 公钥
         */
        private String publicKey;

        /**
         * 私钥
         */
        private String privateKey;

		public String getPublicKey() {
			return publicKey;
		}

		public void setPublicKey(String publicKey) {
			this.publicKey = publicKey;
		}
		public String getPrivateKey() {
			return privateKey;
		}

		public void setPrivateKey(String privateKey) {
			this.privateKey = privateKey;
		}
    }
    
    public static class MD5Util {
        /**
         * MD5加密
         *
         * @param s
         * @return
         */
        public static String MD5(String s) {
            char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
            try {
                byte[] btInput = s.getBytes("utf8");
                // 获得MD5摘要算法的 MessageDigest 对象
                MessageDigest mdInst = MessageDigest.getInstance("MD5");
                // 使用指定的字节更新摘要
                mdInst.update(btInput);
                // 获得密文
                byte[] md = mdInst.digest();
                // 把密文转换成十六进制的字符串形式
                int j = md.length;
                char str[] = new char[j * 2];
                int k = 0;
                for (int i = 0; i < j; i++) {
                    byte byte0 = md[i];
                    str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                    str[k++] = hexDigits[byte0 & 0xf];
                }
                return new String(str);
            } catch (Exception e) {
            	e.printStackTrace();
                return null;
            }
        }
    }

    
    public static void main(String[] args) throws Exception {
    	RSADTO dto = generateRSA();
		System.out.println("私钥 :" + dto.getPrivateKey().trim());
		System.out.println("公钥 :" + dto.getPublicKey().trim());

		String paramStr = "a=1&b=2";
		String privateKey = dto.getPrivateKey();
		String sign = RSAUtil.encodeStringPrivate(MD5Util.MD5(paramStr).getBytes(), RSAUtil.getPrivateKey(privateKey));
        System.out.println(sign);
        String content = "cfc";
//        String p = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKdmk8ci9Vn6Q/irMWzeQ1emFEyV\nxS9FPBnwktPbG3SZCr6oREdVPwvym6ezrmgz4zbb0mkqcLd0+wE2KJJYv8VFjVo6SXVn+i4aoY/o\nSJrYAmE6XvvquRTgz4lHu7cOYi6xT8uWRfZd0iqcDTXHR1h6bMgMPUh6ssyvOL4GQHHXAgMBAAEC\ngYBUq3F7e5cVl5vhntU094agQoRp6CcALcikZiYduek9JFtm67z9R4TICkHopN1GH1iBWtYmEy1H\n3YdHqZkDzJfR4JcpifmGvnan+hX5KitCfeVzTlErlN+22tvfFqRtr4+UqYsmQp5oA6idnMhBRwo2\nY6YvtKD5azFTVcYMtmA+QQJBAOvLNZSSVaaQQX5F38QOkRGFfsI/QQU3ttGQhKLdiy96VB4Gdcey\npk+USsAU5hwE71FaJj2LxInu2/b4JJqmZ0kCQQC1vvnKq0l1oB7VxVf5r3vJs3YKfERhQ16oUx0X\nQ4ZSZnJOlauUZq9Hat+5krE7CJnslQ/iRgbzflpAwJh1mnAfAkEApl2aUFlvVTYy/4UNfKdKc7Jc\n3XEDz0qgEvyEmOT3b2hAMW8daukBruRSWXRlZx+UOzIACSy0Xx3I1Vf/e54tAQJBAK4OU9OQ7y1D\nR8Sa1oI9WWP2BtpL4ID1sDWsQ3QAKyfHDTnNhSD+pmbTJ6floO5UcnVs3GVrg0S2gWD6Egiu+ykC\nQCttGcWT6KspWht8NtfeljFvOwVbVU4G2ksFm2Bw2hBWxvh2mPNFVNMNw+RkReqnKyKQTkfg+I8a\nFVshpN/kO1g=";
//        String pu = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnZpPHIvVZ+kP4qzFs3kNXphRMlcUvRTwZ8JLT\n2xt0mQq+qERHVT8L8puns65oM+M229JpKnC3dPsBNiiSWL/FRY1aOkl1Z/ouGqGP6Eia2AJhOl77\n6rkU4M+JR7u3DmIusU/LlkX2XdIqnA01x0dYemzIDD1IerLMrzi+BkBx1wIDAQAB";
        String p ="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKdmk8ci9Vn6Q/irMWzeQ1emFEyVxS9FPBnwktPbG3SZCr6oREdVPwvym6ezrmgz4zbb0mkqcLd0+wE2KJJYv8VFjVo6SXVn+i4aoY/oSJrYAmE6XvvquRTgz4lHu7cOYi6xT8uWRfZd0iqcDTXHR1h6bMgMPUh6ssyvOL4GQHHXAgMBAAECgYBUq3F7e5cVl5vhntU094agQoRp6CcALcikZiYduek9JFtm67z9R4TICkHopN1GH1iBWtYmEy1H3YdHqZkDzJfR4JcpifmGvnan+hX5KitCfeVzTlErlN+22tvfFqRtr4+UqYsmQp5oA6idnMhBRwo2Y6YvtKD5azFTVcYMtmA+QQJBAOvLNZSSVaaQQX5F38QOkRGFfsI/QQU3ttGQhKLdiy96VB4Gdceypk+USsAU5hwE71FaJj2LxInu2/b4JJqmZ0kCQQC1vvnKq0l1oB7VxVf5r3vJs3YKfERhQ16oUx0XQ4ZSZnJOlauUZq9Hat+5krE7CJnslQ/iRgbzflpAwJh1mnAfAkEApl2aUFlvVTYy/4UNfKdKc7Jc3XEDz0qgEvyEmOT3b2hAMW8daukBruRSWXRlZx+UOzIACSy0Xx3I1Vf/e54tAQJBAK4OU9OQ7y1DR8Sa1oI9WWP2BtpL4ID1sDWsQ3QAKyfHDTnNhSD+pmbTJ6floO5UcnVs3GVrg0S2gWD6Egiu+ykCQCttGcWT6KspWht8NtfeljFvOwVbVU4G2ksFm2Bw2hBWxvh2mPNFVNMNw+RkReqnKyKQTkfg+I8aFVshpN/kO1g=";
        String pu = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnZpPHIvVZ+kP4qzFs3kNXphRMlcUvRTwZ8JLT2xt0mQq+qERHVT8L8puns65oM+M229JpKnC3dPsBNiiSWL/FRY1aOkl1Z/ouGqGP6Eia2AJhOl776rkU4M+JR7u3DmIusU/LlkX2XdIqnA01x0dYemzIDD1IerLMrzi+BkBx1wIDAQAB";
        String ps = RSAUtil.encodeStringPrivate(content.getBytes(), RSAUtil.getPrivateKey(p));
        byte[] pus = RSAUtil.decodeStringPublic(ps, RSAUtil.getPublicKey(pu));
        System.out.println(new String(pus));

    }

}
