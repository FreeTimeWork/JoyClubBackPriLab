package com.joycity.joyclub.apiback.util;

import com.joycity.joyclub.commons.constant.ResultCode;
import com.joycity.joyclub.commons.exception.BusinessException;
import com.joycity.joyclub.commons.utils.MD5Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.joycity.joyclub.commons.constant.ResultCode.API_NO_PERMISSION_FOR_CURRENT_USER;

/**
 * Created by fangchen.chai on 2017/8/3
 */

public class CheckSecretKey {

    public static void checkSecretKey(String secretKey, String sign, Long timestamp){
        long now = System.currentTimeMillis();
        long diff = now - timestamp;
        //10s 以内可以通过
        if (diff < 0 && diff > 10) {
            throw new BusinessException(ResultCode.REQUEST_PARAMS_ERROR, "链接超时");
        }
        if (!MD5Util.MD5(secretKey).equals(sign)) {
            throw new BusinessException(ResultCode.REQUEST_PARAMS_ERROR, "密钥错误");
        }
    }

    public static void main(String[] args) {
        System.out.println(new Date().getTime() - new Date().getTime() );
        if (1L < 1) {
            System.out.println(true);
        }
    }
}
