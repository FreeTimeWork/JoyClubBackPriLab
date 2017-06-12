package com.joycity.joyclub.mallcoo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.joycity.joyclub.commons.constant.ResultCode;
import com.joycity.joyclub.commons.exception.BusinessException;
import com.joycity.joyclub.commons.utils.ThrowBusinessExceptionUtil;
import com.joycity.joyclub.mallcoo.mapper.ProjectMallcooMapper;
import com.joycity.joyclub.mallcoo.modal.ProjectMallcoo;
import com.joycity.joyclub.mallcoo.service.MallCooService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;
import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by CallMeXYZ on 2017/6/6.
 */
@Service
public class MallCooServiceImpl implements MallCooService {
    private final Log logger = LogFactory.getLog(MallCooServiceImpl.class);
    /**
     * 密钥算法
     */
    private final String ALGORITHM = "DES";

    /**
     * 加解密算法/工作模式/填充方式
     */
    private final String ALGORITHM_STR = "DES/ECB/NoPadding";

    private final String CHARSET = "UTF-8";
    /**
     * 填充内容
     */
    private final String PAD_STR = "\0";

    @Value("${mallcoo.url.getTokenByTicket}")
    private String URL_GET_TOKEN_BY_TICKET;
    @Value("${mallcoo.url.getInfoByToken}")
    private String URL_GET_INFO_BY_TOKEN;
    @Autowired
    ProjectMallcooMapper projectMallcooMapper;
    @Resource
    RestTemplate restTemplate;

    @Override
    public Map<String, Object> getUserToken(Long projectId, String ticket){
        ProjectMallcoo projectMallcoo = projectMallcooMapper.getProjectMallcooInfo(projectId);
        ThrowBusinessExceptionUtil.checkNull(projectMallcoo, "该项目猫酷信息不存在");
        String realTicket = decrypt(ticket, projectMallcoo.getPrivateKey());
        //请求参数
        Map<String, String> body = new HashMap<String, String>();
        body.put("Ticket", realTicket);
        //请求头
        HttpHeaders headers = getHeader(projectMallcoo, body);

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.exchange(URL_GET_TOKEN_BY_TICKET, HttpMethod.POST, requestEntity, Map.class);
        return getBody(response);
    }

    private Map<String, Object> getBody(ResponseEntity<Map> response)  {
        Map<String, Object> body = response.getBody();
        int code = ((int) body.get("Code"));
        //猫酷 1 是成功
        if (code != 1) {
            throw new BusinessException(ResultCode.MALLCOO_ERROR,body.get("Message").toString());
        }
        return ((Map) body.get("Data"));
    }

/*    @Override
    public Map getUser(Long projectId,String userToken) throws Exception {

        //请求参数
        Map<String, String> body = new HashMap<String, String>();
        body.put("UserToken", userToken);
        //请求头
        HttpHeaders headers = getHeader(body);
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.exchange(URL_GET_INFO_BY_TOKEN, HttpMethod.POST, requestEntity, Map.class);
        return getBody(response);
    }*/


    private HttpHeaders getHeader(ProjectMallcoo projectMallcoo, Map<String, String> body) {

        String data = JSONObject.toJSONString(body);

        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String encryptString = "{publicKey:" + projectMallcoo.getPublicKey() + ",timeStamp:" + timeStamp + ",data:" + data + ",privateKey:" + projectMallcoo.getPrivateKey() + "}";

        String sign = DigestUtils.md5Hex(encryptString);


        sign = sign.substring(8, 24).toUpperCase();
        HttpHeaders headers = new HttpHeaders();
        headers.add("AppID", projectMallcoo.getAppId());
        headers.add("PublicKey", projectMallcoo.getPublicKey());
        headers.add("TimeStamp", timeStamp);
        headers.add("Sign", sign);
        return headers;
    }

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

    private String pkcs5Pad(final String souce) {
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

    public String decrypt(final String souce, final String key) {

        try {
            return decryptByDes(souce, pkcs5Pad(key)).trim();
        } catch (Exception e) {
            return "";
        }

    }
}
