package com.joycity.joyclub.apiback.controller;

import com.joycity.joyclub.apiback.constant.UserType;
import com.joycity.joyclub.apiback.controller.base.BaseUserSessionController;
import com.joycity.joyclub.apiback.exception.BusinessException;
import com.joycity.joyclub.apiback.modal.base.ResultData;
import com.joycity.joyclub.apiback.modal.generated.SysProject;
import com.joycity.joyclub.apiback.modal.generated.SysUser;
import com.joycity.joyclub.apiback.service.ManagerService;
import com.joycity.joyclub.apiback.service.ProjectService;
import com.joycity.joyclub.apiback.service.impl.ProjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import static com.joycity.joyclub.apiback.constant.Global.URL_API_BACK;
import static com.joycity.joyclub.apiback.constant.ResultCode.API_NO_PERMISSION_FOR_CURRENT_USER;

/**
 * Created by CallMeXYZ on 2017/3/28.
 */
@RestController
@RequestMapping(URL_API_BACK)
public class ProjectController extends BaseUserSessionController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ManagerService managerService;
    /**
     * 只有平台用户可以访问
     *
     * @return data为按时间倒序的所有项目列表
     */
    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public ResultData getList(HttpSession httpSession) {
        //确保是平台用户
        checkPlatFormUser(httpSession);
        return projectService.getList();
    }

    /**
     * 只有平台用户可以访问
     *
     * @param id
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/project/{id}", method = RequestMethod.GET)
    public ResultData getProject(@PathVariable Long id, HttpSession httpSession) {
        //确保是平台用户
        checkPlatFormUser(httpSession);
        return projectService.getProject(id);
    }

    /**
     * 只有平台用户可以访问
     *
     * @param id
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/project/{id}", method = RequestMethod.POST)
    public ResultData updateProject(@PathVariable Long id, SysProject project, HttpSession httpSession) {
        //确保是平台用户
        checkPlatFormUser(httpSession);
        return projectService.updateProject(id, project);
    }

    /**
     * 只有平台用户可以访问
     *
     * @param project
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/project", method = RequestMethod.POST)
    public ResultData createProject(SysProject project, HttpSession httpSession) {
        //确保是平台用户
        checkPlatFormUser(httpSession);
        return projectService.createProject(project);
    }

    /**
     * 只有平台用户可以访问
     *
     * @param id
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/project/{id}/managers", method = RequestMethod.GET)
    public ResultData getProjectManagers(@PathVariable Long id, HttpSession httpSession) {
        //确保是平台用户
        checkPlatFormUser(httpSession);
        return managerService.getProjectManagersByProjectId(id);
    }
    /**
     * 只有平台用户可以访问
     *
     * @param id
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/project/{id}/manager", method = RequestMethod.POST)
    public ResultData createProjectManager(@PathVariable Long id, SysUser user, HttpSession httpSession) {
        //确保是平台用户
        checkPlatFormUser(httpSession);
        return managerService.createProjectManager(id, user);
    }
}
