package com.joycity.joyclub.message.service.impl;

import com.joycity.joyclub.message.service.MessageService;
import com.joycity.joyclub.message.util.HttpJsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: qiaohuizhong
 * @Description:
 * @Date: Created in 18:30 2017/11/15
 */
@Service
public class MessageServiceImpl implements MessageService{
    private HttpJsonUtil httpJsonUtil = new HttpJsonUtil();
    private String result = null;

    @Override
    public String sendMessage(String type, String userVirturId, String content){
        if("sms".equals(type))
        {
            result = httpJsonUtil.sendJson(httpJsonUtil.userName, httpJsonUtil.password, httpJsonUtil.url_submit, userVirturId, content, httpJsonUtil.sign, "", "");
        }else if ("weixin".equals(type)){

        }
        return result;
    }
}
