package com.joycity.joyclub.apiback.controller.card_coupon;

import com.joycity.joyclub.apiback.controller.base.BaseUserSessionController;
import com.joycity.joyclub.apiback.modal.generated.SysUser;
import com.joycity.joyclub.card_coupon.service.ShopService;
import com.joycity.joyclub.commons.modal.base.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/offline/shops", method = RequestMethod.GET)
    public ResultData getShopsByNameAndCode(@RequestParam(required = false) String name,
                                            @RequestParam(required = false) String code,
                                            @RequestParam Long infoId,
                                            HttpSession session) {
//        SysUser sysUser = checkProjectUser(session);
        return shopService.getAllShopByNameAndCode(infoId, name, code);
    }

    @RequestMapping(value = "/group/subcommercial/shops", method = RequestMethod.GET)
    public ResultData getShopsByCodeAndSubCommercial(@RequestParam Long infoId,
                                                     HttpSession session) {
        SysUser sysUser = checkProjectUser(session);
        return shopService.getShopsGroupBySubCommercial(infoId);
    }

    @PostMapping(value = "/offline/shops/sync")
    public ResultData syncShops(@RequestParam Long infoId,HttpSession session) {
        SysUser sysUser = checkUser(session);
        return shopService.syncMallCooShop(infoId);
    }
}
