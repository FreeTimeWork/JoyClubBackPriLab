package com.joycity.joyclub.message.controller;

import com.joycity.joyclub.message.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: qiaohuizhong
 * @Description:
 * @Date: Created in 11:32 2017/11/16
 */
@RestController
@RequestMapping("/api/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @GetMapping("/send")
    public void MessageSend(@RequestParam String phone){
        String result = messageService.sendMessage("sms", phone, "尊敬的会员您好，您本笔消费100元，已累计消费850元，再消费150元即可参加满1000送150的优惠活动！回T退订");
        System.out.println("sendMessage response:" + result);
    }
}
