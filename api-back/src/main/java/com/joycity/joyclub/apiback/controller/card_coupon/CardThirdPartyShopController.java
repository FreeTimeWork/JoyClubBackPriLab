package com.joycity.joyclub.apiback.controller.card_coupon;

import com.joycity.joyclub.apiback.controller.base.BaseUserSessionController;
import com.joycity.joyclub.card_coupon.modal.generated.CardThirdPartyShop;
import com.joycity.joyclub.card_coupon.service.CardThirdPartyShopService;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.joycity.joyclub.commons.constant.Global.URL_API_BACK;

/**
 * Created by fangchen.chai on 2017/7/26
 */
@RestController
@RequestMapping(URL_API_BACK)
public class CardThirdPartyShopController extends BaseUserSessionController{

    @Autowired
    private CardThirdPartyShopService cardThirdPartyShopService;

    @RequestMapping(value = "/card/third/shop", method = RequestMethod.POST)
    public ResultData createThirdPartyShop(@RequestBody CardThirdPartyShop thirdPartyShop, HttpSession session) {

        return cardThirdPartyShopService.createThirdPartyShop(thirdPartyShop);
    }

    @RequestMapping(value = "/card/third/shops", method = RequestMethod.GET)
    public ResultData getListByName(@RequestParam(required = false) String name, PageUtil pageUtil, HttpSession httpSession) {

        //TODO：cfc 从session判断出是第三方商户
        return cardThirdPartyShopService.getListByName(name, pageUtil);
    }
}
