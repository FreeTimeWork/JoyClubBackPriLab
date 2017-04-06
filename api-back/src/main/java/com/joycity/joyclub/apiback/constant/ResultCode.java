package com.joycity.joyclub.apiback.constant;

/**
 * Created by CallMeXYZ on 2017/2/21.
 * http请求时，返回的ResultCode码
 */
public class ResultCode {
    public static final int SUCCESS = 200;

    public static final int LOGIN_ERROR = 20001;
    public static final int API_NO_PERMISSION_FOR_CURRENT_USER = 20002;
    public static final int ACCOUNT_EXIST = 20003;
    public static final int OLD_PASSWORD_ERROR = 2004;

    public static final int USER_SESSION_NULL = 3000;
    public static final int ERR_EXPORT_EXCEL = 3001;
    public static final int UPLOAD_ERROR = 3002;


    /**
     * 数据不存在，比如通过id访问时返回null
     */
    public static final int DATA_NOT_EXIST = 3003;
    public static final int DATA_NOT_PERMIT = 3004;
    public static final int DATA_ALREADY_EXIST = 3005;

    /**
     * 请求的参数错误
     */
    public static final int REQUEST_PARAMS_ERROR = 3006;


}
