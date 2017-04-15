package com.joycity.joyclub.apiback.service;

import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.apiback.modal.generated.SysUser;

/**
 * Created by CallMeXYZ on 2017/3/28.
 */
public interface ManagerService {
    /**
     * 按照添加时间倒序
     *
     * @param projectId
     * @return
     */
    ResultData getProjectManagersByProjectId(Long projectId);

    /**
     * @param projectId
     * @param user
     * @return data.id为创建的id
     */
    ResultData createProjectManager(Long projectId, SysUser user);

    /**
     * 按照添加时间倒序
     *
     * @param storeId
     * @return
     */
    ResultData getStoreManagersByStoreId(Long storeId);

    /**
     * @param storeId
     * @param user
     * @return data.id为创建的id
     */
    ResultData createStoreManager(Long storeId, SysUser user);

    /**
     * 修改管理账户的备注
     *
     * @param managerId
     * @param remark
     * @return
     */
    ResultData updateRemark(Long managerId, String remark);

    /**
     * 重置密码为888888
     *
     * @param managerId
     * @return
     */
    ResultData resetPwd(Long managerId);

    /**
     * 禁用账户
     *
     * @param managerId
     * @return
     */
    ResultData forbid(Long managerId);

    /**
     * 取消禁用账户
     *
     * @param managerId
     * @return
     */
    ResultData cancelForbid(Long managerId);

    ResultData updateManagerPassword(long id, String oldPassword,String password);
}
