package com.joycity.joyclub.apiback.controller;

import com.joycity.joyclub.apiback.controller.base.BaseUserSessionController;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.apiback.service.ProjectVipCardRangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import static com.joycity.joyclub.apiback.constant.Global.URL_API_BACK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by CallMeXYZ on 2017/4/6.
 */
@RestController
@RequestMapping(URL_API_BACK + "/project")
public class ProjectVipCardRangeController extends BaseUserSessionController {
    @Autowired
    ProjectVipCardRangeService vipCardRangeService;

    @RequestMapping(value = "/{projectId}/vipcard/ranges", method = GET)
    public ResultData getRanges(@PathVariable Long projectId, HttpSession session) {
        checkPlatformUser(session);
        return vipCardRangeService.getRanges(projectId);

    }

    @RequestMapping(value = "vipcard/range", method = POST)
    public ResultData createRange(@RequestParam Long projectId, @RequestParam String type, @RequestParam Long min, @RequestParam Long max, HttpSession session) {
        checkPlatformUser(session);
        return vipCardRangeService.addRange(projectId, type, min, max);
    }

    @RequestMapping(value = "vipcard/range/{id}", method = POST)
    public ResultData updateRange(@PathVariable Long id, @RequestParam Long min, @RequestParam Long max, HttpSession session) {
        checkPlatformUser(session);
        return vipCardRangeService.updateRange(id, min, max);
    }
}
