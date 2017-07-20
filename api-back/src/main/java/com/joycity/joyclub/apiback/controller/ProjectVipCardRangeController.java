package com.joycity.joyclub.apiback.controller;

import com.joycity.joyclub.apiback.controller.base.BaseUserSessionController;
import com.joycity.joyclub.apiback.modal.vo.vipcard.VipCardRangeBaseVO;
import com.joycity.joyclub.apiback.modal.vo.vipcard.VipCardRangeCreateVO;
import com.joycity.joyclub.apiback.service.ProjectVipCardRangeService;
import com.joycity.joyclub.commons.modal.base.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.joycity.joyclub.commons.constant.Global.URL_API_BACK;
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
    public ResultData createRange(@RequestParam Long projectId, @Valid @RequestBody VipCardRangeCreateVO createVO, HttpSession session) {
        checkPlatformUser(session);
        return vipCardRangeService.addRange(projectId, createVO.getType(), createVO.getMin(), createVO.getMax());
    }

    @RequestMapping(value = "vipcard/range/{id}", method = POST)
    public ResultData updateRange(@PathVariable Long id, @Valid @RequestBody VipCardRangeBaseVO vo, HttpSession session) {
        checkPlatformUser(session);
        return vipCardRangeService.updateRange(id, vo.getMin(), vo.getMax());
    }
}
