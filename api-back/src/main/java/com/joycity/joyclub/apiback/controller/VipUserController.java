package com.joycity.joyclub.apiback.controller;

import com.joycity.joyclub.apiback.controller.base.BaseUserSessionController;
import com.joycity.joyclub.apiback.modal.generated.SysUser;
import com.joycity.joyclub.apiback.service.VipUserService;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import static com.joycity.joyclub.commons.constant.Global.URL_API_BACK;

/**
 * Created by Administrator on 2017/2/20.
 */
@RestController
@RequestMapping(URL_API_BACK)
public class VipUserController extends BaseUserSessionController {

    @Autowired
    VipUserService vipUserService;

    /**
     * @param type
     * @param pointStart
     * @param pointEnd
     * @param vipNo
     * @param cardNo
     * @param phone
     * @param pageUtil
     * @param session
     * @return
     */
    @RequestMapping("/vipusers")
    public ResultData getList(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Integer pointStart,
            @RequestParam(required = false) Integer pointEnd,
            @RequestParam(required = false) String vipNo,
            @RequestParam(required = false) String cardNo,
            @RequestParam(required = false) String phone,
            PageUtil pageUtil,
            HttpSession session
    ) {

        SysUser sysUser = checkProjectUser(session);
        return vipUserService.getList(/*sysUser.getInfoId(),*/ type, pointStart, pointEnd, vipNo, cardNo, phone, pageUtil);
    }
}
