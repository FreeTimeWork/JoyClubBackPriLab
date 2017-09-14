package com.joycity.joyclub.apiback.controller;

import static com.joycity.joyclub.commons.constant.Global.URL_API_BACK;

import javax.servlet.http.HttpSession;

import com.joycity.joyclub.act.modal.generated.SaleActType;
import com.joycity.joyclub.act.modal.generated.SaleActWithBLOBs;
import com.joycity.joyclub.act.service.ActService;
import com.joycity.joyclub.act.service.ActTypeService;
import com.joycity.joyclub.act.service.FrontApplyActService;
import com.joycity.joyclub.apiback.controller.base.BaseUserSessionController;
import com.joycity.joyclub.apiback.modal.generated.SysUser;
import com.joycity.joyclub.apiback.modal.vo.reviewInfoVO;
import com.joycity.joyclub.apiback.service.ManagerService;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by CallMeXYZ on 2017/3/29.
 */
@RestController
@RequestMapping(URL_API_BACK)
public class ActController extends BaseUserSessionController {
    @Autowired
    private ActService actService;
    @Autowired
    private FrontApplyActService applyActService;
    @Autowired
    private ActTypeService actTypeService;
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
    public ResultData updateAct(@PathVariable Long id, @RequestBody SaleActWithBLOBs act, HttpSession httpSession) {
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
    public ResultData createAct(@RequestBody SaleActWithBLOBs act, HttpSession httpSession) {
        //确保是商户用户
        SysUser user = checkStoreUser(httpSession);
        act.setStoreId(user.getInfoId());
        return actService.createAct(act);
    }

    @RequestMapping(value = "/project/carousel/acts", method = RequestMethod.GET)
    public ResultData getCurrentAct(@RequestParam(required = false) String name, @RequestParam(required = false) String storeName, PageUtil pageUtil, HttpSession httpSession) {
        SysUser user = checkProjectUser(httpSession);
        return actService.getListByActNameAndStoreName(user.getInfoId(), name, storeName, pageUtil);
    }

    /**
     * 申请活动的列表
     */
    @RequestMapping(value = "/act/apply", method = RequestMethod.GET)
    public ResultData getApplyAct(@RequestParam(required = false) Byte reviewStatus, PageUtil pageUtil, HttpSession httpSession) {
        checkProjectUser(httpSession);
        return applyActService.getListApplyAct(reviewStatus, pageUtil);
    }
    /**
     * 申请活动审核通过
     */
    @PostMapping("/act/apply/{id}/review/permit")
    public ResultData permitApplyAct(@PathVariable Long id, HttpSession httpSession) {
        checkProjectUser(httpSession);
        return applyActService.permitApplyAct(id);
    }
    @PostMapping("/act/apply/{id}/review/reject")
    public ResultData rejectApplyAct(@PathVariable Long id,@Validated @RequestBody reviewInfoVO vo, HttpSession httpSession) {
        checkProjectUser(httpSession);
        return applyActService.rejectApplyAct(id, vo.getInfo());
    }

    /**
     * 活动大类创建
     */
    @PostMapping("act/type")
    public ResultData createActType(@RequestBody SaleActType actType, HttpSession httpSession) {
//        checkProjectUser(httpSession);
        return actTypeService.createActType(actType);
    }
}
