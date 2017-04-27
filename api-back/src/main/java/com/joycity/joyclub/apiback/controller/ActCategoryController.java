package com.joycity.joyclub.apiback.controller;

import com.joycity.joyclub.apiback.controller.base.BaseUserSessionController;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.apiback.modal.generated.SysActCategory;
import com.joycity.joyclub.apiback.modal.generated.SysUser;
import com.joycity.joyclub.apiback.service.ActCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import static com.joycity.joyclub.commons.constant.Global.URL_API_BACK;

/**
 * Created by CallMeXYZ on 2017/3/29.
 */
@RestController
@RequestMapping(URL_API_BACK)
public class ActCategoryController extends BaseUserSessionController {
    @Autowired
    private ActCategoryService actCategoryService;


    /**
     * 只有平台用户可以访问
     *
     * @return data为按创建时间倒序的所有项目列表
     */
    @RequestMapping(value = "/act/categories", method = RequestMethod.GET)
    public ResultData getList(HttpSession httpSession) {
        //确保是平台或项目用户
        SysUser user = checkPlatformOrProjectUser(httpSession);
        return actCategoryService.getList();
    }

    /**
     * 只有平台用户可以访问
     *
     * @param id
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/act/category/{id}", method = RequestMethod.GET)
    public ResultData getActCategory(@PathVariable Long id, HttpSession httpSession) {
        //确保是平台或项目用户
        checkPlatformOrProjectUser(httpSession);
        return actCategoryService.getActCategory(id);
    }

    /**
     * 只有平台可以访问
     *
     * @param id
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/act/category/{id}", method = RequestMethod.POST)
    public ResultData updateActCategory(@PathVariable Long id, SysActCategory actCategory, HttpSession httpSession) {
        //确保是平台或项目用户
        checkPlatformOrProjectUser(httpSession);
        return actCategoryService.updateActCategory(id, actCategory);
    }

    /**
     * 只有平台可以访问
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/act/category", method = RequestMethod.POST)
    public ResultData updateActCategory( SysActCategory actCategory, HttpSession httpSession) {
        //确保是平台或项目用户
        checkPlatformOrProjectUser(httpSession);
        return actCategoryService.createActCategory(actCategory);
    }
}
