package com.joycity.joyclub.apiback.service.impl;

import com.joycity.joyclub.apiback.constant.ProjectType;
import com.joycity.joyclub.apiback.constant.UserType;
import com.joycity.joyclub.commons.exception.BusinessException;
import com.joycity.joyclub.apiback.mapper.manual.SysProjectMapper;
import com.joycity.joyclub.apiback.mapper.manual.SysUserMapper;
import com.joycity.joyclub.commons.modal.base.CreateResult;
import com.joycity.joyclub.commons.modal.base.ListResult;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.modal.base.UpdateResult;
import com.joycity.joyclub.apiback.modal.generated.SysProject;
import com.joycity.joyclub.apiback.modal.generated.SysUser;
import com.joycity.joyclub.apiback.service.ManagerService;
import com.joycity.joyclub.commons.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.joycity.joyclub.commons.constant.ResultCode.ACCOUNT_EXIST;
import static com.joycity.joyclub.commons.constant.ResultCode.DATA_NOT_EXIST;
import static com.joycity.joyclub.commons.constant.ResultCode.OLD_PASSWORD_ERROR;
import static com.joycity.joyclub.apiback.constant.UserType.*;

/**
 * Created by CallMeXYZ on 2017/3/28.
 */
@Service
public class ManagerServiceImpl implements ManagerService {
    @Value("${encrypt.password.salt}")
    private String PASSWORD_SALT;
    @Value("${encrypt.password.reset}")
    private String PASSWORD_RESET;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysProjectMapper sysProjectMapper;

    /**
     * 这里的project不止是项目，也可以查找平台的管理者
     *
     * @param projectId
     * @return
     */
    @Override
    public ResultData getProjectManagersByProjectId(Long projectId) {
        SysProject sysProject = sysProjectMapper.selectByPrimaryKey(projectId);
        if (sysProject == null) throw new BusinessException(DATA_NOT_EXIST, "项目不存在");
        List<SysUser> managers;
        //平台的管理者应该包括平台管理员和项目管理员
        if (sysProject.getType().equals(ProjectType.PROJECT_TYPE_PLATFORM)) {
            managers = sysUserMapper.getPlatFormManagersByUserTypeAndInfoId(projectId);
        } else {
            managers = sysUserMapper.getManagersByUserTypeAndInfoId(USER_TYPE_PROJECT, projectId);
        }
        return new ResultData(new ListResult(managers));
    }

    /**
     * 会判断项目不存在，并返回错误
     *
     * @param projectId
     * @param user
     * @return
     */
    @Override
    public ResultData createProjectManager(Long projectId, SysUser user) {
        SysProject sysProject = sysProjectMapper.selectByPrimaryKey(projectId);
        //// TODO: 2017/4/22  平台会员
        if (sysProject == null) throw new BusinessException(DATA_NOT_EXIST, "项目不存在");
        user.setType(USER_TYPE_PROJECT);
        user.setInfoId(projectId);
        user.setSubType(sysProject.getType());
        return createManager(user);
    }

    @Override
    public ResultData getStoreManagersByStoreId(Long storeId) {
        return new ResultData(new ListResult(sysUserMapper.getManagersByUserTypeAndInfoId(USER_TYPE_STORE, storeId)));

    }

    @Override
    public ResultData createStoreManager(Long storeId, SysUser user) {
        user.setInfoId(storeId);
        user.setType(UserType.USER_TYPE_STORE);
        return createManager(user);
    }

    @Override
    public ResultData getThirdPartyShopManagersByShopId(Long shopId) {
        return new ResultData(new ListResult(sysUserMapper.getManagersByUserTypeAndInfoId(USER_TYPE_THIRD_PARTY_SHOP, shopId)));

    }

    @Override
    public ResultData createThirdPartyShopManager(Long shopId, SysUser user) {
        user.setInfoId(shopId);
        user.setType(UserType.USER_TYPE_THIRD_PARTY_SHOP);
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
        return updateManagerPassword(managerId, MD5Util.MD5(PASSWORD_RESET));
    }

    @Override
    public ResultData forbid(Long managerId) {
        SysUser sysUser = new SysUser();
        sysUser.setId(managerId);
        sysUser.setForbidFlag(true);
        sysUser.setForbidTime(new Date());
        return updateManager(sysUser);
    }

    @Override
    public ResultData cancelForbid(Long managerId) {
        SysUser sysUser = new SysUser();
        sysUser.setId(managerId);
        sysUser.setForbidFlag(false);
        return updateManager(sysUser);
    }

    @Override
    public ResultData updateManagerPassword(long id, String oldPassword, String password) {
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(id);
        if (!sysUser.getPassword().toUpperCase().equals(MD5Util.MD5(oldPassword, PASSWORD_SALT).toUpperCase()))
            throw new BusinessException(OLD_PASSWORD_ERROR);
        return updateManagerPassword(id, password);
    }

    private ResultData updateManagerPassword(long id, String password) {
        SysUser sysUser = new SysUser();
        sysUser.setId(id);
        sysUser.setPassword(MD5Util.MD5(password, PASSWORD_SALT));
        return updateManager(sysUser);
    }

    private ResultData updateManager(SysUser sysUser) {
        return new ResultData(new UpdateResult(sysUserMapper.updateByPrimaryKeySelective(sysUser)));
    }

    private ResultData createManager(SysUser sysUser) {
        SysUser accountUser = sysUserMapper.getByAccount(sysUser.getAccount());
        if (accountUser != null) throw new BusinessException(ACCOUNT_EXIST);
        sysUser.setPassword(MD5Util.MD5(sysUser.getPassword(), PASSWORD_SALT));
        sysUserMapper.insertSelective(sysUser);
        return new ResultData(new CreateResult(sysUser.getId()));
    }

}
