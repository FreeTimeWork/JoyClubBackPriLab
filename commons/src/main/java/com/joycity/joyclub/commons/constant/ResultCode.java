package com.joycity.joyclub.commons.constant;

/**
 * Created by CallMeXYZ on 2017/2/21.
 * http请求时，返回的ResultCode码
 */
public class ResultCode {


    public static final int SUCCESS = 200;

    public static final int LOGIN_ERROR = 20001;
    public static final int API_NO_PERMISSION_FOR_CURRENT_USER = 20002;
    public static final int ACCOUNT_EXIST = 20003;
    public static final int OLD_PASSWORD_ERROR = 20004;

    public static final int USER_SESSION_NULL = 30000;
    public static final int ERR_EXPORT_EXCEL = 30001;
    public static final int ERR_IMPORT_EXCEL = 30002;
    public static final int UPLOAD_ERROR = 30101;

    /**
     * 请求的参数错误
     */
    public static final int REQUEST_PARAMS_ERROR = 30006;


    /**
     * 数据不存在，比如通过id访问时返回null
     */
    public static final int DATA_NOT_EXIST = 30201;
    public static final int DATA_NOT_PERMIT = 30202;
    public static final int DATA_ALREADY_EXIST = 30203;
    public static final int REQUEST_PARAM_ERROR = 30204;






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

    public static final int VIP_CARD_NUM_NO_AVAILABLE = 50501;

    /**
     * 制卡错误
     */

    public static final int VIP_CARD_MAKE_ERROR = 50502;

    public static final int VIP_POINT_NOT_ENOUGH = 50601;
    public static final int COUPON_CHECK_ERROR = 50701;


}
