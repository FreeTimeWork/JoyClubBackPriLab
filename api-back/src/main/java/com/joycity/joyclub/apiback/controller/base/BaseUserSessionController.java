package com.joycity.joyclub.apiback.controller.base;

import com.joycity.joyclub.apiback.constant.UserType;
import com.joycity.joyclub.apiback.exception.BusinessException;
import com.joycity.joyclub.apiback.modal.generated.SysUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import javax.servlet.http.HttpSession;

import static com.joycity.joyclub.apiback.constant.ResultCode.API_NO_PERMISSION_FOR_CURRENT_USER;

/**
 * 主要是定义一个基础类， user的获取session属性名
 * Created by CallMeXYZ on 2017/3/27.
 */
public abstract class BaseUserSessionController {
    /**
     * user的session属性名
     */
    @Value("${session.api-back.attr.user}")
    public String SESSION_ATTR_NAME_USER;

    public void checkStoreUser(HttpSession session) {
        checkUser(session, UserType.USER_TYPE_STORE);
    }
    public void checkProjectUser(HttpSession session) {
        checkUser(session, UserType.USER_TYPE_PROJECT);
    }

    public void checkPlatFormUser(HttpSession session) {
        checkUser(session, UserType.USER_TYPE_PLATFORM);
    }

    public void checkUser(HttpSession session, Integer... userType) {
        SysUser user = (SysUser) session.getAttribute(SESSION_ATTR_NAME_USER);
        for (Integer type : userType) {
            if (user.getType() != type) {
                throw new BusinessException(API_NO_PERMISSION_FOR_CURRENT_USER);
            }
        }

    }
}
