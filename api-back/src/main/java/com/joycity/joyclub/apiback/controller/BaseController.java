package com.joycity.joyclub.apiback.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

/**
 * 主要是定义一个基础类， user的获取session属性名
 * Created by CallMeXYZ on 2017/3/27.
 */
@PropertySource("classpath:session.properties")
public class BaseController {
    /**
     * user的session属性名
     */
    @Value("${session.attr.user}")
    public String SESSION_ATTR_NAME_USER;
}
