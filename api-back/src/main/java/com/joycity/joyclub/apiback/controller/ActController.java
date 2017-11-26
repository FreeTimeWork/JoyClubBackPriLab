package com.joycity.joyclub.apiback.controller;

import static com.joycity.joyclub.commons.constant.Global.URL_API_BACK;

import javax.servlet.http.HttpSession;

import com.joycity.joyclub.act.modal.generated.FrontApplyActType;
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
    public ResultData getList(@RequestParam(required = false) Long projectId,@RequestParam(required = false) String name, PageUtil pageUtil, HttpSession httpSession) {
        //确保是商户用户
        SysUser user = checkPlatformOrProjectOrStoreUser(httpSession);
        return actService.getListByStoreIdAndName(projectId, name, pageUtil);
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
        checkPlatformOrProjectOrStoreUser(httpSession);
        return actService.getAct(id);
    }

    /**
     * 只有商户用户可以访问
     *
     * @param httpSession
     * @return 返回商品新建或者修改所需的基础数据，主要是Select标签的数据
     */
    @RequestMapping(value = "/act/formdata", method = RequestMethod.GET)
    public ResultData getActFormdata(@RequestParam(required = false) Long projectId,HttpSession httpSession) {
        //确保是商户用户
//        SysUser user = checkPlatformOrProjectOrStoreUser(httpSession);
        return actService.getActFormData(projectId);
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
        checkPlatformOrProjectOrStoreUser(httpSession);
        act.setId(id);
        return actService.updateAct(act);
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
        SysUser user = checkPlatformOrProjectOrStoreUser(httpSession);
        return actService.createAct(act);
    }

    @RequestMapping(value = "/project/carousel/acts", method = RequestMethod.GET)
    public ResultData getCurrentAct(@RequestParam(required = false) Long projectId,@RequestParam(required = false) String name, @RequestParam(required = false) String storeName, PageUtil pageUtil, HttpSession httpSession) {
        SysUser user = checkPlatformOrProjectOrStoreUser(httpSession);
        return actService.getListByActNameAndStoreName(projectId, name, storeName, pageUtil);
    }

    /**
     * 申请活动的列表
     */
    @RequestMapping(value = "/act/apply", method = RequestMethod.GET)
    public ResultData getApplyAct(@RequestParam(required = false) Long projectId,
                                  @RequestParam(required = false) Byte reviewStatus,
                                  @RequestParam(required = false) String name,
                                  PageUtil pageUtil, HttpSession httpSession) {
        checkPlatformOrProjectOrStoreUser(httpSession);
        return applyActService.getListApplyAct(projectId,reviewStatus, name, pageUtil);
    }

    /**
     * 申请活动的详情
     */
    @RequestMapping(value = "/act/apply/{id}", method = RequestMethod.GET)
    public ResultData getApplyAct(@PathVariable Long id, HttpSession httpSession) {
        checkPlatformOrProjectOrStoreUser(httpSession);
        return applyActService.getApplyActById(id);
    }

    /**
     * 申请活动审核通过
     */
    @PostMapping("/act/apply/{id}/review/permit")
    public ResultData permitApplyAct(@PathVariable Long id, HttpSession httpSession) {
        checkPlatformOrProjectOrStoreUser(httpSession);
        return applyActService.permitApplyAct(id);
    }

    @PostMapping("/act/apply/{id}/review/reject")
    public ResultData rejectApplyAct(@PathVariable Long id, @Validated @RequestBody(required = false) reviewInfoVO vo, HttpSession httpSession) {
        checkPlatformOrProjectOrStoreUser(httpSession);
        String info = null;
        if (vo != null) {
            info = vo.getInfo();
        }
        return applyActService.rejectApplyAct(id, info);
    }

    /**
     * 得到有效的申请活动列表，审核通过，并且没有关联活动
     *
     * @return
     */
    @GetMapping("/act/apply/effectivity")
    public ResultData getEffectivityApplyActs(@RequestParam(required = false) Long projectId,@RequestParam(required = false) String name, PageUtil pageUtil, HttpSession httpSession) {
        checkPlatformOrProjectOrStoreUser(httpSession);
        return applyActService.getEffListApplyAct(projectId,name, pageUtil);
    }

    /**
     * 申请活动里的活动的类型创建
     */
    @PostMapping("/act/apply/type")
    public ResultData createApplyActType(@RequestBody FrontApplyActType applyActType, HttpSession session) {
        checkPlatformOrProjectOrStoreUser(session);
        return applyActService.createApplyActType(applyActType);
    }

    @PostMapping("/act/apply/type/{id}/delete")
    public ResultData deleteApplyActType(@PathVariable Long id, HttpSession session) {
        checkPlatformOrProjectOrStoreUser(session);
        return applyActService.deleteApplyActType(id);
    }

    @PostMapping("/act/apply/type/{id}")
    public ResultData updateApplyActType(@PathVariable Long id, @RequestBody FrontApplyActType applyActType, HttpSession session){
        checkPlatformOrProjectOrStoreUser(session);
        applyActType.setId(id);
        return applyActService.updateApplyActType(applyActType);
    }

    @GetMapping("/act/apply/type/{id}")
    public ResultData getApplyActType(@PathVariable Long id){
        return applyActService.getApplyActTypeById(id);
    }

    /**
     * 活动大类创建
     */
    @PostMapping("/act/type")
    public ResultData createActType(@RequestBody SaleActType actType, HttpSession httpSession) {
        checkPlatformOrProjectOrStoreUser(httpSession);
        return actTypeService.createActType(actType);

    }

    @PostMapping("/act/type/{id}")
    public ResultData updateActType(@PathVariable Long id,@RequestBody SaleActType actType, HttpSession httpSession) {
        checkPlatformOrProjectOrStoreUser(httpSession);
        actType.setId(id);
        return actTypeService.updateActType(actType);
    }

    @GetMapping("/act/type/{id}")
    public ResultData getActType(@PathVariable Long id) {
        return actTypeService.getActType(id);
    }

    @PostMapping("/act/type/{id}/delete")
    public ResultData deleteActType(@PathVariable Long id, HttpSession httpSession) {
        checkPlatformOrProjectOrStoreUser(httpSession);
        return actTypeService.deleteActType(id);

    }

    @GetMapping("/act/apply/type")
    public ResultData getApplyTypes(){
        return applyActService.getListApplyActType();
    }

    @GetMapping("/act/type")
    public ResultData getActTypes(@RequestParam(required = false) Long projectId){
        return actTypeService.getAllSaleActTypes(projectId);
    }
}
