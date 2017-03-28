package com.joycity.joyclub.apiback.config.security;

import com.joycity.joyclub.apiback.exception.BusinessException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.joycity.joyclub.apiback.constant.ResultCode.USER_SESSION_NULL;

/**
 * Created by CallMeXYZ on 2017/3/27.
 */


@Component
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
            if (request.getSession().getAttribute(getApiBackSessionAttrUser()) == null) {
                throw new BusinessException(USER_SESSION_NULL);
            }
        }
        return true;
    }
}