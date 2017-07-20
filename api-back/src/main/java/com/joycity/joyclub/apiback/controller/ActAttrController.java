package com.joycity.joyclub.apiback.controller;

import com.joycity.joyclub.act.modal.generated.SaleActAttr;
import com.joycity.joyclub.act.service.ActAttrService;
import com.joycity.joyclub.apiback.controller.base.BaseUserSessionController;
import com.joycity.joyclub.apiback.modal.generated.SysUser;
import com.joycity.joyclub.apiback.service.ManagerService;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.joycity.joyclub.commons.constant.Global.URL_API_BACK;

/**
 * Created by CallMeXYZ on 2017/3/29.
 */
@RestController
@RequestMapping(URL_API_BACK)
public class ActAttrController extends BaseUserSessionController {
    @Autowired
    private ActAttrService actAttrService;
    @Autowired
    private ManagerService managerService;

    /**
     * 只有商户用户可以访问
     *
     * @return data为按创建时间倒序的所有项目列表
     */
    @RequestMapping(value = "/act/attrs", method = RequestMethod.GET)
    public ResultData getList(@RequestParam(required = false) String actName, PageUtil pageUtil, HttpSession httpSession) {
        //确保是商户用户
        SysUser user = checkStoreUser(httpSession);
        return actAttrService.getListByStoreIdAndActName(user.getInfoId(), actName, pageUtil);
    }

    /**
     * 只有商户用户可以访问
     *
     * @param id
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/act/attr/{id}", method = RequestMethod.GET)
    public ResultData getActAttr(@PathVariable Long id, HttpSession httpSession) {
        //确保是商户用户
        checkStoreUser(httpSession);
        return actAttrService.getActAttr(id);
    }


    /**
     * 只有商户用户可以访问
     *
     * @param id
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/act/attr/{id}", method = RequestMethod.POST)
    public ResultData updateActAttr(@PathVariable Long id, @RequestBody SaleActAttr actAttr, HttpSession httpSession) {
        //确保是商户用户
        checkStoreUser(httpSession);
        actAttr.setId(id);
        return actAttrService.updateActAttr(actAttr);
    }

    /**
     * 只有商户用户可以访问
     *
     * @param actAttr
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/act/attr", method = RequestMethod.POST)
    public ResultData createActAttr(@RequestBody SaleActAttr actAttr, HttpSession httpSession) {
        //确保是商户用户
        SysUser user = checkStoreUser(httpSession);
        return actAttrService.createActAttr(actAttr);
    }

}
