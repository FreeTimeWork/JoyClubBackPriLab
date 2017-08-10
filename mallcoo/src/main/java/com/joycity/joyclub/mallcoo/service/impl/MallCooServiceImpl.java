package com.joycity.joyclub.mallcoo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.joycity.joyclub.commons.constant.ResultCode;
import com.joycity.joyclub.commons.exception.BusinessException;
import com.joycity.joyclub.commons.utils.ThrowBusinessExceptionUtil;
import com.joycity.joyclub.mallcoo.mapper.ProjectMallcooMapper;
import com.joycity.joyclub.mallcoo.modal.ProjectMallcoo;
import com.joycity.joyclub.mallcoo.modal.result.data.*;
import com.joycity.joyclub.mallcoo.service.MallCooService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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
import java.util.List;
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
    @Autowired
    ProjectMallcooMapper projectMallcooMapper;
    @Resource
    RestTemplate restTemplate;
    @Value("${mallcoo.url.getTokenByTicket}")
    private String URL_GET_TOKEN_BY_TICKET;
    @Value("${mallcoo.url.getInfoByToken}")
    private String URL_GET_INFO_BY_TOKEN;
    @Value("${mallcoo.url.getAdvancedInfoById}")
    private String URL_GET_ADVANCED_INFO_BY_ID;
    @Value("${mallcoo.url.getShop}")
    private String URL_GET_SHOPS;
    @Value("${mallcoo.url.getCoupons}")
    private String URL_GET_COUPONS;
    @Value("${mallcoo.url.getCouponInfo}")
    private String URL_GET_COUPON_INFO;

    @Override
    public UserSimpleInfo getUserToken(Long projectId, String ticket) {
        Map<String, String> body = new HashMap<>();
        body.put("Ticket", ticket);
        return postForDataObject(projectId, URL_GET_TOKEN_BY_TICKET, body, UserSimpleInfo.class);
    }

    @Override
    public UserAdvancedInfo getUserAdvancedInfoByTicket(Long projectId, String ticket) {
        UserSimpleInfo simpleInfo = getUserToken(projectId, ticket);
        return getUserAdvancedInfoById(projectId, simpleInfo.getOpenUserId());
    }

    @Override
    public UserAdvancedInfo getUserAdvancedInfoById(Long projectId, String openUserId) {
        Map<String, String> body = new HashMap<>();
        body.put("OpenUserId", openUserId);
        return postForDataObject(projectId, URL_GET_ADVANCED_INFO_BY_ID, body, UserAdvancedInfo.class);
    }

    @Override
    public List<OffLineShopInfo> getShops(Long projectId) {
        Map<String, String> body = new HashMap<>();
        body.put("PageIndex", "1");
        return postForDataArray(projectId, URL_GET_SHOPS, body, OffLineShopInfo.class);
    }

    @Override
    public CouponsResult getCoupons(Long projectId, String crmId) {
        Map<String, String> body = new HashMap<>();
        body.put("MemberID", crmId);
        body.put("MinID", "0");
        body.put("PageSize", "100");
        return postForDataObject(projectId, URL_GET_COUPONS, body, CouponsResult.class);
    }

    @Override
    public CouponInfo getCouponInfo(Long projectId, String code) {
        Map<String, String> body = new HashMap<>();
        body.put("VCode", code);
        return postForDataObject(projectId, URL_GET_COUPON_INFO, body, CouponInfo.class);

    }

    private <T> List<T> postForDataArray(Long projectId, String url, Object body, Class<T> clazz) {
        return JSON.parseArray(postForDataString(projectId, url, body), clazz);
    }

    private <T> T postForDataObject(Long projectId, String url, Object body, Class<T> clazz) {
        return JSON.parseObject(postForDataString(projectId, url, body), clazz);
    }

    private String postForDataString(Long projectId, String url, Object body) {
        ProjectMallcoo projectMallcoo = projectMallcooMapper.getProjectMallcooInfo(projectId);
        ThrowBusinessExceptionUtil.checkNull(projectMallcoo, "该项目猫酷信息不存在");
        HttpHeaders headers = getHeader(projectMallcoo, body);
        HttpEntity request = new HttpEntity(body, headers);
        String response = restTemplate.postForObject(url, request, String.class);
        JSONObject jsonObject = JSONObject.parseObject(response);
        int code = jsonObject.getInteger("Code");
        if (code != 1) {
            throw new BusinessException(ResultCode.MALLCOO_ERROR, jsonObject.getString("Message"));
        }
        String a = jsonObject.getString("Data");
        return jsonObject.getString("Data");
    }

    private HttpHeaders getHeader(ProjectMallcoo projectMallcoo, Object body) {

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
