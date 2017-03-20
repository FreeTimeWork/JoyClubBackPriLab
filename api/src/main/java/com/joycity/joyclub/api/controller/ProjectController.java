package com.joycity.joyclub.api.controller;

import com.joycity.joyclub.api.constant.ResultCode;
import com.joycity.joyclub.api.entity.base.ResultData;
import com.joycity.joyclub.api.entity.project.BaseProject;
import com.joycity.joyclub.api.exception.BusinessException;
import com.joycity.joyclub.api.service.ProjectService;
import com.joycity.joyclub.api.service.impl.ProjectServiceImpl;
import com.joycity.joyclub.api.service.impl.VipUserServiceImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 项目controller
 * Created by CallMeXYZ on 2017/2/27.
 */
@RestController
@RequestMapping("/api")
public class ProjectController {
    private final Log log = LogFactory.getLog(ProjectController.class);
    @Autowired
    ProjectService projectService;

    @RequestMapping("/projects")
    public ResultData getProjects() {
        throw  new BusinessException(1);
        /*log.info(projectService);
        return projectService.getList();*/
    }

}
