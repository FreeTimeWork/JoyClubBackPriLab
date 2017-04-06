package com.joycity.joyclub.apiback.controller;

import com.joycity.joyclub.apiback.controller.base.BaseUserSessionController;
import com.joycity.joyclub.apiback.modal.base.ResultData;
import com.joycity.joyclub.apiback.modal.generated.SaleStoreDesignerWithBLOBs;
import com.joycity.joyclub.apiback.modal.generated.SysUser;
import com.joycity.joyclub.apiback.service.ManagerService;
import com.joycity.joyclub.apiback.service.DesignerService;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.joycity.joyclub.apiback.constant.Global.URL_API_BACK;

/**
 * Created by CallMeXYZ on 2017/3/29.
 */
@RestController
@RequestMapping(URL_API_BACK)
public class DesignerController extends BaseUserSessionController {
    @Autowired
    private DesignerService designerService;
    @Autowired
    private ManagerService managerService;

    /**
     * 只有商户用户可以访问
     *
     * @return data为按创建时间倒序的所有项目列表
     */
    @RequestMapping(value = "/designers", method = RequestMethod.GET)
    public ResultData getList(@RequestParam(required = false) String name, PageUtil pageUtil, HttpSession httpSession) {
        //确保是商户用户
        SysUser user = checkStoreUser(httpSession);
        return designerService.getListByStoreId(user.getInfoId());
    }

    /**
     * 只有商户用户可以访问
     *
     * @param id
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/designer/{id}", method = RequestMethod.GET)
    public ResultData getDesigner(@PathVariable Long id, HttpSession httpSession) {
        //确保是商户用户
        checkStoreUser(httpSession);
        return designerService.getDesigner(id);
    }


    /**
     * 只有商户用户可以访问
     *
     * @param id
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/designer/{id}", method = RequestMethod.POST)
    public ResultData updateDesigner(@PathVariable Long id, SaleStoreDesignerWithBLOBs designer, HttpSession httpSession) {
        //确保是商户用户
        checkStoreUser(httpSession);
        designer.setId(id);
        return designerService.updateDesigner( designer);
    }

    /**
     * 只有商户用户可以访问
     *
     * @param designer
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/designer", method = RequestMethod.POST)
    public ResultData createDesigner(SaleStoreDesignerWithBLOBs
                                                 designer, HttpSession httpSession) {
        //确保是商户用户
        SysUser user = checkStoreUser(httpSession);
        designer.setStoreId(user.getInfoId());
        return designerService.createDesigner(designer);
    }

}
