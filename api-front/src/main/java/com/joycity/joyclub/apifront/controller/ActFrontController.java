package com.joycity.joyclub.apifront.controller;

import com.joycity.joyclub.act.modal.generated.FrontApplyAct;
import com.joycity.joyclub.act.service.ActService;
import com.joycity.joyclub.act.service.ActTypeService;
import com.joycity.joyclub.act.service.FrontApplyActService;
import com.joycity.joyclub.client_token.service.ClientTokenService;
import com.joycity.joyclub.commons.constant.Global;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.joycity.joyclub.commons.constant.Global.URL_API_FRONT;

/**
 * Created by CallMeXYZ on 2017/4/13.
 */
@RestController
@RequestMapping(URL_API_FRONT)
public class ActFrontController {
    @Autowired
    ActService actService;
    @Autowired
    private FrontApplyActService applyActService;
    @Autowired
    private ActTypeService actTypeService;
    @Autowired
    ClientTokenService clientTokenService;

    @RequestMapping(value = "/act/{id}", method = RequestMethod.GET)
    public ResultData getActInfo(@PathVariable Long id) {
        return actService.getInfo(id);
    }

    @RequestMapping(value = "/acts", method = RequestMethod.GET)
    public ResultData getActs(@RequestParam(required = false) Long projectId, @RequestParam(required = false) Long storeId,
                              @RequestParam(required = false) Long actTypeId,PageUtil pageUtil) {

        return actService.getList(projectId,storeId,actTypeId,pageUtil);
    }
    /**
     * 获得商品的属性
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/act/{id}/attrs", method = RequestMethod.GET)
    public ResultData getProductAttrs(@PathVariable Long id) {
        return actService.getAttrs(id);
    }

    @PostMapping("/act/apply")
    public ResultData createApplyAct(@CookieValue(Global.COOKIE_TOKEN) String token,
                                     @RequestBody FrontApplyAct applyAct) {
        Long clientId = clientTokenService.getIdOrThrow(token);
        applyAct.setClientId(clientId);
//        applyAct.setClientId(1L);
        return applyActService.createApplyAct(applyAct);
    }

    @GetMapping("act/group/type")
    public ResultData getActType(PageUtil pageUtil){
        return actTypeService.getList(pageUtil);
    }

}
