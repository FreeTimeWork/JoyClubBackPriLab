package com.joycity.joyclub.apiback.controller;

import com.joycity.joyclub.apiback.controller.base.BaseUserSessionController;
import com.joycity.joyclub.apiback.modal.generated.SysProject;
import com.joycity.joyclub.apiback.modal.generated.SysUser;
import com.joycity.joyclub.apiback.service.ManagerService;
import com.joycity.joyclub.apiback.service.ProjectService;
import com.joycity.joyclub.commons.modal.base.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.joycity.joyclub.commons.constant.Global.URL_API_BACK;

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
     * @return data为按创建时间倒序的所有项目列表
     */
    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public ResultData getList(HttpSession httpSession) {
        //确保是平台用户
        checkPlatformUser(httpSession);
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
        checkPlatformUser(httpSession);
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
    public ResultData updateProject(@PathVariable Long id, @RequestBody SysProject project, HttpSession httpSession) {
        //确保是平台用户
        checkPlatformUser(httpSession);
        project.setId(id);
        return projectService.updateProject(project);
    }

    /**
     * 只有平台或者项目用户可以访问
     * 用来修改自己的资料
     *
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/project/profile", method = RequestMethod.POST)
    public ResultData updateMyProfile(@RequestBody SysProject project, HttpSession httpSession) {
        //确保是平台用户
        SysUser user = checkPlatformOrProjectUser(httpSession);
        project.setId(user.getInfoId());
        return projectService.updateProject(project);
    }

    /**
     * 只有平台或者项目用户可以访问
     * 用来查看自己的资料
     *
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/project/profile", method = RequestMethod.GET)
    public ResultData getMyProfile(HttpSession httpSession) {
        //确保是平台用户
        SysUser user = checkPlatformOrProjectUser(httpSession);
        return projectService.getProject(user.getInfoId());
    }

    /**
     * 只有平台用户可以访问
     *
     * @param project
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/project", method = RequestMethod.POST)
    public ResultData createProject(@RequestBody SysProject project, HttpSession httpSession) {
        //确保是平台用户
        checkPlatformUser(httpSession);
        return projectService.createProject(project);
    }

    /**
     * 只有平台用户可以访问
     * 注意者这里虽然是说查找项目的管理者，实际上平台的管理者也可以查找
     *
     * @param id
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/project/{id}/managers", method = RequestMethod.GET)
    public ResultData getProjectManagers(@PathVariable Long id, HttpSession httpSession) {
        //确保是平台用户
        checkPlatformUser(httpSession);
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
    public ResultData createProjectManager(@PathVariable Long id, @RequestBody SysUser user, HttpSession httpSession) {
        //确保是平台用户
        checkPlatformUser(httpSession);
        return managerService.createProjectManager(id, user);
    }
}
