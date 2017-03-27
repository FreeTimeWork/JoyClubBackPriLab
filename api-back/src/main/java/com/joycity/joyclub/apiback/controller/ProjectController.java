package com.joycity.joyclub.apiback.controller;

import com.joycity.joyclub.apiback.modal.base.ResultData;
import com.joycity.joyclub.apiback.exception.BusinessException;
import com.joycity.joyclub.apiback.service.ProjectService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 项目controller
 * Created by CallMeXYZ on 2017/2/27.
 */
@RestController
@RequestMapping("/api/back")
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
