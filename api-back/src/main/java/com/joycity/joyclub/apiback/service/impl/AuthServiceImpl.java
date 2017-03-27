package com.joycity.joyclub.apiback.service.impl;

import com.joycity.joyclub.apiback.constant.ResultCode;
import com.joycity.joyclub.apiback.exception.BusinessException;
import com.joycity.joyclub.apiback.mapper.SysUserMapper;
import com.joycity.joyclub.apiback.modal.base.ResultData;
import com.joycity.joyclub.apiback.modal.generated.SysUser;
import com.joycity.joyclub.apiback.service.AuthService;
import com.joycity.joyclub.apiback.util.MD5Util;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import sun.security.provider.MD5;

import java.security.MessageDigest;

/**
 * Created by CallMeXYZ on 2017/3/27.
 */
@Service
@PropertySource("classpath:encrypt.properties")
public class AuthServiceImpl implements AuthService {
    @Value("${encrypt.password.salt}")
    private String PASSWORD_SALT;
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public ResultData login(String account, String password) {
        SysUser sysUser = sysUserMapper.getByAccountAndPwd(account);
        if (sysUser == null) throw new BusinessException(ResultCode.LOGIN_ERROR, "账户不存在");
        else if (!MD5Util.MD5(password, PASSWORD_SALT).equals(sysUser.getPassword()))
            throw new BusinessException(ResultCode.LOGIN_ERROR, "密码错误");
        else if (sysUser.getForbidFlag())
           throw new BusinessException(ResultCode.LOGIN_ERROR, "该账户已被禁用");
        //清空密码
        sysUser.setPassword(null);
        return new ResultData(sysUser);
    }

    @Override
    public ResultData logout() {
        return new ResultData("退出成功");
    }
}
