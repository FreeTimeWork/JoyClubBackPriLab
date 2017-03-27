package com.joycity.joyclub.apiback.exception;

import com.joycity.joyclub.apiback.constant.ResultCode;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by CallMeXYZ on 2017/3/20.
 * 捕获业务层抛出错误代码的异常并返回相应的错误代码{@link com.joycity.joyclub.apiback.modal.base.ResultData}
 */
public class BusinessException extends BaseBusinessException {
    private static Map<Integer, String> map = new HashMap<>();

    static {
        map.put(ResultCode.SUCCESS, "请求成功");
        map.put(ResultCode.ERR_EXPORT_EXCEL, "导出excel失败");
        map.put(ResultCode.ERR_EXPORT_EXCEL, "导出excel失败");
        map.put(ResultCode.LOGIN_ERROR, "登陆失败");
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
