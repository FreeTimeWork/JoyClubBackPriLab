package com.joycity.joyclub.apifront.exception;

import com.joycity.joyclub.apifront.constant.ResultCode;
import com.joycity.joyclub.apifront.modal.base.ResultData;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by CallMeXYZ on 2017/3/20.
 * 捕获业务层抛出错误代码的异常并返回相应的错误代码{@link ResultData}
 */
public class BusinessException extends BaseBusinessException {
    private static Map<Integer, String> map = new HashMap<>();

    static {
        map.put(ResultCode.SUCCESS, "请求成功");
        map.put(ResultCode.WECHAT_PAY_REQUEST_ERROR, "请求微信支付异常");
        map.put(ResultCode.KECHUAN_INFO_ERROR, "获得会员信息失败");
        map.put(ResultCode.MSG_AUTH_CODE_ERROR, "发送验证码出错");


    }

    public BusinessException(Integer code) {
        super(code);
    }

    public BusinessException(Integer code, String message) {
        super(code, message);
    }

    @Override
    public String getMessageByCode() {
        String message = null;
        Integer code = getCode();
        if (code != null) {
            message = map.get(code);
        }
        return message;
    }
}
