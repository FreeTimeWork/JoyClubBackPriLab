package com.joycity.joyclub.system.config.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Created by CallMeXYZ on 2017/3/27.
 */


@Component
@PropertySource("classpath:log4j.properties")
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

    /**
     * api back的userSession对应的属性名
     */

    private String apiBackSessionAttrUser;

    public String getApiBackSessionAttrUser() {
        return apiBackSessionAttrUser;
    }

    public void setApiBackSessionAttrUser(String apiBackSessionAttrUser) {
        this.apiBackSessionAttrUser = apiBackSessionAttrUser;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
//        for api back
        if (!uri.endsWith("/api/back/login")) {
            System.out.println(getApiBackSessionAttrUser()+""+request.getSession().getAttribute(getApiBackSessionAttrUser()));

            if (request.getSession().getAttribute(getApiBackSessionAttrUser()) == null) {
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "用户未登录");
                return false;
            }
        }
        return true;
    }
}