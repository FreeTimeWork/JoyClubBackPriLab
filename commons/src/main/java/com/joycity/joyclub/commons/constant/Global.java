package com.joycity.joyclub.commons.constant;

/**
 * Created by Administrator on 2017/4/15.
 */
public class Global {
    /**
     * 平台的项目id
     */
    // TODO: 2017/4/15将这个变成可配置
    public static final Long PLATFORM_ID = 1L;
    /**
     * 用于给controller的projectId提供默认值
     */
    public static final String PLATFORM_ID_REQUEST_PARAM = "1";


    public static final String LOGGER_HEADER = "-----------------";

    public static final String URL_API_BACK = "/api/back";
    public static final String URL_API_FRONT = "/api/front";


    public static final String RESET_PWD = "888888";

    public static final String SEX_MALE = "1";
    public static final String SEX_FEMALE = "0";

    public static final String COOKIE_TOKEN = "token";

    public static final String PROFILE_DEVELOPMENT = "development";
    public static final String PROFILE_NOT_DEVELOPMENT = "!" + PROFILE_DEVELOPMENT;

}
