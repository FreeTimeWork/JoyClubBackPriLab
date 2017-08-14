package com.joycity.joyclub.apiback.util;

import com.alibaba.fastjson.JSONObject;
import com.joycity.joyclub.commons.constant.ResultCode;
import com.joycity.joyclub.commons.exception.BusinessException;
import com.joycity.joyclub.commons.utils.MD5Util;

/**
 * Created by fangchen.chai on 2017/8/3
 */

public class CheckSecretKey {


    public static void checkSecretKey(Object o, String secretKey, String sign, Long timestamp){
        String jsonO = JSONObject.toJSONString(o);
        //待加密字符串组成为 data + timestamp + secretKey
        String encryptString = jsonO + timestamp.toString() + secretKey;
        if (!MD5Util.MD5(encryptString).toUpperCase().equals(sign.toUpperCase())) {
            throw new BusinessException(ResultCode.REQUEST_PARAMS_ERROR, "密钥错误");
        }
    }
}
