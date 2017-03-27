package com.joycity.joyclub.apiback.controller;

import com.joycity.joyclub.apiback.modal.base.ResultData;
import com.joycity.joyclub.apiback.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by CallMeXYZ on 2017/3/27.
 */
@RestController
@RequestMapping("/api/back")
public class AuthController extends BaseController {

    @Autowired
    private AuthService authService;


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultData login(@RequestParam String account, @RequestParam String password, HttpSession session) {
        ResultData result = authService.login(account, password);
        session.setAttribute(SESSION_ATTR_NAME_USER, result.getData());
        return result;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResultData login(HttpSession session) {
        session.invalidate();
        return authService.logout();
    }

}
