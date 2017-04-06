package com.joycity.joyclub.apifront.controller;

import com.joycity.joyclub.apifront.modal.base.ResultData;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.joycity.joyclub.apifront.constant.Global.URL_API_FRONT;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by CallMeXYZ on 2017/4/5.
 */
@RestController
@RequestMapping(URL_API_FRONT)
public class StoreFrontController {
    /**
     * 获取某个商品信息
     *
     * @return
     */
    @RequestMapping(value = "/store/{id}", method = GET)
    public ResultData getStoreData(@PathVariable Long id) {
        return null;
    }
}
