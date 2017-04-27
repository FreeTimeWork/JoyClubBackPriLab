package com.joycity.joyclub.apifront.controller;

import com.joycity.joyclub.apifront.modal.cart.ClientPostAddress;
import com.joycity.joyclub.apifront.service.PostAddressService;
import com.joycity.joyclub.commons.modal.base.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.joycity.joyclub.commons.constant.Global.URL_API_FRONT;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Administrator on 2017/4/19.
 */
@RestController
@RequestMapping(URL_API_FRONT)
public class PostAddresssController {
    @Autowired
    PostAddressService postAddressService;

    @RequestMapping(value = "/postaddresses", method = GET)
    public ResultData getList(@RequestParam Long clientId) {
        return postAddressService.getList(clientId);
    }


    @RequestMapping(value = "/postaddress", method = POST)
    public ResultData add(ClientPostAddress postAddress) {
        return postAddressService.add(postAddress);
    }

    @RequestMapping(value = "/postaddress/id/delete", method = POST)
    public ResultData remove(@PathVariable Long id) {
        return postAddressService.remove(id);
    }
}