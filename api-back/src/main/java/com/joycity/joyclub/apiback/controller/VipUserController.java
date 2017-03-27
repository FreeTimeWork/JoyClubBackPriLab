package com.joycity.joyclub.apiback.controller;

import com.joycity.joyclub.apiback.service.impl.VipUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2017/2/20.
 */
@RestController
@RequestMapping("/api/vipuser")
public class VipUserController {

    @Autowired
    VipUserServiceImpl vipUserService;

    @RequestMapping("/list")
    public Object list() {
        return vipUserService.getList();
    }

    @RequestMapping("/export")
    public void export(HttpServletResponse response) {
        vipUserService.export(response);
    }

}
