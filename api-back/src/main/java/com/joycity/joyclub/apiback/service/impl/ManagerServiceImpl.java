package com.joycity.joyclub.apiback.service.impl;

import com.joycity.joyclub.apiback.constant.UserType;
import com.joycity.joyclub.apiback.mapper.manual.SysUserMapper;
import com.joycity.joyclub.apiback.modal.base.CreateResult;
import com.joycity.joyclub.apiback.modal.base.DataListResult;
import com.joycity.joyclub.apiback.modal.base.ResultData;
import com.joycity.joyclub.apiback.modal.base.UpdateResult;
import com.joycity.joyclub.apiback.modal.generated.SysUser;
import com.joycity.joyclub.apiback.service.ManagerService;
import com.joycity.joyclub.apiback.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.joycity.joyclub.apiback.constant.Global.RESET_PWD;
import static com.joycity.joyclub.apiback.constant.UserType.USER_TYPE_PROJECT;
import static com.joycity.joyclub.apiback.constant.UserType.USER_TYPE_STORE;

/**
 * Created by CallMeXYZ on 2017/3/28.
 */
@Service
public class ManagerServiceImpl implements ManagerService {
    @Value("${encrypt.password.salt}")
    private String PASSWORD_SALT;
    @Autowired
    private SysUserMapper sysSysUserMapper;

    @Override
    public ResultData getProjectManagersByProjectId(Long projectId) {
        return new ResultData(new DataListResult(sysSysUserMapper.getManagersByUserTypeAndInfoId(USER_TYPE_PROJECT,projectId)));
    }

    @Override
    public ResultData createProjectManager(Long projectId, SysUser user) {
        user.setType(USER_TYPE_PROJECT);
        return createManager(user);
    }

    @Override
    public ResultData getStoreManagersByStoreId(Long storeId) {
        return new ResultData(new DataListResult(sysSysUserMapper.getManagersByUserTypeAndInfoId(USER_TYPE_STORE,storeId)));

    }

    @Override
    public ResultData createStoreManager(Long storeId, SysUser user) {
        user.setType(UserType.USER_TYPE_STORE);
        return createManager(user);
    }

    @Override
    public ResultData updateRemark(Long managerId, String remark) {
        SysUser sysUser = new SysUser();
        sysUser.setId(managerId);
        sysUser.setRemark(remark);
        return updateManager(sysUser);
    }

    @Override
    public ResultData resetPwd(Long managerId) {
        SysUser sysUser = new SysUser();
        sysUser.setId(managerId);
        sysUser.setPassword(MD5Util.MD5(RESET_PWD,PASSWORD_SALT));
        return updateManager(sysUser);
    }

    @Override
    public ResultData forbid(Long managerId) {
        SysUser sysUser = new SysUser();
        sysUser.setId(managerId);
        sysUser.setForbidFlag(true);
        return updateManager(sysUser);
    }

    @Override
    public ResultData cancelForbid(Long managerId) {
        SysUser sysUser = new SysUser();
        sysUser.setId(managerId);
        sysUser.setForbidFlag(false);
        return updateManager(sysUser);
    }

    private ResultData updateManager(SysUser sysUser) {
        return new ResultData(new UpdateResult(sysSysUserMapper.updateByPrimaryKeySelective(sysUser)));
    }
    private ResultData createManager(SysUser sysUser) {
        sysUser.setDeleteFlag(null);
        sysUser.setLastUpdate(null);
        sysUser.setCreateTime(null);
        sysUser.setDeleteTime(null);
        sysSysUserMapper.insertSelective(sysUser);
        return new ResultData(new CreateResult(sysUser.getId()));
    }

}
