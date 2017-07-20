package com.joycity.joyclub.apifront.controller;

import com.joycity.joyclub.apifront.modal.vo.mallcoo.MallcooUserTokenVO;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.mallcoo.service.MallCooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.joycity.joyclub.commons.constant.Global.URL_API_FRONT;

/**
 * Created by CallMeXYZ on 2017/4/6.
 */
@RestController
@RequestMapping(URL_API_FRONT)
public class MallcooController {
    @Autowired
    MallCooService mallCooService;

    @RequestMapping(value = "/mallcoo/usertoken", method = {RequestMethod.POST})
    public ResultData subProjectWapAutoLogin(@Valid @RequestBody MallcooUserTokenVO vo) {
        return new ResultData(mallCooService.getUserToken(vo.getProjectId(), vo.getTicket()));
    }

}
