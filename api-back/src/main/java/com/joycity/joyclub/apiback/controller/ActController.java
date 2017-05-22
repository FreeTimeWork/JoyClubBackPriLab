package com.joycity.joyclub.apiback.controller;

import com.joycity.joyclub.act.modal.generated.SaleActWithBLOBs;
import com.joycity.joyclub.act.service.ActService;
import com.joycity.joyclub.apiback.controller.base.BaseUserSessionController;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.apiback.modal.generated.SysUser;
import com.joycity.joyclub.apiback.service.ManagerService;
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
public class ActController extends BaseUserSessionController {
    @Autowired
    private ActService actService;
    @Autowired
    private ManagerService managerService;

    /**
     * 只有商户用户可以访问
     *
     * @return data为按创建时间倒序的所有项目列表
     */
    @RequestMapping(value = "/acts", method = RequestMethod.GET)
    public ResultData getList(@RequestParam(required = false) String name, PageUtil pageUtil, HttpSession httpSession) {
        //确保是商户用户
        SysUser user = checkStoreUser(httpSession);
        return actService.getListByStoreIdAndName(user.getInfoId(), name, pageUtil);
    }

    /**
     * 只有商户用户可以访问
     *
     * @param id
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/act/{id}", method = RequestMethod.GET)
    public ResultData getAct(@PathVariable Long id, HttpSession httpSession) {
        //确保是商户用户
        checkStoreUser(httpSession);
        return actService.getAct(id);
    }

    /**
     * 只有商户用户可以访问
     *
     * @param httpSession
     * @return 返回商品新建或者修改所需的基础数据，主要是Select标签的数据
     */
    @RequestMapping(value = "/act/formdata", method = RequestMethod.GET)
    public ResultData getAct(HttpSession httpSession) {
        //确保是商户用户
        SysUser user = checkStoreUser(httpSession);
        return actService.getActFormData(user.getInfoId());
    }

    /**
     * 只有商户用户可以访问
     *
     * @param id
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/act/{id}", method = RequestMethod.POST)
    public ResultData updateAct(@PathVariable Long id, SaleActWithBLOBs act, HttpSession httpSession) {
        //确保是商户用户
        checkStoreUser(httpSession);
        act.setId(id);
        return actService.updateAct( act);
    }

    /**
     * 只有商户用户可以访问
     *
     * @param act
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/act", method = RequestMethod.POST)
    public ResultData createAct(SaleActWithBLOBs act, HttpSession httpSession) {
        //确保是商户用户
        SysUser user = checkStoreUser(httpSession);
        act.setStoreId(user.getInfoId());
        return actService.createAct(act);
    }

}
