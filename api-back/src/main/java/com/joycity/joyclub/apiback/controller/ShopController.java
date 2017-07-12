package com.joycity.joyclub.apiback.controller;

import static com.joycity.joyclub.commons.constant.Global.URL_API_BACK;

import javax.servlet.http.HttpSession;

import com.joycity.joyclub.apiback.controller.base.BaseUserSessionController;
import com.joycity.joyclub.card_coupon.service.ShopService;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by fangchen.chai on 2017/7/12.
 */
@RestController
@RequestMapping(URL_API_BACK )
public class ShopController extends BaseUserSessionController {
    @Autowired
    private ShopService shopService;

    @RequestMapping(value = "/shops")
    public ResultData getShopsByCodeAndSubCommercial(@RequestParam String code, @RequestParam String name, PageUtil pageUtil, HttpSession session) {
        checkPlatformOrProjectOrStoreUser(session);
        return shopService.getListByCodeAndName(code, name, pageUtil);
    }

    @RequestMapping(value = "group/SubCommercial/shops")
    public ResultData getShopsByCodeAndSubCommercial(HttpSession session) {
        checkPlatformOrProjectOrStoreUser(session);
        return shopService.getShopsGroupBySubCommercial();
    }
}
