package com.joycity.joyclub.apifront.constant;

/**
 * Created by CallMeXYZ on 2017/2/21.
 * http请求时，返回的ResultCode码
 */
public class ResultCode {
    public static final int SUCCESS = 200;
    public static final int REQUEST_PARAM_ERROR = 40000;
    public static final int HTTPKIT_CONNECT_ERROR = 50000;

    public static final int WECHAT_PAY_REQUEST_ERROR = 50001;
    public static final int KECHUAN_INFO_ERROR = 50102;
    /**
     * 科传返回的结果是错误，但是实际上并不是错误。
     * 现在的情况只有一个，就是会员积分记录为0时，返回结果出错，其实不能算错误。
     * */
    public static final int KECHUAN_INFO_ERROR_BUT_RIGHT = 50103;

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
    public static final int VIP_POINT_NOT_ENOUGH = 50601;


}
