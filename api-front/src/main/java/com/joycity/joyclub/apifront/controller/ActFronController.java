package com.joycity.joyclub.apifront.controller;

import com.joycity.joyclub.act.service.ActService;
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
public class ActFronController {
    @Autowired
    ActService actService;

    @RequestMapping(value = "/act/{id}", method = RequestMethod.GET)
    public ResultData getActInfo(@PathVariable Long id) {
        return actService.getInfo(id);
    }

    @RequestMapping(value = "/acts", method = RequestMethod.GET)
    public ResultData getActs(@RequestParam(required = false) Long projectId, @RequestParam(required = false) Long storeId, PageUtil pageUtil) {

        return actService.getList(projectId,storeId,pageUtil);
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

}
