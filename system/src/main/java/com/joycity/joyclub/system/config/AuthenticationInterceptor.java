package com.joycity.joyclub.system.config;

import com.joycity.joyclub.commons.exception.BusinessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.joycity.joyclub.commons.constant.ResultCode.USER_SESSION_NULL;

/**
 * Created by CallMeXYZ on 2017/3/27.
 */
@Component
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {
    /**
     * api back的userSession对应的属性名
     */
    @Value("${session.api-back.attr.user}")
    private String apiBackSessionAttrUser;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {


        String uri = request.getRequestURI();
        //后端不是登陆的请求都要有session
        if ( uri.startsWith("/api/back/")&&!uri.endsWith("/api/back/login")) {
            if (request.getSession().getAttribute(apiBackSessionAttrUser) == null) {
                throw new BusinessException(USER_SESSION_NULL);
            }
        }
        return true;
    }

}