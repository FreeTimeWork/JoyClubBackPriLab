package com.joycity.joyclub.apiback.controller;

import com.joycity.joyclub.apiback.controller.base.BaseUserSessionController;
import com.joycity.joyclub.apiback.modal.generated.SysUser;
import com.joycity.joyclub.apiback.modal.vo.vipcard.BatchMakeCardVO;
import com.joycity.joyclub.apiback.service.VipCardNumService;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.joycity.joyclub.commons.constant.Global.URL_API_BACK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by CallMeXYZ on 2017/3/29.
 */
@RestController
@RequestMapping(URL_API_BACK + "/vipcard/")
public class VipCardNumController extends BaseUserSessionController {
    @Autowired
    private VipCardNumService vipCardNumService;

    /**
     * 只有项目用户可以访问
     *
     * @return data为按创建时间倒序的所有项目列表
     */
    @RequestMapping(value = "/nums", method = GET)
    public ResultData getList(/*@RequestParam(required = false) Long projectId,*/
                              @RequestParam(required = false) String batch,
                              @RequestParam(required = false) String type,
                              @RequestParam(required = false) Byte status,
                              @RequestParam Long infoId,
                              PageUtil pageUtil, HttpSession httpSession) {
// TODO: 2017/4/27 平台用户返回所有的卡号，并且前端每条记录增加门店的信息和门店的筛选功能
        SysUser sysUser = checkProjectUser(httpSession);
        return vipCardNumService.getList(infoId, batch, type, status, pageUtil);
    }

    /**
     * 只有项目用户可以访问
     * 批量制卡
     *
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/nums", method = POST)
    public ResultData createNums(/*@RequestParam Long projectId,*/
                                 @Valid @RequestBody BatchMakeCardVO makeCardVO, HttpSession httpSession) {

        SysUser sysUser = checkProjectUser(httpSession);
        return vipCardNumService.createCardNum(makeCardVO.getInfoId(), makeCardVO.getBatch(), makeCardVO.getType(), makeCardVO.getNum());
    }

    /**
     * 只有项目用户可以访问
     *
     * @return data为按创建时间倒序的所有项目列表
     */
    @RequestMapping(value = "/num/formdata", method = GET)
    public ResultData getFormData(@RequestParam Long infoId, HttpSession httpSession) {
        // TODO: 2017/4/27 平台用户返回所有的卡号，并且前端每条记录增加门店的信息和门店的筛选功能
        SysUser sysUser = checkProjectUser(httpSession);
        return vipCardNumService.getFormData(infoId);
    }
}
