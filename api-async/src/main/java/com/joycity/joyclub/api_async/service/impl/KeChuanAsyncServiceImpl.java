package com.joycity.joyclub.api_async.service.impl;

import com.alibaba.fastjson.JSON;
import com.joycity.joyclub.api_async.modal.kechuan.KeChuanAsyncData;
import com.joycity.joyclub.api_async.modal.kechuan.KeChuanAsyncResult;
import com.joycity.joyclub.api_async.modal.kechuan.KeChuanClient;
import com.joycity.joyclub.api_async.service.KeChuanAsyncService;
import com.joycity.joyclub.client.service.ClientService;
import com.joycity.joyclub.commons.utils.EncryptionUtil;
import com.joycity.joyclub.commons.utils.MD5Util;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * KechuanAsyncService
 *
 * @author CallMeXYZ
 * @date 2017/8/11
 */
@Service
public class KeChuanAsyncServiceImpl implements KeChuanAsyncService {
    private final Log logger = LogFactory.getLog(KeChuanAsyncServiceImpl.class);
    @Autowired
    ClientService clientService;
    @Value("${async.kechuan.client.privateKey}")
    private String PRIVATE_KEY;
    @Value("${async.kechuan.client.userCode}")
    private String USER_CODE;


    @Override
    public KeChuanAsyncResult ayncClinet(KeChuanAsyncData data) {
        KeChuanAsyncResult result = new KeChuanAsyncResult(data.getUuid());
        int num = 0;
        try {
            List<KeChuanClient> keChuanClients = decryptClientData(data);
            for (KeChuanClient client : keChuanClients) {
                if (client.getCrmId() != null) {
                    clientService.fetchClientByVipCodeFromKechuanAndAynclocal(client.getCrmId());
                    num++;
                }
            }
        } catch (Exception e) {
            result.setError(e.getMessage());
        }
        logger.info("科传会员更改通知，有效数据" + num + "条");
        // TODO: 2017/8/11 后续需要删除
        logger.info("科传会员更改通知，数据为" + JSON.toJSONString(data));
        return result;
    }

    private List<KeChuanClient> decryptClientData(KeChuanAsyncData data) throws Exception {
        String aesKey = EncryptionUtil.rsaDecrypt(data.getSecretkey(), PRIVATE_KEY);
        String clientsStr = EncryptionUtil.aesDecrypt(data.getContent(), aesKey);
        if (clientsStr == null) {
            throw new Exception("AES解密失败");
        }
        if(!data.getUsercode().equals(USER_CODE)){
            throw new Exception("用户名错误");
        }
        if (!MD5Util.MD5(clientsStr).toUpperCase().equals(data.getCheckcode().toUpperCase())) {
            throw new Exception("chekcode 错误");
        }
        return JSON.parseArray(clientsStr, KeChuanClient.class);
    }

}