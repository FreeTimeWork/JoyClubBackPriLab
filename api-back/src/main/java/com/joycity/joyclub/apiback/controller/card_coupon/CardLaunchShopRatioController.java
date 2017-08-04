package com.joycity.joyclub.apiback.controller.card_coupon;

import com.joycity.joyclub.apiback.controller.base.BaseUserSessionController;
import com.joycity.joyclub.card_coupon.service.ShopRatioService;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.joycity.joyclub.commons.constant.Global.URL_API_BACK;

/**
 * @Author fangchen.chai
 * @Date 2017/7/21 16:02
 */
@RestController
@RequestMapping(URL_API_BACK + "/card/coupon")
public class CardLaunchShopRatioController extends BaseUserSessionController{

    @Autowired
    private ShopRatioService shopRatioService;

    @GetMapping(value = "/launch/{id}/shop/ratio")
    public ResultData getShopRatioByLaunchId(@PathVariable Long id,
                                             @RequestParam(required = false) String shopName,
                                             PageUtil pageUtil,
                                             HttpSession session) {
        checkProjectUser(session);
        // 以后如果不指定launchId 需要用projectId
        return shopRatioService.getListByLaunchId(id, shopName, pageUtil);
    }

}
