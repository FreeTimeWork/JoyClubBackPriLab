package com.joycity.joyclub.apiback.controller;

import com.joycity.joyclub.apiback.controller.base.BaseUserSessionController;
import com.joycity.joyclub.apiback.modal.base.ResultData;
import com.joycity.joyclub.apiback.modal.generated.SysProject;
import com.joycity.joyclub.apiback.modal.generated.SysUser;
import com.joycity.joyclub.apiback.service.ManagerService;
import com.joycity.joyclub.apiback.service.ProjectService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.joycity.joyclub.apiback.constant.Global.URL_API_BACK;

/**
 * Created by CallMeXYZ on 2017/3/28.
 */
@RestController
@RequestMapping(URL_API_BACK)
public class ManagerController extends BaseUserSessionController {

    @Autowired
    private ManagerService managerService;


    /**
     * 只有平台用户可以访问
     *
     * @param id
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/manager/{id}/forbid", method = RequestMethod.POST)
    public ResultData forbid(@PathVariable Long id, HttpSession httpSession) {
        //确保是平台用户
        checkPlatformUser(httpSession);
        return managerService.forbid(id);
    }

    /**
     * 只有平台用户可以访问
     *
     * @param id
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/manager/{id}/forbidcancel", method = RequestMethod.POST)
    public ResultData cancelForbid(@PathVariable Long id, HttpSession httpSession) {
        //确保是平台用户
        checkPlatformUser(httpSession);
        return managerService.cancelForbid(id);
    }


    /**
     * 只有平台用户可以访问
     *
     * @param id
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/manager/{id}/password/reset", method = RequestMethod.POST)
    public ResultData resetPassword(@PathVariable Long id, HttpSession httpSession) {
        //确保是平台用户
        checkPlatformUser(httpSession);
        return managerService.resetPwd(id);
    }
    /**
     * 只有登陆可以访问
     *
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/manager/password", method = RequestMethod.POST)
    public ResultData changeCurrentUserPassword(@RequestParam String oldPassword,@RequestParam String password, HttpSession httpSession) {
        //确保是登陆用户
       SysUser user= checkUser(httpSession);
        return managerService.updateManagerPassword(user.getId(),oldPassword,password);
    }
    /**
     * 只有平台用户可以访问
     *
     * @param id
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/manager/{id}/remark", method = RequestMethod.POST)
    public ResultData resetPassword(@PathVariable Long id, @RequestParam String remark, HttpSession httpSession) {
        //确保是平台用户
        checkPlatformUser(httpSession);
        return managerService.updateRemark(id, remark);
    }
}
