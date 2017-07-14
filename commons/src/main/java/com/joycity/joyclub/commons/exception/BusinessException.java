package com.joycity.joyclub.commons.exception;

import java.util.HashMap;
import java.util.Map;

import com.joycity.joyclub.commons.constant.ResultCode;


/**
 * Created by CallMeXYZ on 2017/3/20.
 * 捕获业务层抛出错误代码的异常并返回相应的错误代码{@link com.joycity.joyclub.commons.modal.base.ResultData}
 */
public class BusinessException extends BaseBusinessException {
    private static Map<Integer, String> map = new HashMap<>();

    static {
        map.put(ResultCode.SUCCESS, "请求成功");
        map.put(ResultCode.ERR_EXPORT_EXCEL, "导出excel失败");
        map.put(ResultCode.ERR_EXPORT_EXCEL, "导出excel失败");
        map.put(ResultCode.LOGIN_ERROR, "登陆失败");
        map.put(ResultCode.UPLOAD_ERROR, "上传失败");
        map.put(ResultCode.USER_SESSION_NULL, "用户未登录或者登陆过期，请重新登陆");
        map.put(ResultCode.API_NO_PERMISSION_FOR_CURRENT_USER, "当前账户没有权限访问该内容");
        map.put(ResultCode.DATA_NOT_EXIST, "数据不存在");
        map.put(ResultCode.ACCOUNT_EXIST, "该账号已经存在");
        map.put(ResultCode.DATA_NOT_PERMIT, "不允许该数据");
        map.put(ResultCode.OLD_PASSWORD_ERROR, "旧密码不正确");
        map.put(ResultCode.VIP_CARD_MAKE_ERROR, "制卡错误");
        map.put(ResultCode.COUPON_CHECK_ERROR, "卡券核销错误");
        map.put(ResultCode.WECHAT_PAY_REQUEST_ERROR, "请求微信支付异常");
        map.put(ResultCode.KECHUAN_INFO_ERROR, "获得会员信息失败");
        map.put(ResultCode.KECHUAN_INFO_ERROR_BUT_RIGHT, "科传返回异常");
        map.put(ResultCode.MSG_AUTH_CODE_ERROR, "发送验证码出错");
        map.put(ResultCode.DATA_NOT_EXIST, "数据不存在");
        map.put(ResultCode.REQUEST_PARAM_ERROR, "请求参数错误");

        map.put(ResultCode.CLIENT_TOKEN_NULL, "会话已过期，请重新登陆");
        map.put(ResultCode.CLIENT_TOKEN_EXPIRE, "会话已过期，请重新登陆");
        map.put(ResultCode.LAUNCH_NUM_EXCEED_COUPON_NUM, "投放数量超过剩余发行量");
        map.put(ResultCode.COUPON_LAUNCH_TYPE_ERROR, "投放类型错误");

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
