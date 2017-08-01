package com.joycity.joyclub.apiback.controller.card_coupon;

import com.joycity.joyclub.apiback.controller.base.BaseUserSessionController;
import com.joycity.joyclub.apiback.modal.generated.SysUser;
import com.joycity.joyclub.apiback.service.ManagerService;
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
    private CardThirdPartyShopService thirdPartyShopService;
    @Autowired
    private ManagerService managerService;

    @RequestMapping(value = "/card/third/shop", method = RequestMethod.POST)
    public ResultData createThirdPartyShop(@RequestBody CardThirdPartyShop thirdPartyShop, HttpSession session) {

        SysUser user = checkProjectUser(session);
        thirdPartyShop.setProjectId(user.getInfoId());
        return thirdPartyShopService.createThirdPartyShop(thirdPartyShop);
    }

    @RequestMapping(value = "/card/third/shop/{id}", method = RequestMethod.POST)
    public ResultData updateShop(@PathVariable Long id, @RequestBody CardThirdPartyShop shop, HttpSession httpSession) {
        //确保是平台或项目用户
        checkProjectUser(httpSession);
        shop.setId(id);
        return thirdPartyShopService.updateShop(shop);
    }

    @RequestMapping(value = "/card/third/shops", method = RequestMethod.GET)
    public ResultData getListByName(@RequestParam(required = false) String name, PageUtil pageUtil, HttpSession httpSession) {

        SysUser user = checkProjectUser(httpSession);
        return thirdPartyShopService.getListByName(user.getInfoId(), name, pageUtil);
    }
    /**
     * 只有项目用户可以访问
     *
     * @param id
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "card/third/shop/{id}", method = RequestMethod.GET)
    public ResultData getThirdPartyShop(@PathVariable Long id, HttpSession httpSession) {
        //确保是项目用户
        checkProjectUser(httpSession);
        return thirdPartyShopService.getShop(id);
    }
    /**
     * 只有第三方商户用户可以访问
     * 修改自己的资料
     *
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/card/third/shop/profile", method = RequestMethod.POST)
    public ResultData updateProfile(@RequestBody CardThirdPartyShop shop, HttpSession httpSession) {
        //确保是商户用户
        SysUser user = checkThirdPartyShopUser(httpSession);
        shop.setId(user.getInfoId());
        return thirdPartyShopService.updateShop(shop);
    }

    /**
     * 只有第三方商户用户可以访问
     * 查看自己的资料
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/card/third/shop/profile", method = RequestMethod.GET)
    public ResultData getProfile(HttpSession httpSession) {
        //确保是第三方商户用户
        SysUser user = checkThirdPartyShopUser(httpSession);
        return thirdPartyShopService.getShop(user.getInfoId());
    }

    /**
     * 只有项目用户可以访问
     *
     * @param id
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/card/third/shop/{id}/manager", method = RequestMethod.POST)
    public ResultData createShopManager(@PathVariable Long id, @RequestBody SysUser user, HttpSession httpSession) {
        //确保是项目用户
        checkProjectUser(httpSession);
        return managerService.createThirdPartyShopManager(id, user);
    }

    /**
     * 只有项目用户可以访问
     * 返回第三方用户列表
     * @param id
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/card/third/shop/{id}/managers", method = RequestMethod.GET)
    public ResultData getShopManagers(@PathVariable Long id, HttpSession httpSession) {
        //确保项目用户
        checkProjectUser(httpSession);
        return managerService.getThirdPartyShopManagersByShopId(id);
    }
}
