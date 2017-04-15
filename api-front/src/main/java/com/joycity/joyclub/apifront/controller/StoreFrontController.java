package com.joycity.joyclub.apifront.controller;

import com.joycity.joyclub.apifront.service.StoreFrontService;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.joycity.joyclub.apifront.constant.Global.URL_API_FRONT;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by CallMeXYZ on 2017/4/5.
 */
@RestController
@RequestMapping(URL_API_FRONT)
public class StoreFrontController {
    @Autowired
    StoreFrontService storeService;

    @RequestMapping(value = "/store/{id}", method = GET)
    public ResultData getStoreData(@PathVariable Long id) {
        return storeService.getInfo(id);
    }
    @RequestMapping(value = "/stores", method = GET)
    public ResultData getStoreData(@RequestParam(required = false) Long projectId, PageUtil pageUtil) {
        return storeService.getList(projectId, pageUtil);
    }


}
