package com.joycity.joyclub.apiback.controller;

import com.joycity.joyclub.apiback.controller.base.BaseUserSessionController;
import com.joycity.joyclub.apiback.modal.generated.SysUser;
import com.joycity.joyclub.card_coupon.service.ShopService;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import static com.joycity.joyclub.commons.constant.Global.URL_API_BACK;

/**
 * Created by fangchen.chai on 2017/7/12.
 */
@RestController
@RequestMapping(URL_API_BACK )
public class ShopController extends BaseUserSessionController {
    @Autowired
    private ShopService shopService;

    @RequestMapping(value = "/all/shops", method = RequestMethod.GET)
    public ResultData getShopsByNameAndSubCommercial(@RequestParam(required = false) String name, @RequestParam(required = false) String subCommercial, HttpSession session) {
        SysUser sysUser = checkProjectUser(session);
        return shopService.getAllShopByNameAndSubCommercial(sysUser.getInfoId(), name, subCommercial);
    }

    @RequestMapping(value = "/shops", method = RequestMethod.GET)
    public ResultData getShopsByCodeAndSubCommercial(@RequestParam(required = false) String code, @RequestParam(required = false) String name, PageUtil pageUtil, HttpSession session) {
        SysUser sysUser = checkProjectUser(session);
        return shopService.getListByCodeAndName(sysUser.getInfoId(), code, name, pageUtil);
    }

    @RequestMapping(value = "/group/subcommercial/shops", method = RequestMethod.GET)
    public ResultData getShopsByCodeAndSubCommercial(HttpSession session) {
        SysUser sysUser = checkProjectUser(session);
        return shopService.getShopsGroupBySubCommercial(sysUser.getInfoId());
    }
}
