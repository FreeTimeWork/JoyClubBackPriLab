package com.joycity.joyclub.apifront.constant;

/**
 * Created by CallMeXYZ on 2017/2/21.
 * http请求时，返回的ResultCode码
 */
public class ResultCode {
    public static final int SUCCESS = 200;

    public static final int HTTPKIT_CONNECT_ERROR = 50000;

    public static final int WECHAT_PAY_REQUEST_ERROR = 50001;
    public static final int KECHUAN_INFO_ERROR = 50102;

    /**
     * 验证码错误
     */
    public static final int MSG_AUTH_CODE_ERROR = 50201;
    /**
     * 微信相关错误
     */
    public static final int WECHAT_ERROR = 50301;

    public static final int DATA_NOT_EXIST = 50401;
    public static final int VIP_CARD_NUM_NO_AVAILABLE = 50501;
}
