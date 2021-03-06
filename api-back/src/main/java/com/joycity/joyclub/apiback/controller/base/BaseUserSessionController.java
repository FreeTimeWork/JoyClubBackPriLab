package com.joycity.joyclub.apiback.controller.base;

import com.joycity.joyclub.apiback.constant.UserType;
import com.joycity.joyclub.commons.exception.BusinessException;
import com.joycity.joyclub.apiback.modal.generated.SysUser;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpSession;

import static com.joycity.joyclub.commons.constant.ResultCode.API_NO_PERMISSION_FOR_CURRENT_USER;
import static com.joycity.joyclub.commons.constant.ResultCode.USER_SESSION_NULL;
import static com.joycity.joyclub.apiback.constant.UserType.*;

/**
 * 主要是定义一个基础类， user的获取session属性名
 * Created by CallMeXYZ on 2017/3/27.
 */
public abstract class BaseUserSessionController{
    /**
     * user的session属性名
     */
    @Value("${session.api-back.attr.user}")
    public String SESSION_ATTR_NAME_USER;

    protected SysUser checkThirdPartyShopUser(HttpSession session) {
        return checkUser(session, USER_TYPE_THIRD_PARTY_SHOP);
    }

    protected SysUser checkStoreUser(HttpSession session) {
        return checkUser(session, USER_TYPE_STORE);
    }

    public SysUser checkProjectUser(HttpSession session) {
        return checkUser(session, UserType.USER_TYPE_PROJECT);
    }

    protected SysUser checkPlatformUser(HttpSession session) {
        return checkUser(session, USER_TYPE_PLATFORM);
    }



    protected SysUser checkPlatformOrProjectUser(HttpSession session) {
        return checkUser(session, USER_TYPE_PLATFORM, USER_TYPE_PROJECT);
    }


    protected SysUser checkPlatformOrProjectOrStoreUser(HttpSession session) {
        return checkUser(session, USER_TYPE_PLATFORM, USER_TYPE_PROJECT,USER_TYPE_STORE);
    }
    protected SysUser checkUser(HttpSession session, Integer... userType) {
        SysUser user = (SysUser) session.getAttribute(SESSION_ATTR_NAME_USER);
        //目前验证user==null是多余的，因为在所有的api请求前都会验证session的存在
        if (user == null) throw new BusinessException(USER_SESSION_NULL);
        //如果没有提供角色，直接返回登陆用户
        if(userType.length==0) return user;
        boolean checkRight = false;
        for (Integer type : userType) {
            if (user.getType().equals(type)) {
                checkRight = true;
                break;
            }
        }
        if (!checkRight) throw new BusinessException(API_NO_PERMISSION_FOR_CURRENT_USER);
        return user;

    }
}
