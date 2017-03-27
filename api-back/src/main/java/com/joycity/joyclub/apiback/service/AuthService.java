package com.joycity.joyclub.apiback.service;

import com.joycity.joyclub.apiback.modal.base.ResultData;

/**
 * Created by CallMeXYZ on 2017/3/27.
 */
public interface AuthService {

    /**
     * 按账户名和账户密码区分大小写，返回登陆用户信息（
     * @param account
     * @param password 该密码前端应该MD5加密过一遍
     * @return
     */
    ResultData login(String account,String password);

    /**
     * 目前什么也不做
     * @return
     */
    ResultData logout();
}
