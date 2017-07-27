package com.joycity.joyclub.apiback.controller;

import com.joycity.joyclub.apiback.controller.base.BaseUserSessionController;
import com.joycity.joyclub.apiback.modal.generated.SysUser;
import com.joycity.joyclub.apiback.service.ManagerService;
import com.joycity.joyclub.card_coupon.service.CardThirdPartyShopService;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.modal.generated.SysStore;
import com.joycity.joyclub.commons.service.StoreService;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.joycity.joyclub.commons.constant.Global.URL_API_BACK;

/**
 * Created by fangchen.chai on 2017/7/27.
 */
@RestController
@RequestMapping(URL_API_BACK)
public class ThirdPartyShopController extends BaseUserSessionController {
    @Autowired
    private CardThirdPartyShopService thirdPartyShopService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private ManagerService managerService;

    /**
     * 只有平台或项目用户可以访问
     *
     * @return data为按创建时间倒序的所有项目列表
     */
//    @RequestMapping(value = "/third/party/shops", method = RequestMethod.GET)
//    public ResultData getList(PageUtil pageUtil, HttpSession httpSession) {
//        //确保是平台或项目用户
//        SysUser user = checkPlatformOrProjectUser(httpSession);
//        return storeService.getList(user.getInfoId(), pageUtil);
//    }
//
//    *
//     * 只有平台或项目用户可以访问
//     *
//     * @param id
//     * @param httpSession
//     * @return
//
//    @RequestMapping(value = "/third/party/shop/{id}", method = RequestMethod.GET)
//    public ResultData getStore(@PathVariable Long id, HttpSession httpSession) {
//        //确保是平台或项目用户
//        checkPlatformOrProjectUser(httpSession);
//        return storeService.getStore(id);
//    }
//    *
//     * 只有第三方商户用户可以访问
//     *查看自己的资料
//     * @param httpSession
//     * @return
//
//    @RequestMapping(value = "/third/party/shop/profile", method = RequestMethod.GET)
//    public ResultData getProfile(HttpSession httpSession) {
//        //确保是商户用户
//        SysUser user = checkStoreUser(httpSession);
//        return storeService.getStore(user.getInfoId());
//    }
//    *
//     * 只有商户用户可以访问
//     * 修改自己的资料
//     *
//     * @param httpSession
//     * @return
//
//    @RequestMapping(value = "/store/profile", method = RequestMethod.POST)
//    public ResultData updateProfile(@RequestBody SysStore store, HttpSession httpSession) {
//        //确保是商户用户
//        SysUser user = checkStoreUser(httpSession);
//        store.setId(user.getInfoId());
//        return storeService.updateStore(store);
//    }
//
//    *
//     * 只有平台或项目用户可以访问
//     *
//     * @param id
//     * @param httpSession
//     * @return
//
//    @RequestMapping(value = "/store/{id}", method = RequestMethod.POST)
//    public ResultData updateStore(@PathVariable Long id, @RequestBody SysStore store, HttpSession httpSession) {
//        //确保是平台或项目用户
//        checkPlatformOrProjectUser(httpSession);
//        store.setId(id);
//        return storeService.updateStore(store);
//    }
//
//    *
//     * 只有平台或项目用户可以访问
//     *
//     * @param store
//     * @param httpSession
//     * @return
//
//    @RequestMapping(value = "/store", method = RequestMethod.POST)
//    public ResultData createStore(@RequestBody SysStore store, HttpSession httpSession) {
//        //确保是平台或项目用户
//        SysUser user = checkPlatformOrProjectUser(httpSession);
//        store.setProjectId(user.getInfoId());
//        return storeService.createStore(store);
//    }
//
//    *
//     * 只有平台或项目用户可以访问
//     *
//     * @param id
//     * @param httpSession
//     * @return
//
//    @RequestMapping(value = "/store/{id}/managers", method = RequestMethod.GET)
//    public ResultData getStoreManagers(@PathVariable Long id, HttpSession httpSession) {
//        //确保是平台或项目用户
//        checkPlatformOrProjectUser(httpSession);
//        return managerService.getStoreManagersByStoreId(id);
//    }
//
//    *
//     * 只有平台或项目用户可以访问
//     *
//     * @param id
//     * @param httpSession
//     * @return
//
//    @RequestMapping(value = "/store/{id}/manager", method = RequestMethod.POST)
//    public ResultData createStoreManager(@PathVariable Long id, @RequestBody SysUser user, HttpSession httpSession) {
//        //确保是平台或项目用户
//        checkPlatformOrProjectUser(httpSession);
//        return managerService.createStoreManager(id, user);
//    }
}
