package com.joycity.joyclub.apifront.controller;

import com.joycity.joyclub.apifront.service.DesignerFrontService;
import com.joycity.joyclub.commons.modal.base.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.joycity.joyclub.apifront.constant.Global.URL_API_FRONT;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by CallMeXYZ on 2017/4/5.
 */
@RestController
@RequestMapping(URL_API_FRONT)
public class DesignerFrontController {
    @Autowired
    DesignerFrontService designerService;

    @RequestMapping(value = "/designer/{id}", method = GET)
    public ResultData getInfo(@PathVariable Long id) {
        return designerService.getInfo(id);
    }
    //    todo project
    @RequestMapping(value = "/designers", method = GET)

    public ResultData getList(@RequestParam(required = false) Long projectId,@RequestParam(required = false) Long storeId) {
        return designerService.getList(projectId,storeId);
    }
}
