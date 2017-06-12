import com.alibaba.fastjson.JSON;
import com.joycity.joyclub.commons.utils.MD5Util;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;
import sun.misc.BASE64Decoder;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by CallMeXYZ on 2017/4/1.
 */

public class TestPay {
    private class Ticket {
        private String Ticket;

        public Ticket(String ticket) {
            Ticket = ticket;
        }

        public String getTicket() {
            return Ticket;
        }

        public void setTicket(String ticket) {
            Ticket = ticket;
        }
    }
    @Test
    public void test() throws NoSuchPaddingException, NoSuchAlgorithmException, IOException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException, InvalidKeySpecException {

        Map<String,String> map = new HashMap<>();
        map.put("Ticket",decrypt("QAnQbrZcZOFafI3dooziZg==","6ba21371d66eb44d"));
        System.out.println(testSign(map));

    }
   public String testSign(Object data){
       //公钥
       String PublicKey = "17597c9f438c4c4d";
       //私钥
       String privateKey = "6ba21371d66eb44d";
       //时间戳
       String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
       System.out.println(timeStamp);

       String jsonData = JSON.toJSONString(data);


       //待加密字符串（顺序必须一致，大小写必须相同）
       String encryptString = "{publicKey:" + PublicKey + ",timeStamp:" + timeStamp + ",data:" + jsonData + ",privateKey:" + privateKey + "}";
       //生成[签名字符串](使用MD516位加密)
       System.out.println(encryptString);
      return DigestUtils.md5Hex(encryptString).substring(8, 24).toUpperCase();
       /// <summary>
       /// MD5加密（16位）
       /// </summary>
       /// <param name="EncryptString">需要加密的字符串</param>
       /// <returns></returns>

   }

    public  String decrypt(final String souce, final String key) throws NoSuchPaddingException, NoSuchAlgorithmException, IOException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException, InvalidKeySpecException {

            return decryptByDes(souce, pkcs5Pad(key)).trim();

    }
    /**
     * 密钥算法
     */
    private static final String ALGORITHM = "DES";

    /**
     * 加解密算法/工作模式/填充方式
     */
    private static final String ALGORITHM_STR = "DES/ECB/NoPadding";

    private static final String CHARSET = "UTF-8";

    private String decryptByDes(final String source, final String key) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IOException,
            IllegalBlockSizeException, BadPaddingException {
        //DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        //从原始密匙数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key.getBytes(CHARSET));
        //创建一个密匙工厂，然后用它把DESKeySpec转换成 一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey key1 = keyFactory.generateSecret(dks);
        //Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(ALGORITHM_STR);
        //用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, key1, sr);
        //将加密报文用BASE64算法转化为字节数组
        byte[] encryptedData = new BASE64Decoder().decodeBuffer(source);
        //用DES算法解密报文
        byte decryptedData[] = cipher.doFinal(encryptedData);
        return new String(decryptedData, CHARSET);
    }
    /**
     * 填充内容
     */
    private static final String PAD_STR = "\0";
    private static String pkcs5Pad(final String souce) {
        //密文和密钥的长度必须是8的倍数
        if (0 == souce.length() % 8) {
            return souce;
        }

        StringBuffer tmp = new StringBuffer(souce);

        while (0 != tmp.length() % 8) {
            tmp.append(PAD_STR);
        }
        return tmp.toString();
    }
}
